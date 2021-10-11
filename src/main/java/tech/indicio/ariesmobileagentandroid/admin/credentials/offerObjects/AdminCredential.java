package tech.indicio.ariesmobileagentandroid.admin.credentials.offerObjects;

import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;

public class AdminCredential {
    public String referent;

    @SerializedName("attrs")
    public JsonObject attributes;

    @SerializedName("schema_id")
    public String schemaId;

    @SerializedName("cred_def_id")
    public String credDefId;
}
