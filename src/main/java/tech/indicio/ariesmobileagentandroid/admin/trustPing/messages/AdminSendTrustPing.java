package tech.indicio.ariesmobileagentandroid.admin.trustPing.messages;

import com.google.gson.annotations.SerializedName;

import tech.indicio.ariesmobileagentandroid.messaging.BaseMessage;

public class AdminSendTrustPing extends BaseMessage{

    @SerializedName("@type")
    public final static String type = "https://github.com/hyperledger/aries-toolbox/tree/master/docs/admin-trustping/0.1/send";

    @SerializedName("connection_id")
    public String connectionId;

    @SerializedName("comment")
    public String comment;

    public AdminSendTrustPing(String comment, String connectionId){
        super();
        this.comment = comment;
        this.connectionId = connectionId;
    }

    public AdminSendTrustPing(String connectionId){
        super();
        this.connectionId = connectionId;
    }

    public String getType(){ return this.type;}
}
