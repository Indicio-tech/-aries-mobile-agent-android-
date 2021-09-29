package tech.indicio.ariesmobileagentandroid.admin.proofs.proofObjects;

import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;

public class PresentationRequest {
    public String nonce;
    public String name;
    public String version;

    @SerializedName("requested_attributes")
    public JsonObject requestedAttributes;

    @SerializedName("requested_predicates")
    public JsonObject requestedPredicates;
}
