package tech.indicio.ariesmobileagentandroid.messaging;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.hyperledger.indy.sdk.IndyException;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import tech.indicio.ariesmobileagentandroid.IndyWallet;
import tech.indicio.ariesmobileagentandroid.connections.ConnectionRecord;
import tech.indicio.ariesmobileagentandroid.connections.diddoc.IndyService;
import tech.indicio.ariesmobileagentandroid.messaging.transports.TransportService;

public class MessageSender {

    private static final String TAG = "AMAA-MessageSender";

    private final IndyWallet indyWallet;
    private final TransportService transportService;
    private final ThreadHandler threadHandler;

    public MessageSender(IndyWallet indyWallet, MessageReceiver messageReceiver, ThreadHandler threadHandler) {
        this.threadHandler = threadHandler;
        this.indyWallet = indyWallet;
        this.transportService = new TransportService(messageReceiver, this);
    }

    public CompletableFuture<BaseMessage> sendMessage(BaseMessage message, ConnectionRecord connectionRecord) throws JSONException, InterruptedException, ExecutionException, IndyException {
        String endpoint;
        String[] recipientKeys;

        if (connectionRecord.theirDidDoc != null) {
            IndyService service = selectService(connectionRecord.theirDidDoc.service);
            endpoint = service.serviceEndpoint;
            recipientKeys = service.recipientKeys;
        } else {
            Log.e(TAG, new Gson().toJson(connectionRecord));
            endpoint = connectionRecord.invitation.serviceEndpoint;
            recipientKeys = connectionRecord.invitation.recipientKeys;
        }

        String senderVerkey = connectionRecord.verkey;

        Gson gson = new GsonBuilder().excludeFieldsWithModifiers(java.lang.reflect.Modifier.TRANSIENT).create();
        String jsonMessage = gson.toJson(message);
        Log.d(TAG, "GSON object: " + jsonMessage);

        //Just for logging
        JSONObject logMessage = new JSONObject(jsonMessage);
        String prettyMessage = logMessage.toString(4).replaceAll("\\\\", "");
        Log.d(TAG, "Sending Message of type '" + logMessage.getString("@type") + "' to Endpoint '" + endpoint + "', \n message: " + prettyMessage);

        //Pack with indy
        byte[] packedMessage = this.indyWallet.packMessage(jsonMessage, recipientKeys, senderVerkey);
        Log.d(TAG, "OUTBOUND MESSAGE:" + jsonMessage);

        CompletableFuture<BaseMessage> thread = new CompletableFuture<BaseMessage>();
        this.threadHandler.cacheMessageId(message.id, thread);

        this.transportService.send(packedMessage, endpoint, connectionRecord);

        return thread;
    }

    private IndyService selectService(IndyService[] services, String[] protocolPreference) {
        //Loop through protocols in order of preference and grab the first one that has a matching endpoint.
        for (String protocol : protocolPreference) {
            for (IndyService service : services) {
                if (service.serviceEndpoint.startsWith(protocol)) {
                    return service;
                }
            }
        }
        //Return first service if there is not one.
        return services[0];
    }

    private IndyService selectService(IndyService[] services) {
        String[] preferenceOrder = {"wss", "ws", "https", "http"};
        return selectService(services, preferenceOrder);
    }
}
