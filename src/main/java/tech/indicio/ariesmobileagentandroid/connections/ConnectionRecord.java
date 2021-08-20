package tech.indicio.ariesmobileagentandroid.connections;

import org.json.JSONObject;

import java.util.Date;

import tech.indicio.ariesmobileagentandroid.connections.diddoc.DIDDoc;
import tech.indicio.ariesmobileagentandroid.connections.messages.InvitationMessage;
import tech.indicio.ariesmobileagentandroid.storage.BaseRecord;

public class ConnectionRecord extends BaseRecord {
    public static String type = "connection";

    //Record info
    public Date createdAt;
    public InvitationMessage invitation;
    public String threadId;
    public ConnectionState state;
    public boolean autoAcceptConnection;

    //Our info
    public String role;
    public String did;
    public DIDDoc didDoc;
    public String verkey;
    public String alias;

    //Their info
    public String theirDid;
    public DIDDoc theirDidDoc;
    public String theirLabel;

    public enum ConnectionState{
        INVITED,
        REQUESTED,
        RESPONDED,
        COMPLETE
    }

    public String getType(){
        return type;
    }

    public ConnectionRecord(
            String id,
            Date createdAt,
            InvitationMessage invitation,
            String threadId,
            ConnectionState state,
            boolean autoAcceptConnection,
            String role,
            String did,
            DIDDoc didDoc,
            String verkey,
            String alias,
            String theirDid,
            DIDDoc theirDidDoc,
            String theirLabel,
            JSONObject tags
    ) {
        this.id = id;
        this.createdAt = createdAt;
        this.invitation = invitation;
        this.threadId = threadId;
        this.state = state;
        this.autoAcceptConnection = autoAcceptConnection;
        this.role = role;
        this.did = did;
        this.didDoc = didDoc;
        this.verkey = verkey;
        this.alias = alias;
        this.theirDid = theirDid;
        this.theirDidDoc = theirDidDoc;
        this.theirLabel = theirLabel;
        this.tags = tags;
    }
}
