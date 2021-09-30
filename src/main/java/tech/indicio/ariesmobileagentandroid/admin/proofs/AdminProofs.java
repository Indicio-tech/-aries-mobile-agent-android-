package tech.indicio.ariesmobileagentandroid.admin.proofs;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

import tech.indicio.ariesmobileagentandroid.admin.AdminMessageConfirmationRecord;
import tech.indicio.ariesmobileagentandroid.admin.proofs.eventRecords.AdminMatchingCredentialsRecord;
import tech.indicio.ariesmobileagentandroid.admin.proofs.eventRecords.AdminPresentationsListRecord;
import tech.indicio.ariesmobileagentandroid.admin.proofs.messages.PresentationGetMatchingCredentialsMessage;
import tech.indicio.ariesmobileagentandroid.admin.proofs.messages.PresentationMatchingCredentialsMessage;
import tech.indicio.ariesmobileagentandroid.admin.proofs.messages.PresentationRequestApproveMessage;
import tech.indicio.ariesmobileagentandroid.admin.proofs.messages.PresentationSentMessage;
import tech.indicio.ariesmobileagentandroid.admin.proofs.messages.PresentationsGetListMessage;
import tech.indicio.ariesmobileagentandroid.admin.proofs.messages.PresentationsListMessage;
import tech.indicio.ariesmobileagentandroid.admin.proofs.proofObjects.PresentationRequest;
import tech.indicio.ariesmobileagentandroid.connections.ConnectionRecord;
import tech.indicio.ariesmobileagentandroid.messaging.MessageSender;

public class AdminProofs {

    private final MessageSender messageSender;
    private ConnectionRecord adminConnection;

    public AdminProofs(MessageSender messageSender, ConnectionRecord adminConnection) {
        this.messageSender = messageSender;
        this.adminConnection = adminConnection;
    }

    protected void setAdminConnection(ConnectionRecord adminConnection) {
        this.adminConnection = adminConnection;
    }

    //TODO - PresentationsListMessage does not contain thread information
    public CompletableFuture<Void> sendGetPresentations() {
        return CompletableFuture.runAsync(() -> {
            try{
                PresentationsGetListMessage message = new PresentationsGetListMessage();
                this.messageSender.sendMessage(message, this.adminConnection);
            }catch(Exception e){
                throw new CompletionException(e);
            }
        });
    }

    //TODO - PresentationsListMessage does not contain thread information
    public CompletableFuture<Void> sendGetPresentationsByConnection(String connectionId) {
        return CompletableFuture.runAsync(() -> {
            try{
                PresentationsGetListMessage message = new PresentationsGetListMessage(connectionId);
                this.messageSender.sendMessage(message, this.adminConnection);
            }catch(Exception e){
                throw new CompletionException(e);
            }
        });
    }

    public CompletableFuture<AdminMatchingCredentialsRecord> sendGetMatchingCredentials(String presentationExchangeId) {
        return CompletableFuture.supplyAsync(() -> {
            try{
                PresentationGetMatchingCredentialsMessage message = new PresentationGetMatchingCredentialsMessage(presentationExchangeId);
                PresentationMatchingCredentialsMessage matchingMessage = (PresentationMatchingCredentialsMessage) this.messageSender.sendMessage(message, this.adminConnection).get();
                return new AdminMatchingCredentialsRecord(matchingMessage, this.adminConnection);
            }catch(Exception e){
                throw new CompletionException(e);
            }
        });
    }

    public CompletableFuture<AdminMessageConfirmationRecord> sendAcceptRequest(AdminMatchingCredentialsRecord record, PresentationRequest presentationRequest){
        return CompletableFuture.supplyAsync(() -> {
            try{
                PresentationRequestApproveMessage message = new PresentationRequestApproveMessage(record, presentationRequest);
                PresentationSentMessage sentMessage = (PresentationSentMessage) this.messageSender.sendMessage(message, this.adminConnection).get();
                return new AdminMessageConfirmationRecord(sentMessage, adminConnection);
            }catch(Exception e){
                throw new CompletionException(e);
            }
        });
    }
}
