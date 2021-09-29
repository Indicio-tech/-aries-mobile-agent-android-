package tech.indicio.ariesmobileagentandroid.admin.proofs.messages;

import com.google.gson.annotations.SerializedName;

import tech.indicio.ariesmobileagentandroid.admin.messages.BaseAdminConfirmationMessage;

public class PresentationSentMessage extends BaseAdminConfirmationMessage {
    @SerializedName("@type")
    public final static String type = "did:sov:BzCbsNYhMrjHiqZDTUASHg;spec/admin-holder/0.1/presentation-sent";

    @SerializedName("@id")
    public String id;

    @SerializedName("connection_id")
    public String connectionId;

    @SerializedName("presentation_exchange_id")
    public String presentationExchangeId;

    public String getType(){ return type; }
}
