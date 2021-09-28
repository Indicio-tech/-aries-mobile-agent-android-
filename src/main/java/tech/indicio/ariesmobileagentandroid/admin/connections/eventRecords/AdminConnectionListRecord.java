package tech.indicio.ariesmobileagentandroid.admin.connections.eventRecords;

import com.google.gson.JsonObject;

import tech.indicio.ariesmobileagentandroid.admin.connections.AdminConnection;
import tech.indicio.ariesmobileagentandroid.admin.connections.messages.ConnectionListMessage;
import tech.indicio.ariesmobileagentandroid.connections.ConnectionRecord;
import tech.indicio.ariesmobileagentandroid.storage.BaseRecord;

public class AdminConnectionListRecord extends BaseRecord {
    public static final String type = "admin_connection_list";
    public ConnectionRecord adminConnection;
    public ConnectionListMessage messageObject;
    public AdminConnection[] connections;
    public String threadId;

    @Override
    public String getType() {
        return type;
    }

    public AdminConnectionListRecord(ConnectionListMessage message, ConnectionRecord adminConnection){
        this.adminConnection = adminConnection;
        this.messageObject = message;
        this.connections = message.connections;
        this.threadId = message.thread.thid;
        this.id = message.id;
        this.tags = new JsonObject();
        tags.addProperty("adminConnection", adminConnection.id);
    }

}
