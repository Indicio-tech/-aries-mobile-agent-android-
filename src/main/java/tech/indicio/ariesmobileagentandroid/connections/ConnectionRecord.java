package tech.indicio.ariesmobileagentandroid.connections;

import com.google.gson.JsonObject;

import java.util.Date;

import tech.indicio.ariesmobileagentandroid.connections.diddoc.DIDDoc;
import tech.indicio.ariesmobileagentandroid.connections.messages.InvitationMessage;
import tech.indicio.ariesmobileagentandroid.storage.BaseRecord;

public class ConnectionRecord extends BaseRecord {
    public static final String type = "connection";

    //Record info
    public Date createdAt;
    public InvitationMessage invitation;
    public ConnectionState state;
    public boolean autoAcceptConnection;

    //Our info
    public String role;
    public String did;
    public DIDDoc didDoc;
    public String verkey;
    public String label;

    //Their info
    public String theirDid;
    public DIDDoc theirDidDoc;
    public String theirLabel;
    public String threadId;



    //Constructor for Connections.receiveInvitation
    public ConnectionRecord(
            String id,
            Date createdAt,
            InvitationMessage invitation,
            ConnectionState state,
            boolean autoAcceptConnection,
            String role,
            String label,
            JsonObject tags
    ) {
        this.id = id;
        this.createdAt = createdAt;
        this.invitation = invitation;
        this.state = state;
        this.autoAcceptConnection = autoAcceptConnection;
        this.role = role;
        this.label = label;
        this.tags = tags;
    }

    //Constructor for Connections.createConnection
    public ConnectionRecord(
            String id,
            Date createdAt,
            InvitationMessage invitation,
            ConnectionState state,
            boolean autoAcceptConnection,
            String role,
            String did,
            DIDDoc didDoc,
            String verkey,
            String alias,
            JsonObject tags
    ) {
        this.id = id;
        this.createdAt = createdAt;
        this.invitation = invitation;
        this.state = state;
        this.autoAcceptConnection = autoAcceptConnection;
        this.role = role;
        this.did = did;
        this.didDoc = didDoc;
        this.verkey = verkey;
        this.label = label;
        this.tags = tags;
    }

    public String getType() {
        return type;
    }

    public enum ConnectionState {
        INVITED,
        REQUESTED,
        RESPONDED,
        COMPLETE
    }
}
