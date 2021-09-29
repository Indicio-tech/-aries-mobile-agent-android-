package tech.indicio.ariesmobileagentandroid.admin.proofs.messages;

import com.google.gson.annotations.SerializedName;

import tech.indicio.ariesmobileagentandroid.admin.proofs.proofObjects.MatchingCredentials;
import tech.indicio.ariesmobileagentandroid.messaging.BaseMessage;
import tech.indicio.ariesmobileagentandroid.messaging.decorators.PageDecorator;
import tech.indicio.ariesmobileagentandroid.messaging.decorators.ThreadDecorator;

public class PresentationMatchingCredentialsMessage extends BaseMessage {
    @SerializedName("@type")
    public final static String type = "did:sov:BzCbsNYhMrjHiqZDTUASHg;spec/admin-holder/0.1/presentation-matching-credentials";

    @SerializedName("~thread")
    public ThreadDecorator thread;

    @SerializedName("@id")
    public String id;

    @SerializedName("presentation_exchange_id")
    public String presentationExchangeId;

    @SerializedName("matching_credentials")
    public MatchingCredentials[] matchingCredentials;

    @SerializedName("~page")
    public PageDecorator page;

}
