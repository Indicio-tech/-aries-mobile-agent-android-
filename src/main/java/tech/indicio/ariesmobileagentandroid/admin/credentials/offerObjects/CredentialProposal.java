package tech.indicio.ariesmobileagentandroid.admin.credentials.offerObjects;

import com.google.gson.annotations.SerializedName;

public class CredentialProposal {
    @SerializedName("@type")
    public static final String type = "did:sov:BzCbsNYhMrjHiqZDTUASHg;spec/issue-credential/1.0/credential-preview";

    public CredentialAttribute[] attributes;
}
