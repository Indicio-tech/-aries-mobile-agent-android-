package tech.indicio.ariesmobileagentandroid.messaging;

import android.util.Base64;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import tech.indicio.ariesmobileagentandroid.IndyWallet;
import tech.indicio.ariesmobileagentandroid.messaging.decorators.SignatureDecorator;
import tech.indicio.ariesmobileagentandroid.messaging.decorators.ThreadDecorator;

public class MessageReceiver {
    private static final String TAG = "AMAA-MessageReceiver";
    private final ArrayList<MessageListener> listeners = new ArrayList<>();
    private final IndyWallet indyWallet;
    private final ThreadHandler threadHandler;

    public MessageReceiver(IndyWallet indyWallet, ThreadHandler threadHandler) {
        Log.d(TAG, "Creating messageReceiver service");
        this.indyWallet = indyWallet;
        this.threadHandler = threadHandler;
    }

    public void receiveMessage(byte[] packedPackage) {
        try {
            Log.d(TAG, "Packed message: " + new String(packedPackage, StandardCharsets.UTF_8));

            //Unpacking Message
            byte[] unpackedPackage = this.indyWallet.unpackMessage(packedPackage);
            String packageString = new String(unpackedPackage);
            JSONObject jsonPackage = new JSONObject(packageString);

            Log.d(TAG, "unpacked message: " + packageString);

            Object message = jsonPackage.get("message");
            String messageString = parseDecorators(message.toString());

            JSONObject messageJson = new JSONObject(messageString);
            String messageType = messageJson.getString("@type");

            Gson gson = new GsonBuilder().excludeFieldsWithModifiers(Modifier.TRANSIENT).create();

            boolean handled = false;

            Log.d(TAG, "Message received of type: " + messageType + "\nMessage: " + messageJson.toString(4).replaceAll("\\\\", ""));

            //Check each listener to see if it supports message type
            for (MessageListener listener : listeners) {
                //Get supported message types of listener
                HashMap<String, Class<? extends BaseMessage>> supportedMessages = listener.getSupportedMessages();
                for (Map.Entry<String, Class<? extends BaseMessage>> entry : supportedMessages.entrySet()) {
                    String type = entry.getKey();
                    if (type.equals(messageType)) {
                        Class<? extends BaseMessage> messageClass = entry.getValue();
                        BaseMessage returnedMessage = gson.fromJson(messageString, messageClass);

                        //Create a new thread for handling callback messages
                        new Thread(() ->{
                            try {
                                listener.callback(
                                        type,
                                        returnedMessage,
                                        (String) jsonPackage.get("sender_verkey")
                                );
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }).start();

                        new Thread(()->{
                            //Check to see if message has a thread ID and complete thread if so.
                            try{
                                ThreadDecorator thread = (ThreadDecorator) returnedMessage.getClass().getField("thread").get(returnedMessage);
                                this.threadHandler.completeThread(thread.thid, returnedMessage);
                            } catch (NoSuchFieldException | IllegalAccessException e) {
                                try{
                                    String threadId = (String) returnedMessage.getClass().getField("threadId").get(returnedMessage);
                                    this.threadHandler.completeThread(threadId, returnedMessage);
                                }catch (NoSuchFieldException | IllegalAccessException f){
                                    Log.d(TAG, "Message is not part of a thread");
                                }
                            }
                        }).start();

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


    private String parseDecorators(String stringMessage) {
        JsonObject messageJson = new JsonParser().parse(stringMessage).getAsJsonObject();
        Gson gson = new Gson();

        //Loop through message keys
        for (String key : messageJson.keySet()) {

            //~sig decorator
            if (key.endsWith("~sig")) {
                try {
                    SignatureDecorator sig = gson.fromJson(messageJson.get(key), SignatureDecorator.class);

                    //Needs to be decoded from base64
                    byte[] signedData = Base64.decode(sig.sigData, Base64.NO_WRAP | Base64.URL_SAFE);
                    byte[] signature = Base64.decode(sig.signature, Base64.NO_WRAP | Base64.URL_SAFE);

                    //Verify signature
                    boolean isValid = this.indyWallet.verify(sig.signer, signedData, signature);

                    if (!isValid) {
                        throw new IllegalAccessException();
                    }

                    //Replace the signed version with the unsigned
                    JsonObject parsedData = new JsonParser().parse(new String(signedData).substring(8)).getAsJsonObject(); //Need to remove first 8 bytes of signedData
                    messageJson.add(
                            key.replace("~sig", ""),
                            parsedData
                    );
                } catch (IllegalAccessException e) {
                    Log.e(TAG, "Invalid signature");
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }
        return messageJson.toString().replaceAll("\\\\", "");
    }

}
