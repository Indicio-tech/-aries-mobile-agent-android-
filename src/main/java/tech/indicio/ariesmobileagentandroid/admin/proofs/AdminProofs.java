package tech.indicio.ariesmobileagentandroid.admin.proofs;

import org.hyperledger.indy.sdk.IndyException;
import org.json.JSONException;

import java.util.concurrent.ExecutionException;

import tech.indicio.ariesmobileagentandroid.admin.proofs.messages.PresentationsGetListMessage;
import tech.indicio.ariesmobileagentandroid.connections.ConnectionRecord;
import tech.indicio.ariesmobileagentandroid.messaging.MessageSender;

public class AdminProofs {

    private MessageSender messageSender;
    private ConnectionRecord adminConnection;

    public AdminProofs(MessageSender messageSender, ConnectionRecord adminConnection){
        this.messageSender = messageSender;
        this.adminConnection = adminConnection;
    }

    public void _setAdminConnection(ConnectionRecord adminConnection){
        this.adminConnection = adminConnection;
    }

    public String sendGetPresentations() throws InterruptedException, ExecutionException, IndyException, JSONException {
        PresentationsGetListMessage message = new PresentationsGetListMessage();
        this.messageSender.sendMessage(message, this.adminConnection);
        return message.id;
    }

    public String sendGetPresentationsByConnection(String connectionId) throws InterruptedException, ExecutionException, IndyException, JSONException {
        PresentationsGetListMessage message = new PresentationsGetListMessage(connectionId);
        this.messageSender.sendMessage(message, this.adminConnection);
        return message.id;
    }
}
