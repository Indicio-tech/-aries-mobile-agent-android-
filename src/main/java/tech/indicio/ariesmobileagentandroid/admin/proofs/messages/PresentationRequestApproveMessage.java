package tech.indicio.ariesmobileagentandroid.admin.proofs.messages;

import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;

import tech.indicio.ariesmobileagentandroid.messaging.BaseMessage;

public class PresentationRequestApproveMessage  extends BaseMessage {
    @SerializedName("@type")
    public final static String type = "did:sov:BzCbsNYhMrjHiqZDTUASHg;spec/admin-holder/0.1/presentation-request-approve";

    @SerializedName("@id")
    public String id;

    @SerializedName("presentation_exchange_id")
    public String presentationExchangeId;

    @SerializedName("self_attested_attributes")
    public JsonObject selfAttestedAttributes;

    @SerializedName("requested_attributes")
    public JsonObject requestedAttributes;

    @SerializedName("requested_predicates")
    public JsonObject requestedPredicates;

    public String comment;
}
