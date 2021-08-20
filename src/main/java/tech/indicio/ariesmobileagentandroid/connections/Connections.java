package tech.indicio.ariesmobileagentandroid.connections;

import android.net.Uri;
import android.util.Base64;
import android.util.Log;
import android.util.Pair;

import com.google.gson.Gson;

import org.hyperledger.indy.sdk.IndyException;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

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
    private final IndyWallet indyWallet;
    private final MessageSender messageSender;
    private final Storage storage;
    //Add supported message classes in constructor.
    private final HashMap<String, Class<? extends BaseMessage>> supportedMessages = new HashMap<>();
    //Test ConnectionRecord
    public ConnectionRecord testConnectionRecord = new ConnectionRecord(
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
            new JSONObject()
    );

    public Connections(IndyWallet indyWallet, MessageSender messageSender, Storage storage) {
        Log.d(TAG, "Creating Connections service");
        this.supportedMessages.put(ConnectionRequest.type, ConnectionRequest.class);
        this.indyWallet = indyWallet;
        this.messageSender = messageSender;
        this.storage = storage;
    }

    public HashMap<String, Class<? extends BaseMessage>> _getSupportedMessages() {
        return this.supportedMessages;
    }

    public void receiveInvitationUrl(String invitationUrl) throws Exception {
        Log.d(TAG, "Decoding invitation url: " + invitationUrl);
        Uri invitationUri = Uri.parse(invitationUrl);
        String encodedInvitation = invitationUri.getQueryParameter("c_i");

        byte[] invitationBytes = Base64.decode(encodedInvitation, Base64.NO_WRAP | Base64.URL_SAFE);
        String decodedInvitation = new String(invitationBytes);

        Gson gson = new Gson();
        InvitationMessage invitationMessage = gson.fromJson(decodedInvitation, InvitationMessage.class);
        Log.d(TAG, "Invitation message decoded:\n" + decodedInvitation);

        sendRequest(invitationMessage);
    }

    private void sendRequest(InvitationMessage invitationMessage) throws InterruptedException, ExecutionException, IndyException, JSONException {
        Log.d(TAG, "Creating Connection Request");

        ConnectionRecord connectionRecord = createConnection(invitationMessage);
        Connection connection = new Connection(connectionRecord.didDoc.id, connectionRecord.didDoc);

        ConnectionRequest connectionRequest = new ConnectionRequest(connectionRecord.alias, connection);

        this.messageSender.sendMessage(connectionRequest, connectionRecord);
    }

    private ConnectionRecord createConnection(InvitationMessage invitationMessage) throws InterruptedException, ExecutionException, IndyException, JSONException {
        try {
            Log.d(TAG, "Creating Connection");
            Log.d(TAG, "Creating DID and DIDDoc");
            Pair<String, String> didVerkey = this.indyWallet.generateDID();
            String did = didVerkey.first;
            String verkey = didVerkey.second;

            DIDDoc didDoc = DIDDoc.createDefaultDIDDoc(did, verkey);
            Log.d(TAG, "DIDDoc Created");

            //TODO determine when/where to store connectionRecord..
            ConnectionRecord connectionRecord = new ConnectionRecord(
                    UUID.randomUUID().toString(),
                    new Date(),
                    invitationMessage,
                    UUID.randomUUID().toString(),
                    ConnectionRecord.ConnectionState.REQUESTED,
                    true,
                    "invitee",
                    did,
                    didDoc,
                    verkey,
                    "AMAA Agent",
                    new JSONObject()
            );

            //Log connectionRecord
            Gson gson = new Gson();
            String stringRecord = new JSONObject(gson.toJson(connectionRecord)).toString(4);
            Log.d(TAG, "New connection record created:\n" + stringRecord);


            return connectionRecord;
        } catch (Exception e) {
            Log.e(TAG, "Error while creating Connection");
            throw e;
        }
    }

    @Override
    public void _callback(String type, BaseMessage message) {
        switch (type) {
            case InvitationMessage.type:
                invitationMessageHandler(message);
                break;
        }
    }

    private void invitationMessageHandler(BaseMessage message) {

    }

    public ConnectionRecord retrieveConnectionRecord(String id) throws IndyException, ExecutionException, JSONException, InterruptedException {
        try {
            return (ConnectionRecord) storage.retrieveRecord(ConnectionRecord.type, id);
        } catch (Exception e) {
            throw e;
        }
    }

}
