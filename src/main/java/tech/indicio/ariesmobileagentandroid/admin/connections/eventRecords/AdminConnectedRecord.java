package tech.indicio.ariesmobileagentandroid.admin.connections.eventRecords;

import com.google.gson.JsonObject;

import tech.indicio.ariesmobileagentandroid.admin.connections.messages.ConnectedMessage;
import tech.indicio.ariesmobileagentandroid.connections.ConnectionRecord;
import tech.indicio.ariesmobileagentandroid.storage.BaseRecord;

public class AdminConnectedRecord extends BaseRecord {
    public static final String type = "admin_connected";
    public ConnectionRecord adminConnection;
    public ConnectedMessage messageObject;
    public String threadId;
    public String connectionId;
    public String theirDid;
    public String myDid;
    public String state;
    public String label;

    public AdminConnectedRecord(ConnectedMessage message, ConnectionRecord adminConnection) {
        this.adminConnection = adminConnection;

        this.messageObject = message;
        this.id = message.id;
        this.connectionId = message.connectionId;
        this.theirDid = message.theirDid;
        this.myDid = message.myDid;
        this.state = message.state;
        this.label = message.label;

        this.tags = new JsonObject();
        tags.addProperty("adminConnection", adminConnection.id);
    }

    @Override
    public String getType() {
        return type;
    }

}