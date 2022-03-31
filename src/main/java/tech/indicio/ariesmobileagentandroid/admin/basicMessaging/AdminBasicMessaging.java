package tech.indicio.ariesmobileagentandroid.admin.basicMessaging;

import org.hyperledger.indy.sdk.IndyException;
import org.json.JSONException;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.ExecutionException;

import tech.indicio.ariesmobileagentandroid.admin.AdminMessageConfirmationRecord;
import tech.indicio.ariesmobileagentandroid.admin.basicMessaging.eventRecords.AdminBasicMessagesRecord;
import tech.indicio.ariesmobileagentandroid.admin.basicMessaging.messages.DeleteBasicMessage;
import tech.indicio.ariesmobileagentandroid.admin.basicMessaging.messages.DeletedBasicMessage;
import tech.indicio.ariesmobileagentandroid.admin.basicMessaging.messages.GetBasicMessage;
import tech.indicio.ariesmobileagentandroid.admin.basicMessaging.messages.ReceivedBasicMessages;
import tech.indicio.ariesmobileagentandroid.admin.basicMessaging.messages.SendBasicMessage;
import tech.indicio.ariesmobileagentandroid.admin.basicMessaging.messages.SentBasicMessage;
import tech.indicio.ariesmobileagentandroid.connections.ConnectionRecord;
import tech.indicio.ariesmobileagentandroid.messaging.MessageSender;



public class AdminBasicMessaging {
    private final MessageSender messageSender;
    private ConnectionRecord adminConnection;

    public AdminBasicMessaging(MessageSender messageSender, ConnectionRecord adminConnection) {
        this.adminConnection = adminConnection;
        this.messageSender = messageSender;
    }

    protected void setAdminConnection(ConnectionRecord adminConnection) {
        this.adminConnection = adminConnection;
    }

    public CompletableFuture<AdminMessageConfirmationRecord> sendBasicMessage(String content, String connectionId) {
        return CompletableFuture.supplyAsync(() -> {
            try{
                SendBasicMessage message = new SendBasicMessage(content, connectionId);
                SentBasicMessage sentMessage =  (SentBasicMessage) this.messageSender.sendMessage(message, this.adminConnection).get();
                return new AdminMessageConfirmationRecord(sentMessage, adminConnection);
            }catch(Exception e){
                throw new CompletionException(e);
            }
        });
    }

    private CompletableFuture<AdminBasicMessagesRecord> sendGetBasicMessages(GetBasicMessage message){
        return CompletableFuture.supplyAsync(() ->{
            try{
                ReceivedBasicMessages returnedMessage = (ReceivedBasicMessages) this.messageSender.sendMessage(message, this.adminConnection).get();
                return new AdminBasicMessagesRecord(returnedMessage, this.adminConnection);
            }catch (Exception e){
                throw new CompletionException(e);
            }
        });
    }

    public CompletableFuture<AdminBasicMessagesRecord> sendGetAllBasicMessages() {
        GetBasicMessage message = new GetBasicMessage();
        return sendGetBasicMessages(message);
    }

    public CompletableFuture<AdminBasicMessagesRecord> sendGetBasicMessagesFromConnection(String connectionId) throws InterruptedException, ExecutionException, IndyException, JSONException {
        GetBasicMessage message = new GetBasicMessage(connectionId);
        return sendGetBasicMessages(message);
    }

    public CompletableFuture<AdminMessageConfirmationRecord> sendDeleteBasicMessage(String messageId) throws InterruptedException, ExecutionException, IndyException, JSONException {
        return CompletableFuture.supplyAsync(() -> {
            try{
                DeleteBasicMessage message = new DeleteBasicMessage(messageId);
                DeletedBasicMessage deletedMessage = (DeletedBasicMessage) this.messageSender.sendMessage(message, this.adminConnection).get();
                return new AdminMessageConfirmationRecord(deletedMessage, this.adminConnection);
            }catch (Exception e){
                throw new CompletionException(e);
            }
        });
    }
}
