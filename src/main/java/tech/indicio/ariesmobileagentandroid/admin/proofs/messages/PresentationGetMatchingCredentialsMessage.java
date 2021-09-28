package tech.indicio.ariesmobileagentandroid.admin.proofs.messages;

import com.google.gson.annotations.SerializedName;

import tech.indicio.ariesmobileagentandroid.admin.messages.BaseOutboundAdminMessage;
import tech.indicio.ariesmobileagentandroid.messaging.BaseMessage;

public class PresentationGetMatchingCredentialsMessage extends BaseOutboundAdminMessage {
    @SerializedName("@type")
    public final static String type = "did:sov:BzCbsNYhMrjHiqZDTUASHg;spec/admin-holder/0.1/presentation-get-matching-credentials";

    @SerializedName("@id")
    public String id;

    @SerializedName("presentation_exchange_id")
    public String presentationExchangeId;
}
