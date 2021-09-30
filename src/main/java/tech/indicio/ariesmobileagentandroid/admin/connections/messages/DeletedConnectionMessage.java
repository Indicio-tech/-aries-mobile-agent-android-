package tech.indicio.ariesmobileagentandroid.admin.connections.messages;

import com.google.gson.annotations.SerializedName;

import tech.indicio.ariesmobileagentandroid.admin.messages.BaseAdminConfirmationMessage;

public class DeletedConnectionMessage extends BaseAdminConfirmationMessage {
    @SerializedName("@type")
    public final static String type = "https://github.com/hyperledger/aries-toolbox/tree/master/docs/admin-connections/0.1/deleted";

    @SerializedName("connection_id")
    public String connectionId;

    public String getType() {
        return type;
    }
}