package tech.indicio.ariesmobileagentandroid.admin.credentials.eventRecords;

import com.google.gson.JsonObject;

import tech.indicio.ariesmobileagentandroid.admin.credentials.messages.CredentialsListMessage;
import tech.indicio.ariesmobileagentandroid.admin.credentials.offerObjects.CredentialExchangeItem;
import tech.indicio.ariesmobileagentandroid.connections.ConnectionRecord;
import tech.indicio.ariesmobileagentandroid.storage.BaseRecord;

public class AdminCredentialsListReceivedRecord extends BaseRecord {
    public static final String type = "admin_credentials_list_received_record";
    public ConnectionRecord adminConnection;
    public String threadId;
    public CredentialExchangeItem[] results;
    public CredentialsListMessage messageObject;

    @Override
    public String getType() {return type;}

    public AdminCredentialsListReceivedRecord(CredentialsListMessage message, ConnectionRecord adminConnection){
        this.adminConnection = adminConnection;
        this.messageObject = message;
        this.threadId = message.thread.thid;
        this.results = message.results;
        this.id = message.id;
        this.tags = new JsonObject();
        tags.addProperty("adminConnection", adminConnection.id);
    }
}
