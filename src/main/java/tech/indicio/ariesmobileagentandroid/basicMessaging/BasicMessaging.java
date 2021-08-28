package tech.indicio.ariesmobileagentandroid.basicMessaging;

import android.util.Log;

import java.util.HashMap;

import tech.indicio.ariesmobileagentandroid.IndyWallet;
import tech.indicio.ariesmobileagentandroid.connections.messages.ConnectionRequest;
import tech.indicio.ariesmobileagentandroid.connections.messages.ConnectionResponse;
import tech.indicio.ariesmobileagentandroid.connections.messages.InvitationMessage;
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
    private final HashMap<String, Class<? extends BaseMessage>> supportedMessages = new HashMap<>();

    @Override
    public HashMap<String, Class<? extends BaseMessage>> _getSupportedMessages() {
        return this.supportedMessages;
    }

    public BasicMessaging(IndyWallet indyWallet, MessageSender messageSender, Storage storage){
        Log.d(TAG, "Creating BasicMessaging service");
        this.indyWallet = indyWallet;
        this.messageSender = messageSender;
        this.storage = storage;

        //Register supported messages
        this.supportedMessages.put(BasicMessage.type, BasicMessage.class);
    }

    @Override
    public void _callback(String type, BaseMessage message) {
        switch (type) {
            case InvitationMessage.type:
                BasicMessageReceiver((BasicMessage) message);
                break;
        }
    }

    private void BasicMessageReceiver(BasicMessage basicMessage){
        BasicMessageRecord basicMessageRecord = new BasicMessageRecord(
                basicMessage,
                BasicMessageRecord.BasicMessageRole.RECIEVED,
                "connectionId"
        );
    }
}
