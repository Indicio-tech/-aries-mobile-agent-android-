package tech.indicio.ariesmobileagentandroid.admin.basicMessaging.messages;

import com.google.gson.annotations.SerializedName;

import java.util.UUID;

import tech.indicio.ariesmobileagentandroid.admin.messages.BaseOutboundAdminMessage;
import tech.indicio.ariesmobileagentandroid.messaging.BaseMessage;

public class SendBasicMessage extends BaseOutboundAdminMessage {
    @SerializedName("@type")
    public final static String type = "https://github.com/hyperledger/aries-toolbox/tree/master/docs/admin-basicmessage/0.1/send";

    @SerializedName("@id")
    public String id;

    @SerializedName("connection_id")
    public String connectionId;

    public String content;


    public SendBasicMessage(String content, String connectionId){
        this.content = content;
        this.connectionId = connectionId;
        this.id = UUID.randomUUID().toString();
    }
}
