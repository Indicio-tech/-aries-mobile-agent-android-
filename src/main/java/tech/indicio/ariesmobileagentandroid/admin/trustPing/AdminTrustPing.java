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
import tech.indicio.ariesmobileagentandroid.admin.trustPing.messages.AdminSendTrustPing;
import tech.indicio.ariesmobileagentandroid.admin.trustPing.messages.AdminSentTrustPing;
import tech.indicio.ariesmobileagentandroid.connections.ConnectionRecord;
import tech.indicio.ariesmobileagentandroid.messaging.BaseMessage;
import tech.indicio.ariesmobileagentandroid.messaging.MessageSender;



public class AdminTrustPing extends BaseMessage {
    private final MessageSender messageSender;
    private ConnectionRecord adminConnection;

    public AdminTrustPing(MessageSender messageSender, ConnectionRecord adminConnection) {
        this.adminConnection = adminConnection;
        this.messageSender = messageSender;
    }

    protected void setAdminConnection(ConnectionRecord adminConnection) {
        this.adminConnection = adminConnection;
    }

    public CompletableFuture<AdminMessageConfirmationRecord> sendTrustPing(String comment, String connectionId){
        return CompletableFuture.supplyAsync(() ->{
            try{
                AdminSendTrustPing ping = new AdminSendTrustPing(comment, connectionId);
                AdminSentTrustPing sent = (AdminSentTrustPing) this.messageSender.sendMessage(ping, this.adminConnection).get();
                //need to create AdminTrustPingConfirmationRecord
                return new AdminMessageConfirmationRecord(sent, adminConnection);
            }catch (Exception e){
                throw new CompletionException(e);
            }
        });
    }
}
