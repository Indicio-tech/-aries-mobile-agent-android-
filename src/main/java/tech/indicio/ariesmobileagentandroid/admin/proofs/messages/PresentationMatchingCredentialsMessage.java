package tech.indicio.ariesmobileagentandroid.admin.proofs.messages;

import com.google.gson.annotations.SerializedName;

import tech.indicio.ariesmobileagentandroid.admin.proofs.MatchingCredentials;
import tech.indicio.ariesmobileagentandroid.messaging.BaseMessage;
import tech.indicio.ariesmobileagentandroid.messaging.decorators.PageDecorator;

public class PresentationMatchingCredentialsMessage extends BaseMessage {
    @SerializedName("@type")
    public final static String type = "did:sov:BzCbsNYhMrjHiqZDTUASHg;spec/admin-holder/0.1/presentation-matching-credentials";

    @SerializedName("@id")
    public String id;

    @SerializedName("presentation_exchange_id")
    public String presentationExchangeId;

    @SerializedName("matching_credentials")
    public MatchingCredentials matchingCredentials;

    @SerializedName("~page")
    public PageDecorator page;

}
