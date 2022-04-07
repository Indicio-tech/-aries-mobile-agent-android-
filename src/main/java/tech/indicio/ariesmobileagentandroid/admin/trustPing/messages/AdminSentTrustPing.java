package tech.indicio.ariesmobileagentandroid.admin.trustPing.messages;

import com.google.gson.annotations.SerializedName;

import tech.indicio.ariesmobileagentandroid.admin.messages.BaseAdminConfirmationMessage;

public class AdminSentTrustPing extends BaseAdminConfirmationMessage {
    @SerializedName("@type")
    public final static String type = "https://github.com/hyperledger/aries-toolbox/tree/master/docs/admin-trustping/0.1/sent";

    @SerializedName("connection_id")
    public String connectionId;

    public String getType() {
        return type;
    }
}