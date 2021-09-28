package tech.indicio.ariesmobileagentandroid.admin.credentials.messages;

import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;

import tech.indicio.ariesmobileagentandroid.admin.credentials.offerObjects.AdminCredential;
import tech.indicio.ariesmobileagentandroid.admin.credentials.offerObjects.CredentialOffer;
import tech.indicio.ariesmobileagentandroid.admin.credentials.offerObjects.CredentialProposalDict;
import tech.indicio.ariesmobileagentandroid.messaging.BaseMessage;

public class CredentialReceivedMessage extends BaseMessage {
    @SerializedName("@type")
    public final static String type = "did:sov:BzCbsNYhMrjHiqZDTUASHg;spec/admin-holder/0.1/credential-received";

    @SerializedName("@id")
    public String id;

    public String state;

    @SerializedName("created_at")
    public String createdAt;

    @SerializedName("updated_at")
    public String updatedAt;

    public boolean trace;

    @SerializedName("credential_exchange_id")
    public String credentialExchangeId;

    @SerializedName("connection_id")
    public String connectionId;

    @SerializedName("thread_id")
    public String threadId;

    @SerializedName("parent_thread_id")
    public String parentThreadId;

    public String initiator;

    public String role;

    @SerializedName("credential_definition_id")
    public String credentialDefinitionId;

    @SerializedName("schema_id")
    public String schemaId;

    @SerializedName("credential_proposal_dict")
    public CredentialProposalDict credentialProposalDict;

    @SerializedName("credential_offer_dict")
    public JsonObject credentialOfferDict;

    @SerializedName("credential_offer")
    public CredentialOffer credentialOffer;

    @SerializedName("credential_request")
    public JsonObject credentialRequest;

    @SerializedName("credential_request_metadata")
    public JsonObject credentialRequestMetadata;

    @SerializedName("credential_id")
    public String credentialId;

    @SerializedName("raw_credential")
    public JsonObject rawCredential;

    public AdminCredential credential;

    @SerializedName("auto_offer")
    public boolean autoOffer;

    @SerializedName("auto_issue")
    public boolean autoIssue;

    @SerializedName("auto_remove")
    public boolean autoRemove;

    @SerializedName("error_msg")
    public String errorMsg;

    @SerializedName("revoc_reg_id")
    public String revocRegId;

    @SerializedName("revocation_id")
    public String revocationId;

}
