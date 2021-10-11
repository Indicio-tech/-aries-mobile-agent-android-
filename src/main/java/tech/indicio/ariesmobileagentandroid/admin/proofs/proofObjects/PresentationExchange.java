package tech.indicio.ariesmobileagentandroid.admin.proofs.proofObjects;

import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;

public class PresentationExchange {
    public String initiator;

    @SerializedName("updated_at")
    public String updatedAt;

    public String role;
    @SerializedName("created_at")
    public String createdAt;
    @SerializedName("presentation_exchange_id")
    public String presentationExchangeId;
    @SerializedName("connection_id")
    public String connectionId;
    @SerializedName("thread_id")
    public String threadId;
    @SerializedName("presentation_request_dict")
    public JsonObject presentationRequestDict;
    @SerializedName("presentation_request")
    public PresentationRequest presentationRequest;
    public String state;
    boolean trace;


}
