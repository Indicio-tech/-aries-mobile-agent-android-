package tech.indicio.ariesmobileagentandroid.connections.messages;

import com.google.gson.annotations.SerializedName;

import java.util.UUID;

import tech.indicio.ariesmobileagentandroid.connections.Connection;
import tech.indicio.ariesmobileagentandroid.messaging.BaseMessage;
import tech.indicio.ariesmobileagentandroid.messaging.decorators.TransportDecorator;

public class ConnectionRequest extends BaseMessage {
    @SerializedName("@type")
    public final static String type = "https://didcomm.org/connections/1.0/request";

    public String label;
    public Connection connection;

    //TODO: Replace with a decorator
    @SerializedName("~transport")
    public TransportDecorator transport;

    public ConnectionRequest(String label, Connection connection) {
        this.label = label;
        this.connection = connection;
        this.transport = new TransportDecorator("all");
        this.id = UUID.randomUUID().toString();
    }

    public ConnectionRequest(String label, Connection connection, String id) {
        this.label = label;
        this.connection = connection;
        this.transport = new TransportDecorator("all");
        this.id = id;
    }
}
