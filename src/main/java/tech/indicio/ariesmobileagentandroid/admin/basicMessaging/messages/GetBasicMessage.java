package tech.indicio.ariesmobileagentandroid.admin.basicMessaging.messages;

import com.google.gson.annotations.SerializedName;

import java.util.UUID;

import tech.indicio.ariesmobileagentandroid.admin.messages.BaseOutboundAdminMessage;
import tech.indicio.ariesmobileagentandroid.messaging.BaseMessage;
import tech.indicio.ariesmobileagentandroid.messaging.BasicMessage;
import tech.indicio.ariesmobileagentandroid.messaging.decorators.TransportDecorator;

public class GetBasicMessage extends BaseOutboundAdminMessage {
    @SerializedName("@type")
    public final static String type = "https://github.com/hyperledger/aries-toolbox/tree/master/docs/admin-basicmessage/0.1/get";

    @SerializedName("@id")
    public String id;

    @SerializedName("connection_id")
    public String connectionId;

    public int limit;
    public int offset;

    public GetBasicMessage(){
        this.id = UUID.randomUUID().toString();
    }

    public GetBasicMessage(String connectionId){
        this();
        this.connectionId = connectionId;
    }

    public GetBasicMessage(int limit){
        this();
        this.limit = limit;
    }

    public GetBasicMessage(int limit, int offset){
        this(limit);
        this.offset = offset;
    }

    public GetBasicMessage(String connectionId, int limit){
        this(connectionId);
        this.limit = limit;
    }

    public GetBasicMessage(String connectionId, int limit, int offset){
        this(limit, offset);
        this.connectionId = connectionId;
    }

}
