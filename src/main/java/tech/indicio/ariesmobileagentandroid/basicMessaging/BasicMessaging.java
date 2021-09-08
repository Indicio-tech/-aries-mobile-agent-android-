package tech.indicio.ariesmobileagentandroid.basicMessaging;

import android.util.Log;

import org.hyperledger.indy.sdk.IndyException;
import org.json.JSONException;

import java.util.HashMap;
import java.util.concurrent.ExecutionException;

import tech.indicio.ariesmobileagentandroid.IndyWallet;
import tech.indicio.ariesmobileagentandroid.connections.ConnectionRecord;
import tech.indicio.ariesmobileagentandroid.connections.Connections;
import tech.indicio.ariesmobileagentandroid.messaging.BaseMessage;
import tech.indicio.ariesmobileagentandroid.messaging.BasicMessage;
import tech.indicio.ariesmobileagentandroid.messaging.MessageListener;
import tech.indicio.ariesmobileagentandroid.messaging.MessageSender;
import tech.indicio.ariesmobileagentandroid.storage.Storage;

public class BasicMessaging extends MessageListener {
    private static final String TAG = "AMAA-BasicMessaging";

    private final IndyWallet indyWallet;
    private final MessageSender messageSender;
    private final Storage storage;
    private final Connections connections;
    private final HashMap<String, Class<? extends BaseMessage>> supportedMessages = new HashMap<>();

    @Override
    public HashMap<String, Class<? extends BaseMessage>> _getSupportedMessages() {
        return this.supportedMessages;
    }

    public BasicMessaging(IndyWallet indyWallet, MessageSender messageSender, Storage storage, Connections connections) {
        Log.d(TAG, "Creating BasicMessaging service");
        this.indyWallet = indyWallet;
        this.messageSender = messageSender;
        this.storage = storage;
        this.connections = connections;

        //Register supported messages
        this.supportedMessages.put(BasicMessage.type, BasicMessage.class);
    }

    public void sendBasicMessage(String message, ConnectionRecord connectionRecord) throws InterruptedException, ExecutionException, IndyException, JSONException {
        BasicMessage basicMessage = new BasicMessage(message);
        BasicMessageRecord basicMessageRecord = new BasicMessageRecord(basicMessage, BasicMessageRecord.BasicMessageRole.SENT, connectionRecord.id);
        this.storage.storeRecord(basicMessageRecord);
        this.messageSender.sendMessage(basicMessage, connectionRecord);
    }

    @Override
    public void _callback(String type, BaseMessage message, String senderVerkey) {
        try {
            Log.d(TAG, "Sender verkey: " + senderVerkey);
            ConnectionRecord senderConnection = this.connections.retrieveConnectionRecordByVerkey(senderVerkey);
            switch (type) {
                case BasicMessage.type:
                    BasicMessageReceiver((BasicMessage) message, senderConnection);
                    break;
            }
        } catch (IndyException | ExecutionException | InterruptedException | JSONException e) {
            Log.e(TAG, "Could not retrieve connection for this message");
            e.printStackTrace();
        }
    }

    private void BasicMessageReceiver(BasicMessage basicMessage, ConnectionRecord senderConnection) throws InterruptedException, ExecutionException, IndyException {
        BasicMessageRecord basicMessageRecord = new BasicMessageRecord(
                basicMessage,
                BasicMessageRecord.BasicMessageRole.RECEIVED,
                senderConnection.id
        );
        this.storage.storeRecord(basicMessageRecord);
    }
}
