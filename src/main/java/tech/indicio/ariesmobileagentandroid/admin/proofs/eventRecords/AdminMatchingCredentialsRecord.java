package tech.indicio.ariesmobileagentandroid.admin.proofs.eventRecords;

import com.google.gson.JsonObject;

import tech.indicio.ariesmobileagentandroid.admin.proofs.messages.PresentationMatchingCredentialsMessage;
import tech.indicio.ariesmobileagentandroid.admin.proofs.proofObjects.MatchingCredentials;
import tech.indicio.ariesmobileagentandroid.connections.ConnectionRecord;
import tech.indicio.ariesmobileagentandroid.storage.BaseRecord;

public class AdminMatchingCredentialsRecord extends BaseRecord {
    public static final String type = "admin_presentation_matching_credentials";
    public ConnectionRecord adminConnection;
    public String threadId;
    public String presentationExchangeId;
    public PresentationMatchingCredentialsMessage messageObject;
    public MatchingCredentials[] matchingCredentials;

    public AdminMatchingCredentialsRecord(PresentationMatchingCredentialsMessage message, ConnectionRecord adminConnection) {
        this.adminConnection = adminConnection;
        this.messageObject = message;
        this.threadId = message.thread.thid;
        this.presentationExchangeId = message.presentationExchangeId;
        this.matchingCredentials = message.matchingCredentials;
        this.id = message.id;
        this.tags = new JsonObject();
        tags.addProperty("adminConnection", adminConnection.id);
    }

    @Override
    public String getType() {
        return type;
    }
}
