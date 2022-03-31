package tech.indicio.ariesmobileagentandroid.admin.trustPing.eventRecords;

import com.google.gson.JsonObject;
import tech.indicio.ariesmobileagentandroid.admin.trustPing.messages.AdminResponseReceivedTrustPing;
import tech.indicio.ariesmobileagentandroid.admin.trustPing.messages.AdminSendTrustPing;
import tech.indicio.ariesmobileagentandroid.connections.ConnectionRecord;
import tech.indicio.ariesmobileagentandroid.storage.BaseRecord;

public class AdminTrustPingResponseRecord extends BaseRecord {
    public static final String type = "admin_trust_ping_response";
    public ConnectionRecord adminConnection;
    public AdminResponseReceivedTrustPing message;

    public AdminBasicMessageReceivedRecord(AdminResponseReceivedTrustPing message, ConnectionRecord adminConnection) {
        this.adminConnection = adminConnection;
        this.message = message;
        this.id = message.id;
        this.tags = new JsonObject();
        tags.addProperty("adminConnection", adminConnection.id);
    }

    @Override
    public String getType() {
        return type;
    }
}