package tech.indicio.ariesmobileagentandroid.connections.messages;

import com.google.gson.annotations.SerializedName;

import tech.indicio.ariesmobileagentandroid.connections.Connection;
import tech.indicio.ariesmobileagentandroid.messaging.BaseMessage;

public class ConnectionRequest extends BaseMessage {
    @SerializedName("@type")
    public final static String type = "https://didcomm.org/connections/1.0/request";

    @SerializedName("@id")
    public String id;

    public String label;
    public Connection connection;
}
