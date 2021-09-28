package tech.indicio.ariesmobileagentandroid.admin.credentials.eventRecords;

import com.google.gson.JsonObject;

import tech.indicio.ariesmobileagentandroid.admin.credentials.messages.CredentialReceivedMessage;
import tech.indicio.ariesmobileagentandroid.admin.credentials.offerObjects.CredentialAttribute;
import tech.indicio.ariesmobileagentandroid.connections.ConnectionRecord;
import tech.indicio.ariesmobileagentandroid.storage.BaseRecord;

public class AdminCredentialReceivedRecord extends BaseRecord {
    public static final String type = "admin_credential_received_record";
    public ConnectionRecord adminConnection;
    public CredentialReceivedMessage messageObject;
    public CredentialAttribute[] attributes;
    public String connectionId;
    public String credentialExchangeId;

    @Override
    public String getType(){return type;}

    public AdminCredentialReceivedRecord(CredentialReceivedMessage message, ConnectionRecord adminConnection){
        this.adminConnection = adminConnection;
        this.messageObject = message;
        this.attributes = message.credentialProposalDict.credentialProposal.attributes;
        this.connectionId = message.connectionId;
        this.id = message.id;
        this.credentialExchangeId = message.credentialExchangeId;
        this.tags = new JsonObject();
        tags.addProperty("adminConnection", adminConnection.id);
    }
}
