package tech.indicio.ariesmobileagentandroid.messaging;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.hyperledger.indy.sdk.IndyException;
import org.hyperledger.indy.sdk.crypto.Crypto;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

import tech.indicio.ariesmobileagentandroid.IndyWallet;
import tech.indicio.ariesmobileagentandroid.transports.TransportService;

public class MessageSender {

    private static final String TAG = "AMAA-MessageSender";

    private IndyWallet indyWallet;

    public MessageSender (IndyWallet indyWallet) {
        this.indyWallet = indyWallet;
    }

    //TODO: Replace ourKey, endpoint, recipientKeys, and routingKeys with ConnectionRecord
    public void sendMessage(BaseMessage message, String endpoint, String senderVerkey, String[] recipientKeys, String[] routingKeys) throws JSONException, InterruptedException, ExecutionException, IndyException {


        Gson gson = new GsonBuilder().excludeFieldsWithModifiers(java.lang.reflect.Modifier.TRANSIENT).create();
        String jsonMessage = gson.toJson(message);

        JSONObject logMessage = new JSONObject(jsonMessage);
        String prettyMessage = logMessage.toString(4).replaceAll("\\\\", "");
        Log.d(TAG, "Sending Message of type '" + logMessage.getString("@type") + "' to Endpoint '" + endpoint + "', \n message: " + prettyMessage);

        byte[] packedMessage = this.indyWallet.packMessage(jsonMessage, recipientKeys, senderVerkey);

        TransportService.send(packedMessage, endpoint);
    }
}
