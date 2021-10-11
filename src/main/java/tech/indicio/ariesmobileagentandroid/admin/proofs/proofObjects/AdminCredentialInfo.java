package tech.indicio.ariesmobileagentandroid.admin.proofs.proofObjects;

import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;

public class AdminCredentialInfo {
    public String referent;
    public JsonObject attrs;

    @SerializedName("schema_id")
    public String schemaId;

    @SerializedName("cred_def_id")
    public String credDefId;
}
