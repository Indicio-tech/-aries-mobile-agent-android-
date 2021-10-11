package tech.indicio.ariesmobileagentandroid.admin.proofs.proofObjects;

import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;

public class AdminPresentationProposal {
    @SerializedName("@type")
    public final static String type = "did:sov:BzCbsNYhMrjHiqZDTUASHg;spec/present-proof/1.0/presentation-preview";

    public JsonObject attributes;
    public JsonObject predicates;
}
