package tech.indicio.ariesmobileagentandroid.messaging;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MessageReceiver {
    private ArrayList<MessageListener> listeners = new ArrayList<>();
    private static final String TAG = "AMAA-MessageReceiver";


    public MessageReceiver(){
        Log.d(TAG, "Creating messageReceiver service");
    }

    public void receiveMessage(String message) throws JSONException {
        Gson gson = new GsonBuilder().excludeFieldsWithModifiers(java.lang.reflect.Modifier.TRANSIENT).create();
        JSONObject messageJson = new JSONObject(message);
        String messageType = messageJson.getString("@type");

        boolean handled = false;

        Log.d(TAG, "Message received of type: "+messageType+"\nMessage: "+messageJson.toString(4).replaceAll("\\\\", ""));

        //Check each listener to see if it supports message type
        for(MessageListener listener : listeners){
            //Get supported message types of listener
            HashMap<String, Class<? extends BaseMessage>> supportedMessages = listener.getSupportedMessages();
            for(Map.Entry<String, Class<? extends BaseMessage>> entry : supportedMessages.entrySet()){
                String type = entry.getKey();
                if(type.equals(messageType)){
                    Class<? extends BaseMessage> messageClass = entry.getValue();
                    listener.callback(
                            type,
                            gson.fromJson(message, messageClass)
                    );
                    handled = true;
                }
            }
        }

        if(!handled){
            Log.e(TAG, "Message received has no valid listeners.");
        }

    }

    public void registerListener(MessageListener listener){
        this.listeners.add(listener);
        Log.d(TAG, listener.getClass().getSimpleName()+" listener registered");
    }

}
