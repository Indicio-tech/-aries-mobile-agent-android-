package tech.indicio.ariesmobileagentandroid.connections.messages;

import com.google.gson.annotations.SerializedName;

import java.util.UUID;

import tech.indicio.ariesmobileagentandroid.messaging.BaseMessage;

public class InvitationMessage extends BaseMessage {

    @SerializedName("@type")
    public final static String type = "https://didcomm.org/connections/1.0/invitation";

    @SerializedName("@id")
    public String id = UUID.randomUUID().toString();
    public String label;
    public String serviceEndpoint;
    public String[] recipientKeys;
    public String[] routingKeys;


    public InvitationMessage(
            String id,
            String label,
            String serviceEndpoint,
            String[] recipientKeys,
            String[] routingKeys
    ){
        this.id = id;
        this.label = label;
        this.serviceEndpoint = serviceEndpoint;
        this.recipientKeys = recipientKeys;
        this.routingKeys  = routingKeys;
    }


}