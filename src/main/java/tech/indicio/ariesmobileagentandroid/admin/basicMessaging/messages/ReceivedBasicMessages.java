package tech.indicio.ariesmobileagentandroid.admin.basicMessaging.messages;

import com.google.gson.annotations.SerializedName;

import tech.indicio.ariesmobileagentandroid.messaging.BaseMessage;
import tech.indicio.ariesmobileagentandroid.messaging.decorators.ThreadDecorator;

public class ReceivedBasicMessages extends BaseMessage {
    @SerializedName("@type")
    public final static String type = "https://github.com/hyperledger/aries-toolbox/tree/master/docs/admin-basicmessage/0.1/messages";

    @SerializedName("connection_id")
    public String connectionId;

    @SerializedName("~thread")
    public ThreadDecorator thread;

    public int count;
    public int offset;
    public int remaining;
    public AdminBasicMessage[] messages;
}
