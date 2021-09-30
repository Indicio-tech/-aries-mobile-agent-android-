package tech.indicio.ariesmobileagentandroid.admin.basicMessaging.messages;

import com.google.gson.annotations.SerializedName;

import tech.indicio.ariesmobileagentandroid.admin.messages.BaseAdminConfirmationMessage;

public class DeletedBasicMessage extends BaseAdminConfirmationMessage {
    @SerializedName("@type")
    public final static String type = "https://github.com/hyperledger/aries-toolbox/tree/master/docs/admin-basicmessage/0.1/deleted";

    @SerializedName("connection_id")
    public String connectionId;

    public AdminBasicMessage[] deleted;

    public String getType() {
        return type;
    }
}
