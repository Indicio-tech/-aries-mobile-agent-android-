package tech.indicio.ariesmobileagentandroid.admin.credentials.messages;

import com.google.gson.annotations.SerializedName;

import java.util.UUID;

import tech.indicio.ariesmobileagentandroid.admin.messages.BaseOutboundAdminMessage;
import tech.indicio.ariesmobileagentandroid.messaging.BaseMessage;
import tech.indicio.ariesmobileagentandroid.messaging.decorators.TransportDecorator;

public class GetCredentialsListMessage extends BaseOutboundAdminMessage {
    @SerializedName("@type")
    public final static String type = "did:sov:BzCbsNYhMrjHiqZDTUASHg;spec/admin-holder/0.1/credentials-get-list";

    @SerializedName("@id")
    public String id;

    public GetCredentialsListMessage(){
        this.id = UUID.randomUUID().toString();
    }
}
