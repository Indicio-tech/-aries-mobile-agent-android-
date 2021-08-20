package tech.indicio.ariesmobileagentandroid.connections;

import android.app.Service;
import android.net.Uri;
import android.util.Base64;
import android.util.Log;
import android.util.Pair;

import com.google.gson.Gson;

import org.hyperledger.indy.sdk.IndyException;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

import tech.indicio.ariesmobileagentandroid.IndySdkRejectResponse;
import tech.indicio.ariesmobileagentandroid.IndyWallet;
import tech.indicio.ariesmobileagentandroid.connections.diddoc.Authentication;
import tech.indicio.ariesmobileagentandroid.connections.diddoc.DIDDoc;
import tech.indicio.ariesmobileagentandroid.connections.diddoc.IndyService;
import tech.indicio.ariesmobileagentandroid.connections.diddoc.PublicKey;
import tech.indicio.ariesmobileagentandroid.connections.messages.ConnectionRequest;
import tech.indicio.ariesmobileagentandroid.connections.messages.InvitationMessage;
import tech.indicio.ariesmobileagentandroid.messaging.BaseMessage;
import tech.indicio.ariesmobileagentandroid.messaging.MessageListener;
import tech.indicio.ariesmobileagentandroid.messaging.MessageSender;
import tech.indicio.ariesmobileagentandroid.storage.Storage;

public class Connections extends MessageListener {

    private static final String TAG = "AMAA-Connections";

    private IndyWallet indyWallet;
    private MessageSender messageSender;
    private Storage storage;

    //Add supported message classes in constructor.
    private HashMap<String, Class<? extends BaseMessage>> supportedMessages = new HashMap<>();

    public HashMap<String, Class<? extends BaseMessage>> _getSupportedMessages(){
        return this.supportedMessages;
    }

    public Connections(IndyWallet indyWallet, MessageSender messageSender, Storage storage){
        Log.d(TAG, "Creating Connections service");
        this.supportedMessages.put(ConnectionRequest.type, ConnectionRequest.class);
        this.indyWallet = indyWallet;
        this.messageSender = messageSender;
        this.storage = storage;
    }


    public void receiveInvitationUrl(String invitationUrl) throws Exception {
        Log.d(TAG, "Decoding invitation url: "+invitationUrl);
        Uri invitationUri = Uri.parse(invitationUrl);
        String encodedInvitation = invitationUri.getQueryParameter("c_i");

        byte[] invitationBytes = Base64.decode(encodedInvitation, Base64.NO_WRAP | Base64.URL_SAFE);
        String decodedInvitation = new String(invitationBytes);

        Gson gson = new Gson();
        InvitationMessage invitationMessage = gson.fromJson(decodedInvitation, InvitationMessage.class);
        Log.d(TAG, "Invitation message decoded:\n"+decodedInvitation);

        sendRequest(invitationMessage);
    }

    private void sendRequest(InvitationMessage invitationMessage) throws InterruptedException, ExecutionException, IndyException, JSONException {
        Log.d(TAG, "Creating Connection Request");

        DIDDoc didDoc = createConnection();
        Connection connection = new Connection(didDoc.id, didDoc);
        ConnectionRequest connectionRequest = new ConnectionRequest("AMAA Agent", connection);

        this.messageSender.sendMessage(connectionRequest,
                invitationMessage.serviceEndpoint,
                didDoc.publicKey[0].publicKeyBase58,
                invitationMessage.recipientKeys,
                new String[]{});
    }

    private DIDDoc createConnection() throws InterruptedException, ExecutionException, IndyException {
        try{
            Log.d(TAG, "Creating Connection");
            Log.d(TAG, "Creating DID and DIDDoc");
            Pair<String, String> didVerkey = this.indyWallet.generateDID();
            String did = didVerkey.first;
            String verkey = didVerkey.second;

            DIDDoc didDoc = DIDDoc.createDefaultDIDDoc(did, verkey);
            Log.d(TAG, "DIDDoc Created");

            return didDoc;
        }catch (Exception e) {
            Log.e(TAG, "Error while creating Connection");
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


    //Test ConnectionRecord
    public ConnectionRecord testConnectionRecord= new ConnectionRecord(
            "uniqueID",
            new Date(),
            new InvitationMessage(
                    "invitationID",
                    "Invitation label!",
                    "serviceEndpoint",
                    new String[1],
                    new String[1]
            ),
            "unique threadId",
            ConnectionRecord.ConnectionState.COMPLETE,
            true,
            "invitee",
            "ourDIDDocId",
            new DIDDoc(
                    "context",
                    "ourDIDDocId",
                    new PublicKey[0],
                    new Authentication[0],
                    new IndyService[0]
            ),
            "ourVerkey",
            "ourAlias",
            "theirDidId",
            new DIDDoc(
                    "context",
                    "theirDidId",
                    new PublicKey[0],
                    new Authentication[0],
                    new IndyService[0]
            ),
            "theirLabel",
            new JSONObject()
    );

    public ConnectionRecord retrieveConnectionRecord(String id) throws IndyException, ExecutionException, JSONException, InterruptedException {
        try {
            return (ConnectionRecord) storage.retrieveRecord(ConnectionRecord.type, id);
        } catch (Exception e) {
           throw e;
        }
    }

}
