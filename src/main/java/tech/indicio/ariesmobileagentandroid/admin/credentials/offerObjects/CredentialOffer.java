package tech.indicio.ariesmobileagentandroid.admin.credentials.offerObjects;

import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;

public class CredentialOffer {
    @SerializedName("schema_id")
    public String schemaId;

    @SerializedName("cred_def_id")
    public String credDefId;

    public String nonce;

    @SerializedName("key_correctness_proof")
    public JsonObject keyCorrectnessProof;


}
