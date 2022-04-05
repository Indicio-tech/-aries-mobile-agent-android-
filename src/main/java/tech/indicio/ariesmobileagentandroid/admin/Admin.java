package tech.indicio.ariesmobileagentandroid.admin;

import android.util.Log;

import com.google.gson.JsonObject;

import org.hyperledger.indy.sdk.IndyException;
import org.json.JSONException;

import java.sql.Time;
import java.util.HashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;

import tech.indicio.ariesmobileagentandroid.admin.basicMessaging.AdminBasicMessaging;
import tech.indicio.ariesmobileagentandroid.admin.trustPing.AdminTrustPing;
import tech.indicio.ariesmobileagentandroid.admin.basicMessaging.eventRecords.AdminBasicMessageReceivedRecord;
import tech.indicio.ariesmobileagentandroid.admin.basicMessaging.eventRecords.AdminBasicMessagesRecord;
import tech.indicio.ariesmobileagentandroid.admin.basicMessaging.messages.DeleteBasicMessage;
import tech.indicio.ariesmobileagentandroid.admin.basicMessaging.messages.DeletedBasicMessage;
import tech.indicio.ariesmobileagentandroid.admin.basicMessaging.messages.NewBasicMessage;
import tech.indicio.ariesmobileagentandroid.admin.basicMessaging.messages.ReceivedBasicMessages;
import tech.indicio.ariesmobileagentandroid.admin.basicMessaging.messages.SentBasicMessage;
import tech.indicio.ariesmobileagentandroid.admin.connections.AdminConnections;
import tech.indicio.ariesmobileagentandroid.admin.connections.eventRecords.AdminConnectedRecord;
import tech.indicio.ariesmobileagentandroid.admin.connections.eventRecords.AdminConnectionListRecord;
import tech.indicio.ariesmobileagentandroid.admin.connections.eventRecords.AdminConnectionPendingRecord;
import tech.indicio.ariesmobileagentandroid.admin.connections.messages.ConnectedMessage;
import tech.indicio.ariesmobileagentandroid.admin.connections.messages.ConnectionListMessage;
import tech.indicio.ariesmobileagentandroid.admin.connections.messages.ConnectionMessage;
import tech.indicio.ariesmobileagentandroid.admin.connections.messages.DeletedConnectionMessage;
import tech.indicio.ariesmobileagentandroid.admin.credentials.AdminCredentials;
import tech.indicio.ariesmobileagentandroid.admin.credentials.eventRecords.AdminCredentialOfferReceivedRecord;
import tech.indicio.ariesmobileagentandroid.admin.credentials.eventRecords.AdminCredentialReceivedRecord;
import tech.indicio.ariesmobileagentandroid.admin.credentials.eventRecords.AdminCredentialsListReceivedRecord;
import tech.indicio.ariesmobileagentandroid.admin.credentials.messages.CredentialOfferReceivedMessage;
import tech.indicio.ariesmobileagentandroid.admin.credentials.messages.CredentialReceivedMessage;
import tech.indicio.ariesmobileagentandroid.admin.credentials.messages.CredentialsListMessage;
import tech.indicio.ariesmobileagentandroid.admin.messages.BaseAdminConfirmationMessage;
import tech.indicio.ariesmobileagentandroid.admin.proofs.AdminProofs;
import tech.indicio.ariesmobileagentandroid.admin.proofs.eventRecords.AdminMatchingCredentialsRecord;
import tech.indicio.ariesmobileagentandroid.admin.proofs.eventRecords.AdminPresentationsListRecord;
import tech.indicio.ariesmobileagentandroid.admin.proofs.messages.PresentationMatchingCredentialsMessage;
import tech.indicio.ariesmobileagentandroid.admin.proofs.messages.PresentationSentMessage;
import tech.indicio.ariesmobileagentandroid.admin.proofs.messages.PresentationsListMessage;
import tech.indicio.ariesmobileagentandroid.admin.trustPing.AdminTrustPing;
import tech.indicio.ariesmobileagentandroid.admin.trustPing.eventRecords.AdminTrustPingResponseRecord;
import tech.indicio.ariesmobileagentandroid.admin.trustPing.messages.AdminResponseReceivedTrustPing;
import tech.indicio.ariesmobileagentandroid.admin.trustPing.messages.AdminSentTrustPing;
import tech.indicio.ariesmobileagentandroid.connections.ConnectionRecord;
import tech.indicio.ariesmobileagentandroid.connections.Connections;
import tech.indicio.ariesmobileagentandroid.connections.messages.TrustPingMessage;
import tech.indicio.ariesmobileagentandroid.events.AriesEmitter;
import tech.indicio.ariesmobileagentandroid.events.AriesEvent;
import tech.indicio.ariesmobileagentandroid.events.AriesListener;
import tech.indicio.ariesmobileagentandroid.messaging.BaseMessage;
import tech.indicio.ariesmobileagentandroid.messaging.MessageListener;
import tech.indicio.ariesmobileagentandroid.messaging.MessageSender;
import tech.indicio.ariesmobileagentandroid.storage.BaseRecord;
import tech.indicio.ariesmobileagentandroid.storage.Storage;




public class Admin extends MessageListener {
    private static final String TAG = "AMAA-Admin";
    private final HashMap<String, Class<? extends BaseMessage>> supportedMessages = new HashMap<>();
    public ConnectionRecord adminConnection;
    public AdminConnections connections;
    public AdminBasicMessaging basicMessaging;
    public AdminCredentials credentials;
    public AdminProofs proofs;
    public AdminTrustPing trustPing;
    //For creating admin connection
    public boolean connectedToAdmin = false;
    private final MessageSender messageSender;
    private final Storage storage;
    private final Connections agentConnections;
    private final AriesEmitter eventEmitter;
    private String adminConnectionId;
    private String adminInvitationUrl;
    private final AdminListener adminListener;
    private final CountDownLatch latch = new CountDownLatch(1);

    public Admin(Storage storage, MessageSender messageSender, AriesEmitter eventEmitter, Connections agentConnections, String adminInvitationUrl) {
        this.messageSender = messageSender;
        this.agentConnections = agentConnections;
        this.storage = storage;
        this.eventEmitter = eventEmitter;
        this.adminListener = new AdminListener();
        this.eventEmitter.registerListener(this.adminListener);
        //Search for existing admin connection
        if (adminInvitationUrl != null) {
            try {
                this.connectToAdmin(adminInvitationUrl);
            } catch (Exception e) {
                Log.d(TAG, "No admin connection set");
            }
        }

        //BasicMessaging
        this.supportedMessages.put(DeletedBasicMessage.type, DeleteBasicMessage.class);
        this.supportedMessages.put(ReceivedBasicMessages.type, ReceivedBasicMessages.class);
        this.supportedMessages.put(NewBasicMessage.type, NewBasicMessage.class);
        this.supportedMessages.put(SentBasicMessage.type, SentBasicMessage.class);

        //Connections
        this.supportedMessages.put(ConnectionMessage.type, ConnectionMessage.class);
        this.supportedMessages.put(ConnectedMessage.type, ConnectedMessage.class);
        this.supportedMessages.put(DeletedConnectionMessage.type, DeletedConnectionMessage.class);
        this.supportedMessages.put(ConnectionListMessage.type, ConnectionListMessage.class);

        //Credentials
        this.supportedMessages.put(CredentialOfferReceivedMessage.type, CredentialOfferReceivedMessage.class);
        this.supportedMessages.put(CredentialReceivedMessage.type, CredentialReceivedMessage.class);
        this.supportedMessages.put(CredentialsListMessage.type, CredentialsListMessage.class);

        //Proofs
        this.supportedMessages.put(PresentationsListMessage.type, PresentationsListMessage.class);
        this.supportedMessages.put(PresentationMatchingCredentialsMessage.type, PresentationMatchingCredentialsMessage.class);
        this.supportedMessages.put(PresentationSentMessage.type, PresentationSentMessage.class);

        //Trust Ping
        this.supportedMessages.put(AdminResponseReceivedTrustPing.type, AdminResponseReceivedTrustPing.class);
        this.supportedMessages.put(AdminSentTrustPing.type, AdminSentTrustPing.class);

    }

    public Admin(Storage storage, MessageSender messageSender, AriesEmitter eventEmitter, Connections agentConnections) {
        this(storage, messageSender, eventEmitter, agentConnections, "default_admin_connection");
    }

    //TODO - Make behavior more explicit / separate portions to connections module?
    public void setAdminConnection(ConnectionRecord adminConnection, String connectionName) throws InterruptedException, ExecutionException, IndyException, JSONException {
        //Ensure ConnectionRecord is latest version
        adminConnection = agentConnections.retrieveConnectionRecord(adminConnection.id);

        //Remove tag from old connection
        Log.d(TAG, "Setting admin connection");
        try {
            ConnectionRecord oldConnection = this.retrieveAdminConnectionRecord(connectionName);
            oldConnection.tags.remove("admin_connection");
            this.storage.updateRecord(oldConnection);
            Log.d(TAG, "Removed tag from old connection record");
        } catch (Exception e) {
            Log.d(TAG, "Could not update old connection");
        }

        this.adminConnection = adminConnection;
        TrustPingMessage adminPing = new TrustPingMessage(false,"adminConnection","all");
        this.messageSender.sendMessage(adminPing, adminConnection);

        this.basicMessaging = new AdminBasicMessaging(this.messageSender, adminConnection);
        this.connections = new AdminConnections(this.messageSender, adminConnection);
        this.proofs = new AdminProofs(this.messageSender, adminConnection);
        this.credentials = new AdminCredentials(this.messageSender, adminConnection);
        this.trustPing = new AdminTrustPing(this.messageSender, adminConnection);

        Log.d(TAG, "Updating admin record tags");
        adminConnection.tags.addProperty("admin_connection", connectionName);
        Log.d(TAG, adminConnection.tags.toString());
        this.storage.updateRecord(adminConnection);
        Log.d(TAG, "Updated new record to have admin tag");
    }

    public void setAdminConnection(ConnectionRecord adminConnection) throws InterruptedException, ExecutionException, IndyException, JSONException {
        setAdminConnection(adminConnection, "default_admin_connection");
    }

    public void sendTrustPing() throws InterruptedException, ExecutionException, IndyException, JSONException {
        TrustPingMessage trustPing = new TrustPingMessage(false, "adminConnection", "all");
        this.messageSender.sendMessage(trustPing, this.adminConnection);
    }

    public ConnectionRecord connectToAdmin(String adminInvitationUrl) throws Exception {
        this.connectedToAdmin = false;
        this.adminInvitationUrl = adminInvitationUrl;
        try {
            //First check if already connected
            ConnectionRecord adminConnection = this.retrieveAdminConnectionRecord(adminInvitationUrl);
            this.connectedToAdmin = true;
            this.setAdminConnection(adminConnection, adminInvitationUrl);
            return adminConnection;
        } catch (Exception e) {
            Log.d(TAG, "Admin connection not found, creating connection");
            e.printStackTrace();
        }
        try {
            ConnectionRecord connection = this.agentConnections.receiveInvitationUrl(adminInvitationUrl);
            this.adminConnectionId = connection.id;
            latch.await();
            return this.agentConnections.retrieveConnectionRecord(this.adminConnectionId);
        } catch (Exception e) {
            Log.e(TAG, "Could not connect to admin");
            e.printStackTrace();
            throw e;
        }
    }

    private ConnectionRecord retrieveAdminConnectionRecord(String adminName) throws IndyException, ExecutionException, JSONException, InterruptedException {
        JsonObject query = new JsonObject();
        query.addProperty("admin_connection", adminName);
        Log.d(TAG, "Searching for adminConnection with invitation URL: " + adminName);
        return (ConnectionRecord) this.storage.retrieveRecordsByTags(ConnectionRecord.type, query, 1);
    }


    //Event functions
    @Override
    protected HashMap<String, Class<? extends BaseMessage>> getSupportedMessages() {
        return this.supportedMessages;
    }


    @Override
    protected void callback(String type, BaseMessage message, String senderVerkey) {
        BaseRecord record;
        Log.d(TAG, "Type: " + type);
        switch (type) {
            //Basic Messages
            case ReceivedBasicMessages.type:
                Log.d(TAG, "Admin received basic message list");
                record = new AdminBasicMessagesRecord((ReceivedBasicMessages) message, this.adminConnection);
                break;
            case NewBasicMessage.type:
                Log.d(TAG, "Admin received new basic message.");
                record = new AdminBasicMessageReceivedRecord((NewBasicMessage) message, this.adminConnection);
                break;
            //Connections
            case ConnectionMessage.type:
                Log.d(TAG, "Admin received connection message");
                record = new AdminConnectionPendingRecord((ConnectionMessage) message, this.adminConnection);
                break;
            case ConnectedMessage.type:
                Log.d(TAG, "Admin received connected message");
                record = new AdminConnectedRecord((ConnectedMessage) message, this.adminConnection);
                break;
            case ConnectionListMessage.type:
                Log.d(TAG, "Admin fetched connections list");
                record = new AdminConnectionListRecord((ConnectionListMessage) message, this.adminConnection);
                break;
            //Credentials
            case CredentialOfferReceivedMessage.type:
                Log.d(TAG, "Admin credential offer received");
                record = new AdminCredentialOfferReceivedRecord((CredentialOfferReceivedMessage) message, this.adminConnection);
                break;
            case CredentialReceivedMessage.type:
                Log.d(TAG, "Admin credential received");
                record = new AdminCredentialReceivedRecord((CredentialReceivedMessage) message, this.adminConnection);
                break;
            case CredentialsListMessage.type:
                Log.d(TAG, "Admin credentials list received");
                record = new AdminCredentialsListReceivedRecord((CredentialsListMessage) message, this.adminConnection);
                break;
            //Proofs
            case PresentationsListMessage.type:
                Log.d(TAG, "Admin presentations list received");
                record = new AdminPresentationsListRecord((PresentationsListMessage) message, this.adminConnection);
                break;
            case PresentationMatchingCredentialsMessage.type:
                Log.d(TAG, "Admin matching credentials received");
                record = new AdminMatchingCredentialsRecord((PresentationMatchingCredentialsMessage) message, this.adminConnection);
                break;
            //Trust ping Response Received
            case AdminResponseReceivedTrustPing.type:
                Log.d(TAG, "Admin Trust Ping Response Received");
                record = new AdminTrustPingResponseRecord((AdminResponseReceivedTrustPing) message, this.adminConnection);
                break;
            //Confirmation messages
            default:
                Log.d(TAG, "Admin confirmation event triggered");
                record = new AdminMessageConfirmationRecord((BaseAdminConfirmationMessage) message, this.adminConnection);
                break;
        }
        eventEmitter.emitEvent(new AriesEvent(record));
    }

    private class AdminListener implements AriesListener {
        public void onEvent(AriesEvent event) {
            switch (event.recordType) {
                case ConnectionRecord.type:
                    //For connecting to the admin
                    ConnectionRecord cRecord = (ConnectionRecord) event.updatedRecord;
                    if (cRecord.id.equals(adminConnectionId) && cRecord.state.equals(ConnectionRecord.ConnectionState.COMPLETE) && !connectedToAdmin) {
                        //When the admin connection is completed then set it as an admin connection.
                        try {
                            setAdminConnection(cRecord, adminInvitationUrl);
                            connectedToAdmin = true;
                            latch.countDown();
                            Log.d(TAG, "Admin connection completed");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    break;
            }
        }
    }

}
