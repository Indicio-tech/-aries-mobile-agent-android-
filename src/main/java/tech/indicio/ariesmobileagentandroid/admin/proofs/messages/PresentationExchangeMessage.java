package tech.indicio.ariesmobileagentandroid.admin.proofs.messages;

import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;

import tech.indicio.ariesmobileagentandroid.messaging.BaseMessage;

public class PresentationExchangeMessage extends BaseMessage {
    @SerializedName("@type")
    public final static String type = "did:sov:BzCbsNYhMrjHiqZDTUASHg;spec/admin-holder/0.1/presentation-exchange";

    @SerializedName("@id")
    public String id;

    public String state;

    @SerializedName("created_at")
    public String createdAt;

    @SerializedName("updated_at")
    public String updatedAt;

    public boolean trace;

    @SerializedName("presentation_exchange_id")
    public String presentationExchangeId;

    @SerializedName("connection_id")
    public String connectionId;

    @SerializedName("thread_id")
    public String threadId;

    public String initiator;

    public String role;

    @SerializedName("presentation_proposal_dict")
    public JsonObject presentationProposalDict;

    @SerializedName("presentation_request")
    public JsonObject presentationRequest;

    @SerializedName("presentation_request_dict")
    public JsonObject presentationRequestDict;

    public JsonObject presentation;

    public String verified;

    @SerializedName("auto_present")
    public boolean autoPresent;

    @SerializedName("error_msg")
    public String errorMsg;
}
