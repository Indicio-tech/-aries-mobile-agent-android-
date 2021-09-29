package tech.indicio.ariesmobileagentandroid.admin.connections.messages;

import com.google.gson.annotations.SerializedName;

import java.util.UUID;

import tech.indicio.ariesmobileagentandroid.admin.messages.BaseOutboundAdminMessage;

public class GetConnectionListMessage extends BaseOutboundAdminMessage {
    @SerializedName("@type")
    public final static String type = "https://github.com/hyperledger/aries-toolbox/tree/master/docs/admin-connections/0.1/get-list";

    @SerializedName("@id")
    public String id;

    public GetConnectionListMessage() {
        this.id = UUID.randomUUID().toString();
    }

}
