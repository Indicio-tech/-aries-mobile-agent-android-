package tech.indicio.ariesmobileagentandroid.admin.proofs.eventRecords;

import com.google.gson.JsonObject;

import tech.indicio.ariesmobileagentandroid.admin.proofs.messages.PresentationsListMessage;
import tech.indicio.ariesmobileagentandroid.admin.proofs.proofObjects.PresentationExchange;
import tech.indicio.ariesmobileagentandroid.connections.ConnectionRecord;
import tech.indicio.ariesmobileagentandroid.storage.BaseRecord;

public class AdminPresentationsListRecord extends BaseRecord {
    public static final String type = "admin_presentation_list";
    public ConnectionRecord adminConnection;
    public PresentationsListMessage messageObject;
    public PresentationExchange[] presentations;
    public String threadId;

    public AdminPresentationsListRecord(PresentationsListMessage message, ConnectionRecord adminConnection) {
        this.adminConnection = adminConnection;
        this.messageObject = message;
        this.presentations = message.results;
        this.id = message.id;
        this.tags = new JsonObject();
        tags.addProperty("adminConnection", adminConnection.id);
    }

    @Override
    public String getType() {
        return type;
    }

}
