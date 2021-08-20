package tech.indicio.ariesmobileagentandroid.connections;

import com.google.gson.annotations.SerializedName;

import tech.indicio.ariesmobileagentandroid.messaging.BaseMessage;

public class InvitationMessage extends BaseMessage {

    @SerializedName("@type")
    public final static String type = "https://didcomm.org/connections/1.0/invitation";

    @SerializedName("@id")
    public String id;

    public String label;
    public String did;

    public InvitationMessage(
            String id,
            String label,
            String did
    ){
        this.id = id;
        this.label = label;
        this.did = did;
    }
}