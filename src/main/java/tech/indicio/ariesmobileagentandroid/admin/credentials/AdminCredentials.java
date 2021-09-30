package tech.indicio.ariesmobileagentandroid.admin.credentials;

import org.hyperledger.indy.sdk.IndyException;
import org.json.JSONException;

import java.util.concurrent.ExecutionException;

import tech.indicio.ariesmobileagentandroid.admin.credentials.messages.CredentialOfferAcceptMessage;
import tech.indicio.ariesmobileagentandroid.admin.credentials.messages.GetCredentialsListMessage;
import tech.indicio.ariesmobileagentandroid.connections.ConnectionRecord;
import tech.indicio.ariesmobileagentandroid.messaging.MessageSender;

public class AdminCredentials {
    private final MessageSender messageSender;
    private ConnectionRecord adminConnection;

    public AdminCredentials(MessageSender messageSender, ConnectionRecord adminConnection) {
        this.messageSender = messageSender;
        this.adminConnection = adminConnection;
    }

    protected void setAdminConnection(ConnectionRecord adminConnection) {
        this.adminConnection = adminConnection;
    }


    public String sendGetAllCredentials() throws InterruptedException, ExecutionException, IndyException, JSONException {
        GetCredentialsListMessage message = new GetCredentialsListMessage();
        this.messageSender.sendMessage(message, this.adminConnection);
        return message.id;
    }

    public String sendAcceptCredentialOffer(String credentialExchangeId) throws InterruptedException, ExecutionException, IndyException, JSONException {
        CredentialOfferAcceptMessage message = new CredentialOfferAcceptMessage(credentialExchangeId);
        this.messageSender.sendMessage(message, this.adminConnection);
        return message.id;
    }
}
