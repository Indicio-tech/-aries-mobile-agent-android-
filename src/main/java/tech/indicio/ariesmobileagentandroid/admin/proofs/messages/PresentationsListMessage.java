package tech.indicio.ariesmobileagentandroid.admin.proofs.messages;

import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;

import tech.indicio.ariesmobileagentandroid.admin.proofs.proofObjects.PresentationExchange;
import tech.indicio.ariesmobileagentandroid.messaging.BaseMessage;
import tech.indicio.ariesmobileagentandroid.messaging.decorators.PageDecorator;

public class PresentationsListMessage extends BaseMessage {
    @SerializedName("@type")
    public final static String type = "did:sov:BzCbsNYhMrjHiqZDTUASHg;spec/admin-holder/0.1/presentations-list";

    @SerializedName("@id")
    public String id;

    public PresentationExchange[] results;

    @SerializedName("~page")
    public PageDecorator page;
}
