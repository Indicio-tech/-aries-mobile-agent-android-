package tech.indicio.ariesmobileagentandroid.admin.proofs.messages;

import com.google.gson.annotations.SerializedName;

import java.util.UUID;

import tech.indicio.ariesmobileagentandroid.admin.messages.BaseOutboundAdminMessage;

public class PresentationGetMatchingCredentialsMessage extends BaseOutboundAdminMessage {
    @SerializedName("@type")
    public final static String type = "did:sov:BzCbsNYhMrjHiqZDTUASHg;spec/admin-holder/0.1/presentation-get-matching-credentials";

    @SerializedName("presentation_exchange_id")
    public String presentationExchangeId;

    public PresentationGetMatchingCredentialsMessage(String presentationExchangeId) {
        this.presentationExchangeId = presentationExchangeId;
        this.id = this.id = UUID.randomUUID().toString();
    }
}
