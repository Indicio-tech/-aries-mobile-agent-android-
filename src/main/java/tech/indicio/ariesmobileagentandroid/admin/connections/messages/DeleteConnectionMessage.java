package tech.indicio.ariesmobileagentandroid.admin.connections.messages;

import com.google.gson.annotations.SerializedName;

import java.util.UUID;

import tech.indicio.ariesmobileagentandroid.admin.messages.BaseOutboundAdminMessage;
import tech.indicio.ariesmobileagentandroid.messaging.BaseMessage;
import tech.indicio.ariesmobileagentandroid.messaging.decorators.TransportDecorator;

public class DeleteConnectionMessage extends BaseOutboundAdminMessage {
    @SerializedName("@type")
    public final static String type = "https://github.com/hyperledger/aries-toolbox/tree/master/docs/admin-connections/0.1/delete";

    @SerializedName("@id")
    public String id;

    @SerializedName("connection_id")
    public String connectionId;

    public DeleteConnectionMessage(String connectionId){
        this();
        this.connectionId = connectionId;
    }

    public DeleteConnectionMessage(){
        this.id = UUID.randomUUID().toString();
    }

}
