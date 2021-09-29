package tech.indicio.ariesmobileagentandroid.admin.connections;

import com.google.gson.JsonObject;

import org.hyperledger.indy.sdk.IndyException;
import org.json.JSONException;

import java.util.concurrent.ExecutionException;

import tech.indicio.ariesmobileagentandroid.admin.connections.messages.DeleteConnectionMessage;
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

    public void _setAdminConnection(ConnectionRecord adminConnection) {
        this.adminConnection = adminConnection;
    }


    public String sendReceiveInvitationMessage(String invitationUrl, boolean autoAccept) throws InterruptedException, ExecutionException, IndyException, JSONException {
        ReceiveInvitationMessage message = new ReceiveInvitationMessage(invitationUrl, autoAccept);
        this.messageSender.sendMessage(message, this.adminConnection);
        return message.id;
    }

    public String sendReceiveInvitationMessage(String invitationUrl, boolean autoAccept, String mediationUrl) throws InterruptedException, ExecutionException, IndyException, JSONException {
        ReceiveInvitationMessage message = new ReceiveInvitationMessage(invitationUrl, autoAccept, mediationUrl);
        this.messageSender.sendMessage(message, this.adminConnection);
        return message.id;
    }

    public String sendReceiveInvitationMessage(String invitationUrl) throws InterruptedException, ExecutionException, IndyException, JSONException {
        return sendReceiveInvitationMessage(invitationUrl, true);
    }

    public String getAllConnections() throws InterruptedException, ExecutionException, IndyException, JSONException {
        GetConnectionListMessage message = new GetConnectionListMessage();
        this.messageSender.sendMessage(message, this.adminConnection);
        return message.id;
    }

    public String deleteConnection(String connectionId) throws InterruptedException, ExecutionException, IndyException, JSONException {
        DeleteConnectionMessage message = new DeleteConnectionMessage(connectionId);
        this.messageSender.sendMessage(message, this.adminConnection);
        return message.id;
    }

    public String updateConnectionLabel(String connectionId, String newLabel) throws InterruptedException, ExecutionException, IndyException, JSONException {
        JsonObject config = new JsonObject();
        config.addProperty("label", newLabel);
        UpdateConnectionMessage message = new UpdateConnectionMessage(connectionId, config);
        this.messageSender.sendMessage(message, this.adminConnection);
        return message.id;
    }

    public String updateConnectionRole(String connectionId, String newRole) throws InterruptedException, ExecutionException, IndyException, JSONException {
        JsonObject config = new JsonObject();
        config.addProperty("role", newRole);
        UpdateConnectionMessage message = new UpdateConnectionMessage(connectionId, config);
        this.messageSender.sendMessage(message, this.adminConnection);
        return message.id;
    }

}
