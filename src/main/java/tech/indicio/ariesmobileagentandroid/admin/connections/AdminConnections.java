package tech.indicio.ariesmobileagentandroid.admin.connections;

import com.google.gson.JsonObject;

import org.hyperledger.indy.sdk.IndyException;
import org.json.JSONException;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import tech.indicio.ariesmobileagentandroid.admin.AdminMessageConfirmationRecord;
import tech.indicio.ariesmobileagentandroid.admin.connections.eventRecords.AdminConnectionListRecord;
import tech.indicio.ariesmobileagentandroid.admin.connections.eventRecords.AdminConnectionPendingRecord;
import tech.indicio.ariesmobileagentandroid.admin.connections.messages.ConnectionListMessage;
import tech.indicio.ariesmobileagentandroid.admin.connections.messages.ConnectionMessage;
import tech.indicio.ariesmobileagentandroid.admin.connections.messages.DeleteConnectionMessage;
import tech.indicio.ariesmobileagentandroid.admin.connections.messages.DeletedConnectionMessage;
import tech.indicio.ariesmobileagentandroid.admin.connections.messages.GetConnectionListMessage;
import tech.indicio.ariesmobileagentandroid.admin.connections.messages.ReceiveInvitationMessage;
import tech.indicio.ariesmobileagentandroid.admin.connections.messages.UpdateConnectionMessage;
import tech.indicio.ariesmobileagentandroid.connections.ConnectionRecord;
import tech.indicio.ariesmobileagentandroid.messaging.MessageSender;

public class AdminConnections {
    private final MessageSender messageSender;
    private ConnectionRecord adminConnection;

    public AdminConnections(MessageSender messageSender, ConnectionRecord adminConnection) {
        this.adminConnection = adminConnection;
        this.messageSender = messageSender;
    }

    protected void setAdminConnection(ConnectionRecord adminConnection) {
        this.adminConnection = adminConnection;
    }

    private CompletableFuture<AdminConnectionPendingRecord> sendReceiveInvitationMessage(ReceiveInvitationMessage message){
        return CompletableFuture.supplyAsync(() -> {
            try{
                ConnectionMessage connectionMessage = (ConnectionMessage) this.messageSender.sendMessage(message, this.adminConnection).get();
                return new AdminConnectionPendingRecord(connectionMessage, adminConnection);
            }catch (Exception e){
                throw new CompletionException(e);
            }
        });
    }

    public CompletableFuture<AdminConnectionPendingRecord> sendReceiveInvitationMessage(String invitationUrl, boolean autoAccept) {
        ReceiveInvitationMessage message = new ReceiveInvitationMessage(invitationUrl, autoAccept);
        return sendReceiveInvitationMessage(message);
    }

    public CompletableFuture<AdminConnectionPendingRecord> sendReceiveInvitationMessage(String invitationUrl, boolean autoAccept, String mediationUrl) {
        ReceiveInvitationMessage message = new ReceiveInvitationMessage(invitationUrl, autoAccept, mediationUrl);
        return sendReceiveInvitationMessage(message);
    }

    public CompletableFuture<AdminConnectionPendingRecord> sendReceiveInvitationMessage(String invitationUrl) {
        return sendReceiveInvitationMessage(invitationUrl, true);
    }

    public CompletableFuture<AdminConnectionListRecord> getAllConnections() {
        return CompletableFuture.supplyAsync(() -> {
            try{
                GetConnectionListMessage message = new GetConnectionListMessage();
                ConnectionListMessage listMessage = (ConnectionListMessage) this.messageSender.sendMessage(message, this.adminConnection).get();
                return new AdminConnectionListRecord(listMessage, this.adminConnection);
            }catch(Exception e){
                throw new CompletionException(e);
            }
        });
    }

    public CompletableFuture<AdminMessageConfirmationRecord> deleteConnection(String connectionId) {
        return CompletableFuture.supplyAsync(() -> {
           try{
               DeleteConnectionMessage message = new DeleteConnectionMessage(connectionId);
               DeletedConnectionMessage deletedMessage = (DeletedConnectionMessage) this.messageSender.sendMessage(message, this.adminConnection).get();
               return new AdminMessageConfirmationRecord(deletedMessage, adminConnection);
           }catch(Exception e){
               throw new CompletionException(e);
           }
        });
    }

    public CompletableFuture<Void> updateConnectionLabel(String connectionId, String newLabel) {
        return CompletableFuture.runAsync(() -> {
            try{
                JsonObject config = new JsonObject();
                config.addProperty("label", newLabel);
                UpdateConnectionMessage message = new UpdateConnectionMessage(connectionId, config);
                this.messageSender.sendMessage(message, this.adminConnection).get(15, TimeUnit.SECONDS);
            }catch(Exception e){
                throw new CompletionException(e);
            }
        });
    }

    public CompletableFuture<Void> updateConnectionRole(String connectionId, String newRole) {
        return CompletableFuture.runAsync(() -> {
            try{
                JsonObject config = new JsonObject();
                config.addProperty("role", newRole);
                UpdateConnectionMessage message = new UpdateConnectionMessage(connectionId, config);
                this.messageSender.sendMessage(message, this.adminConnection).get(15, TimeUnit.SECONDS);
            }catch(Exception e){
                throw new CompletionException(e);
            }
        });
    }

}
