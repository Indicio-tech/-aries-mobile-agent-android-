package tech.indicio.ariesmobileagentandroid.messaging;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.hyperledger.indy.sdk.IndyException;
import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutionException;

import tech.indicio.ariesmobileagentandroid.IndyWallet;
import tech.indicio.ariesmobileagentandroid.connections.ConnectionRecord;
import tech.indicio.ariesmobileagentandroid.transports.TransportService;

public class MessageSender {

    private static final String TAG = "AMAA-MessageSender";

    private final IndyWallet indyWallet;
    private final TransportService transportService;

    public MessageSender(IndyWallet indyWallet, TransportService transportService) {
        this.indyWallet = indyWallet;
        this.transportService = transportService;
    }

    //TODO: Replace5, endpoint, recipientKeys, and routingKeys with ConnectionRecord
    public void sendMessage(BaseMessage message, ConnectionRecord connectionRecord) throws JSONException, InterruptedException, ExecutionException, IndyException {

        //TODO: Specify services to use?
        String endpoint = connectionRecord.invitation.serviceEndpoint;
        String[] recipientKeys = connectionRecord.invitation.recipientKeys;

        String senderVerkey = connectionRecord.verkey;

        Gson gson = new GsonBuilder().excludeFieldsWithModifiers(java.lang.reflect.Modifier.TRANSIENT).create();
        String jsonMessage = gson.toJson(message);
        Log.d(TAG, "GSON object: "+ jsonMessage);

        //Just for logging
        JSONObject logMessage = new JSONObject(jsonMessage);
        String prettyMessage = logMessage.toString(4).replaceAll("\\\\", "");
        Log.d(TAG, "Sending Message of type '" + logMessage.getString("@type") + "' to Endpoint '" + endpoint + "', \n message: " + prettyMessage);

        //Pack with indy
        byte[] packedMessage = this.indyWallet.packMessage(jsonMessage, recipientKeys, senderVerkey);
        Log.d(TAG, "OUTBOUND MESSAGE:"+jsonMessage);

        this.transportService.send(packedMessage, endpoint);
    }
}
