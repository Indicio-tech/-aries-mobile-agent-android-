package tech.indicio.ariesmobileagentandroid.admin.credentials.eventRecords;

import com.google.gson.JsonObject;

import tech.indicio.ariesmobileagentandroid.admin.credentials.messages.CredentialOfferReceivedMessage;
import tech.indicio.ariesmobileagentandroid.admin.credentials.offerObjects.CredentialAttribute;
import tech.indicio.ariesmobileagentandroid.connections.ConnectionRecord;
import tech.indicio.ariesmobileagentandroid.storage.BaseRecord;

public class AdminCredentialOfferReceivedRecord extends BaseRecord {
    public static final String type = "admin_credential_offer_received";
    public ConnectionRecord adminConnection;
    public CredentialOfferReceivedMessage messageObject;
    public CredentialAttribute[] attributes;
    public String credentialExchangeId;
    public String connectionId;

    @Override
    public String getType(){return type;}

    public AdminCredentialOfferReceivedRecord(CredentialOfferReceivedMessage message, ConnectionRecord adminConnection){
        this.adminConnection = adminConnection;
        this.messageObject = message;
        this.attributes = message.credentialProposalDict.credentialProposal.attributes;
        this.connectionId = message.connectionId;
        this.credentialExchangeId = message.credentialExchangeId;
        this.id = message.id;
        this.tags = new JsonObject();
        tags.addProperty("adminConnection", adminConnection.id);
    }
}
