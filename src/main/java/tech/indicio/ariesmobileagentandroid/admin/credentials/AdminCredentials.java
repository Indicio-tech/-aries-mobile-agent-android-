package tech.indicio.ariesmobileagentandroid.admin.credentials;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

import tech.indicio.ariesmobileagentandroid.admin.credentials.eventRecords.AdminCredentialReceivedRecord;
import tech.indicio.ariesmobileagentandroid.admin.credentials.eventRecords.AdminCredentialsListReceivedRecord;
import tech.indicio.ariesmobileagentandroid.admin.credentials.messages.CredentialOfferAcceptMessage;
import tech.indicio.ariesmobileagentandroid.admin.credentials.messages.CredentialReceivedMessage;
import tech.indicio.ariesmobileagentandroid.admin.credentials.messages.CredentialsListMessage;
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


    public CompletableFuture<AdminCredentialsListReceivedRecord> sendGetAllCredentials() {
        return CompletableFuture.supplyAsync(() -> {
            try{
                GetCredentialsListMessage message = new GetCredentialsListMessage();
                CredentialsListMessage listMessage = (CredentialsListMessage) this.messageSender.sendMessage(message, this.adminConnection).get();
                return new AdminCredentialsListReceivedRecord(listMessage, this.adminConnection);
            }catch (Exception e){
                throw new CompletionException(e);
            }
        });
    }

    public CompletableFuture<AdminCredentialReceivedRecord> sendAcceptCredentialOffer(String credentialExchangeId) {
        return CompletableFuture.supplyAsync(() -> {
            try{
                CredentialOfferAcceptMessage message = new CredentialOfferAcceptMessage(credentialExchangeId);
                CredentialReceivedMessage credentialReceivedMessage = (CredentialReceivedMessage) this.messageSender.sendMessage(message, this.adminConnection).get();
                return new AdminCredentialReceivedRecord(credentialReceivedMessage, this.adminConnection);
            }catch (Exception e){
                throw new CompletionException(e);
            }
        });
    }
}
