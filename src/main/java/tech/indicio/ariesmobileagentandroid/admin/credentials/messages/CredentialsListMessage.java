package tech.indicio.ariesmobileagentandroid.admin.credentials.messages;

import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;

import tech.indicio.ariesmobileagentandroid.admin.credentials.offerObjects.CredentialExchangeItem;
import tech.indicio.ariesmobileagentandroid.messaging.BaseMessage;
import tech.indicio.ariesmobileagentandroid.messaging.decorators.ThreadDecorator;

public class CredentialsListMessage extends BaseMessage {
    @SerializedName("@type")
    public final static String type = "did:sov:BzCbsNYhMrjHiqZDTUASHg;spec/admin-holder/0.1/credentials-list";

    @SerializedName("@id")
    public String id;

    @SerializedName("~thread")
    public ThreadDecorator thread;

    public CredentialExchangeItem[] results;
}
