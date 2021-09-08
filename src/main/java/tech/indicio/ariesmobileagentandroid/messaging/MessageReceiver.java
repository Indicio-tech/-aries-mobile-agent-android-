package tech.indicio.ariesmobileagentandroid.messaging;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONObject;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import tech.indicio.ariesmobileagentandroid.IndyWallet;

public class MessageReceiver {
    private static final String TAG = "AMAA-MessageReceiver";
    private final ArrayList<MessageListener> listeners = new ArrayList<>();
    private final IndyWallet indyWallet;

    public MessageReceiver(IndyWallet indyWallet) {
        Log.d(TAG, "Creating messageReceiver service");
        this.indyWallet = indyWallet;
    }

    public void receiveMessage(byte[] packedPackage) {
        try {
            //Unpacking Message
            byte[] unpackedPackage = this.indyWallet.unpackMessage(packedPackage);
            String packageString = new String(unpackedPackage);
            JSONObject jsonPackage = new JSONObject(packageString);

            Object message = jsonPackage.get("message");
            String messageString = message.toString();

            JSONObject messageJson = new JSONObject(messageString);
            String messageType = messageJson.getString("@type");

            Gson gson = new GsonBuilder().excludeFieldsWithModifiers(Modifier.TRANSIENT).create();

            boolean handled = false;

            Log.d(TAG, "Message received of type: " + messageType + "\nMessage: " + messageJson.toString(4).replaceAll("\\\\", ""));

            //Check each listener to see if it supports message type
            for (MessageListener listener : listeners) {
                //Get supported message types of listener
                HashMap<String, Class<? extends BaseMessage>> supportedMessages = listener._getSupportedMessages();
                for (Map.Entry<String, Class<? extends BaseMessage>> entry : supportedMessages.entrySet()) {
                    String type = entry.getKey();
                    if (type.equals(messageType)) {
                        Class<? extends BaseMessage> messageClass = entry.getValue();
                        listener._callback(
                                type,
                                gson.fromJson(messageString, messageClass)
                        );
                        handled = true;
                    }
                }
            }

            if (!handled) {
                Log.e(TAG, "Message received of type '" + messageType + "' has no valid listeners.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void registerListener(MessageListener listener) {
        this.listeners.add(listener);
        Log.d(TAG, listener.getClass().getSimpleName() + " listener registered");
    }

}
