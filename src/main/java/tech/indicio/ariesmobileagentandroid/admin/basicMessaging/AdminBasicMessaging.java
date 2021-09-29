package tech.indicio.ariesmobileagentandroid.admin.basicMessaging;

import org.hyperledger.indy.sdk.IndyException;
import org.json.JSONException;

import java.util.concurrent.ExecutionException;

import tech.indicio.ariesmobileagentandroid.admin.basicMessaging.messages.DeleteBasicMessage;
import tech.indicio.ariesmobileagentandroid.admin.basicMessaging.messages.GetBasicMessage;
import tech.indicio.ariesmobileagentandroid.admin.basicMessaging.messages.SendBasicMessage;
import tech.indicio.ariesmobileagentandroid.connections.ConnectionRecord;
import tech.indicio.ariesmobileagentandroid.messaging.MessageSender;

public class AdminBasicMessaging {
    private final MessageSender messageSender;
    private ConnectionRecord adminConnection;

    public AdminBasicMessaging(MessageSender messageSender, ConnectionRecord adminConnection) {
        this.adminConnection = adminConnection;
        this.messageSender = messageSender;
    }

    public void _setAdminConnection(ConnectionRecord adminConnection) {
        this.adminConnection = adminConnection;
    }

    public String sendBasicMessage(String content, String connectionId) throws InterruptedException, ExecutionException, IndyException, JSONException {
        SendBasicMessage message = new SendBasicMessage(content, connectionId);
        this.messageSender.sendMessage(message, this.adminConnection);
        return message.id;
    }

    public String sendGetAllBasicMessages() throws InterruptedException, ExecutionException, IndyException, JSONException {
        GetBasicMessage message = new GetBasicMessage();
        this.messageSender.sendMessage(message, this.adminConnection);
        return message.id;
    }

    public String sendGetBasicMessagesFromConnection(String connectionId) throws InterruptedException, ExecutionException, IndyException, JSONException {
        GetBasicMessage message = new GetBasicMessage(connectionId);
        this.messageSender.sendMessage(message, this.adminConnection);
        return message.id;
    }

    public String sendDeleteBasicMessage(String messageId) throws InterruptedException, ExecutionException, IndyException, JSONException {
        DeleteBasicMessage message = new DeleteBasicMessage(messageId);
        this.messageSender.sendMessage(message, this.adminConnection);
        return message.id;
    }
}
