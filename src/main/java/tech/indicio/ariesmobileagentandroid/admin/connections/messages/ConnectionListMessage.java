package tech.indicio.ariesmobileagentandroid.admin.connections.messages;

import com.google.gson.annotations.SerializedName;

import tech.indicio.ariesmobileagentandroid.admin.connections.AdminConnection;
import tech.indicio.ariesmobileagentandroid.messaging.BaseMessage;
import tech.indicio.ariesmobileagentandroid.messaging.decorators.ThreadDecorator;

public class ConnectionListMessage extends BaseMessage {
    @SerializedName("@type")
    public final static String type = "https://github.com/hyperledger/aries-toolbox/tree/master/docs/admin-connections/0.1/list";

    @SerializedName("@id")
    public String id;

    @SerializedName("~thread")
    public ThreadDecorator thread;
    public AdminConnection[] connections;

    public String getType() {
        return type;
    }
}
