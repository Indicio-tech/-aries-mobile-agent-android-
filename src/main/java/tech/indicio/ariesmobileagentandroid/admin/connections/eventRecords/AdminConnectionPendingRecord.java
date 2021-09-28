package tech.indicio.ariesmobileagentandroid.admin.connections.eventRecords;

import com.google.gson.JsonObject;

import tech.indicio.ariesmobileagentandroid.admin.connections.messages.ConnectionMessage;
import tech.indicio.ariesmobileagentandroid.connections.ConnectionRecord;
import tech.indicio.ariesmobileagentandroid.storage.BaseRecord;

public class AdminConnectionPendingRecord extends BaseRecord {
    public static final String type = "admin_new_connection";
    public ConnectionRecord adminConnection;
    public ConnectionMessage messageObject;
    public String threadId;

    @Override
    public String getType() {
        return type;
    }

    public AdminConnectionPendingRecord(ConnectionMessage message, ConnectionRecord adminConnection){
        this.adminConnection = adminConnection;
        this.messageObject = message;
        this.threadId = message.thread.thid;
        this.id = message.connectionId;
        this.tags = new JsonObject();
        tags.addProperty("adminConnection", adminConnection.id);
    }
}
