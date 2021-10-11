package tech.indicio.ariesmobileagentandroid.admin.proofs.messages;

import com.google.gson.annotations.SerializedName;

import java.util.UUID;

import tech.indicio.ariesmobileagentandroid.admin.messages.BaseOutboundAdminMessage;

public class PresentationsGetListMessage extends BaseOutboundAdminMessage {
    @SerializedName("@type")
    public final static String type = "did:sov:BzCbsNYhMrjHiqZDTUASHg;spec/admin-holder/0.1/presentations-get-list";

    @SerializedName("@id")
    public String id;

    @SerializedName("connection_id")
    public String connectionId;


    public PresentationsGetListMessage() {
        this.id = UUID.randomUUID().toString();
    }

    public PresentationsGetListMessage(String connectionId) {
        this();
        this.connectionId = connectionId;
    }
}
