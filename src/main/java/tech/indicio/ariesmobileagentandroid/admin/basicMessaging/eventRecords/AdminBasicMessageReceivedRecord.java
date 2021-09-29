package tech.indicio.ariesmobileagentandroid.admin.basicMessaging.eventRecords;

import com.google.gson.JsonObject;

import tech.indicio.ariesmobileagentandroid.admin.basicMessaging.messages.AdminBasicMessage;
import tech.indicio.ariesmobileagentandroid.admin.basicMessaging.messages.NewBasicMessage;
import tech.indicio.ariesmobileagentandroid.connections.ConnectionRecord;
import tech.indicio.ariesmobileagentandroid.storage.BaseRecord;

public class AdminBasicMessageReceivedRecord extends BaseRecord {
    public static final String type = "admin_new_basic_message";
    public ConnectionRecord adminConnection;
    public NewBasicMessage plainMessage;
    public AdminBasicMessage message;
    public String connectionId;

    public AdminBasicMessageReceivedRecord(NewBasicMessage message, ConnectionRecord adminConnection) {
        this.adminConnection = adminConnection;
        this.plainMessage = message;
        this.message = message.message;
        this.connectionId = message.connectionId;
        this.id = message.id;
        this.tags = new JsonObject();
        tags.addProperty("adminConnection", adminConnection.id);
    }

    @Override
    public String getType() {
        return type;
    }
}
