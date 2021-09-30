package tech.indicio.ariesmobileagentandroid.admin.proofs.messages;

import com.google.gson.annotations.SerializedName;

import tech.indicio.ariesmobileagentandroid.admin.messages.BaseOutboundAdminMessage;
import tech.indicio.ariesmobileagentandroid.admin.proofs.proofObjects.AdminPresentationProposal;

public class SendPresentationProposalMessage extends BaseOutboundAdminMessage {
    @SerializedName("@type")
    public final static String type = "did:sov:BzCbsNYhMrjHiqZDTUASHg;spec/admin-holder/0.1/send-presentation-proposal";

    public boolean trace;

    @SerializedName("connection_id")
    public String connectionId;

    public String connection;

    @SerializedName("presentation_proposal")
    public AdminPresentationProposal presentationProposal;

    public boolean autoPresent;

}
