package tech.indicio.ariesmobileagentandroid.admin.messages;

import com.google.gson.annotations.SerializedName;

import tech.indicio.ariesmobileagentandroid.messaging.BaseMessage;
import tech.indicio.ariesmobileagentandroid.messaging.decorators.TransportDecorator;

public class BaseOutboundAdminMessage extends BaseMessage {
    @SerializedName("~transport")
    public TransportDecorator transport = new TransportDecorator("all");
}
