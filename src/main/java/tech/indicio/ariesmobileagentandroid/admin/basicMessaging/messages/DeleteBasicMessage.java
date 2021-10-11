package tech.indicio.ariesmobileagentandroid.admin.basicMessaging.messages;

import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;

import java.util.UUID;

import tech.indicio.ariesmobileagentandroid.admin.messages.BaseOutboundAdminMessage;

public class DeleteBasicMessage extends BaseOutboundAdminMessage {
    @SerializedName("@type")
    public final static String type = "https://github.com/hyperledger/aries-toolbox/tree/master/docs/admin-basicmessage/0.1/delete";

    @SerializedName("connection_id")
    public String connectionId;

    @SerializedName("before_date")
    public String beforeDate;

    @SerializedName("message_id")
    public String messageId;

    //Deletes all basic messages
    public DeleteBasicMessage() {
        this.id = UUID.randomUUID().toString();
    }

    //Deletes single basic message
    public DeleteBasicMessage(String messageId) {
        this();
        this.messageId = messageId;
    }


    /**
     * @param config {
     *               String connectionId (Optional): Connection ID to target
     *               String beforeDate (Optional): Delete before this date
     *               String messageID (Optional): Message ID to be deleted
     *               }
     */
    public DeleteBasicMessage(JsonObject config) {
        this();
        if (config.has("connectionId"))
            this.connectionId = config.get("connectionId").toString();
        if (config.has("beforeDate"))
            this.beforeDate = config.get("beforeDate").toString();
        if (config.has("messageId"))
            this.messageId = config.get("messageId").toString();
    }


}
