package tech.indicio.ariesmobileagentandroid.basicMessaging;

import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.util.Date;

import tech.indicio.ariesmobileagentandroid.connections.ConnectionRecord;
import tech.indicio.ariesmobileagentandroid.connections.diddoc.DIDDoc;
import tech.indicio.ariesmobileagentandroid.connections.messages.InvitationMessage;
import tech.indicio.ariesmobileagentandroid.messaging.BasicMessage;
import tech.indicio.ariesmobileagentandroid.storage.BaseRecord;

public class BasicMessageRecord extends BaseRecord {
    public static String type = "basic_message";

    //Record info
    public String id;
    public Date createdAt;

    //BasicMessage info
    public String sentTime;
    public String content;
    public BasicMessageRole role;
    public String connectionId;

    @Override
    public String getType() {
        return null;
    }

    public enum BasicMessageRole  {
        SENT,
        RECIEVED
    }

    public BasicMessageRecord(BasicMessage basicMessage, BasicMessageRole role, String connectionId) {
        this.id = basicMessage.id;
        this.createdAt = new Date();
        this.sentTime = basicMessage.sentTime;
        this.content = basicMessage.content;
        this.role = role;
        this.connectionId = connectionId;

        //Save connectionId as a tag so it's easier to search for by connection
        this.tags = new JsonObject();
        this.tags.addProperty("connectionId", connectionId);
    }
}
