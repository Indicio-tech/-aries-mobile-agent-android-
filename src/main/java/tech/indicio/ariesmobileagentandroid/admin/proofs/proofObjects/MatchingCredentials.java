package tech.indicio.ariesmobileagentandroid.admin.proofs.proofObjects;

import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;

public class MatchingCredentials {
    @SerializedName("cred_info")
    public AdminCredentialInfo credInfo;

    @SerializedName("schema_id")
    public String schemaId;

    @SerializedName("cred_def_id")
    public String credDefId;

    @SerializedName("rev_reg_id")
    public String revRegId;

    @SerializedName("cred_rev")
    public String credRev;

    //Gson blows up if interval is null
//    public JsonObject interval;

    @SerializedName("presentation_referents")
    public JsonObject presentationReferents;
}
