package tech.indicio.ariesmobileagentandroid.connections;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.HashMap;

import tech.indicio.ariesmobileagentandroid.messaging.BaseMessage;
import tech.indicio.ariesmobileagentandroid.messaging.MessageListener;

public class Connections extends MessageListener {

    private static final String TAG = "AMAA-Connections";

    //Add supported message classes in constructor.
    public HashMap<String, Class<? extends BaseMessage>> supportedMessages = new HashMap<>();

    public HashMap<String, Class<? extends BaseMessage>> getSupportedMessages(){
        return this.supportedMessages;
    }

    public Connections(){
        Log.d(TAG, "Creating Connections service");
        supportedMessages.put(InvitationMessage.type, InvitationMessage.class);

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String prettyJsonString = gson.toJson("{\"aaaaaaa\":\"aklfjklajfakljf\"}");
        Log.d(TAG, prettyJsonString);
    }


    public static String invitationJson = "{" +
            "\"@type\": \"https://didcomm.org/connections/1.0/invitation\"," +
            "\"@id\": \"12345678900987654321\"," +
            "\"label\": \"Alice\"," +
            "\"did\": \"did:sov:QmWbsNYhMrjHiqZDTUTEJs\"" +
            "}";

//    public static InvitationMessage receiveInvitation(String invitationJson){
//
//    }


    @Override
    public void callback(String type, BaseMessage message) {

    }
}
