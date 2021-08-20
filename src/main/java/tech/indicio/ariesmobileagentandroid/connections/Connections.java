package tech.indicio.ariesmobileagentandroid.connections;

import android.net.Uri;
import android.util.Base64;
import android.util.Log;
import android.util.Pair;

import com.google.gson.Gson;

import org.hyperledger.indy.sdk.IndyException;

import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

import tech.indicio.ariesmobileagentandroid.IndySdkRejectResponse;
import tech.indicio.ariesmobileagentandroid.IndyWallet;
import tech.indicio.ariesmobileagentandroid.connections.messages.InvitationMessage;
import tech.indicio.ariesmobileagentandroid.messaging.BaseMessage;
import tech.indicio.ariesmobileagentandroid.messaging.MessageListener;

public class Connections extends MessageListener {

    private static final String TAG = "AMAA-Connections";

    private IndyWallet indyWallet;

    //Add supported message classes in constructor.
    private HashMap<String, Class<? extends BaseMessage>> supportedMessages = new HashMap<>();

    public HashMap<String, Class<? extends BaseMessage>> _getSupportedMessages(){
        return this.supportedMessages;
    }

    public Connections(IndyWallet indyWallet){
        Log.d(TAG, "Creating Connections service");
        this.supportedMessages.put(InvitationMessage.type, InvitationMessage.class);
        this.indyWallet = indyWallet;
    }

    public static String invitationJson = "{" +
            "\"@type\": \"https://didcomm.org/connections/1.0/invitation\"," +
            "\"@id\": \"12345678900987654321\"," +
            "\"label\": \"Alice\"," +
            "\"did\": \"did:sov:QmWbsNYhMrjHiqZDTUTEJs\"" +
            "}";


    public void receiveInvitationUrl(String invitationUrl) throws MalformedURLException {
        Log.d(TAG, "Decoding invitation url: "+invitationUrl);
        Uri invitationUri = Uri.parse(invitationUrl);
        String encodedInvitation = invitationUri.getQueryParameter("c_i");

        byte[] invitationBytes = Base64.decode(encodedInvitation, Base64.NO_WRAP | Base64.URL_SAFE);
        String decodedInvitation = new String(invitationBytes);

        Gson gson = new Gson();
        InvitationMessage invitationMessage = gson.fromJson(decodedInvitation, InvitationMessage.class);
        Log.d(TAG, "Invitation message decoded:\n"+decodedInvitation);

    }

    private void sendRequest(InvitationMessage invitationMessage){

    }

    private void createConnection() throws InterruptedException, ExecutionException, IndyException {
        try{
            Pair<String, String> didVerkey = this.indyWallet.generateDID();
            String did = didVerkey.first;
            String verkey = didVerkey.second;


        }catch (Exception e) {
            IndySdkRejectResponse rejectResponse = new IndySdkRejectResponse(e);
            String code = rejectResponse.getCode();
            String json = rejectResponse.toJson();
            Log.e(TAG, "INDY ERROR");
            Log.e(TAG, code);
            Log.e(TAG, json);
            throw e;
        }
    }


    @Override
    public void _callback(String type, BaseMessage message) {
        switch(type){
            case InvitationMessage.type:
                invitationMessageHandler(message);
                break;
        }
    }

    private void invitationMessageHandler(BaseMessage message){

    }

}
