package tech.indicio.ariesmobileagentandroid.admin.credentials.offerObjects;

import com.google.gson.annotations.SerializedName;

public class CredentialProposalDict {
    @SerializedName("@type")
    public static final String type = "did:sov:BzCbsNYhMrjHiqZDTUASHg;spec/issue-credential/1.0/propose-credential";

    @SerializedName("@id")
    public String id;

    public String comment;

    @SerializedName("credential_proposal")
    public CredentialProposal credentialProposal;
}
