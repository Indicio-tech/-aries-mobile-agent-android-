package tech.indicio.ariesmobileagentandroid.admin.basicMessaging.messages;

import com.google.gson.annotations.SerializedName;

import tech.indicio.ariesmobileagentandroid.admin.messages.BaseAdminConfirmationMessage;

public class SentBasicMessage extends BaseAdminConfirmationMessage {
    @SerializedName("@type")
    public final static String type = "https://github.com/hyperledger/aries-toolbox/tree/master/docs/admin-basicmessage/0.1/sent";

    @SerializedName("connection_id")
    public String connectionId;

    public AdminBasicMessage message;

    public String getType() {
        return type;
    }
}
