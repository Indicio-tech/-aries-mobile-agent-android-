package tech.indicio.ariesmobileagentandroid.admin.credentials.messages;

import com.google.gson.annotations.SerializedName;

import tech.indicio.ariesmobileagentandroid.admin.credentials.offerObjects.CredentialProposalDict;
import tech.indicio.ariesmobileagentandroid.messaging.BaseMessage;

public class SendCredentialProposalMessage extends BaseMessage {
    @SerializedName("@type")
    public final static String type = "did:sov:BzCbsNYhMrjHiqZDTUASHg;spec/admin-holder/0.1/send-credential-proposal";

    @SerializedName("@id")
    public String id;

    public boolean trace;

    @SerializedName("connection_id")
    public String connectionId;

    @SerializedName("cred_def_id")
    public String credDefId;

    @SerializedName("schema_id")
    public String schemaId;

    @SerializedName("schema_issuer_did")
    public String schemaIssuerDid;

    @SerializedName("schema_name")
    public String schemaName;

    @SerializedName("schema_version")
    public String schemaVersion;

    @SerializedName("issuer_did")
    public String issuerDid;

    @SerializedName("auto_remove")
    public boolean autoRemove;

    public String comment;

    @SerializedName("credential_proposal")
    public CredentialProposalDict credentialProposal;
}
