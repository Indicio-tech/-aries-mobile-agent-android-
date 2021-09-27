package tech.indicio.ariesmobileagentandroid.connections.messages;

import com.google.gson.annotations.SerializedName;

import java.util.UUID;

import tech.indicio.ariesmobileagentandroid.connections.Connection;
import tech.indicio.ariesmobileagentandroid.messaging.BaseMessage;
import tech.indicio.ariesmobileagentandroid.messaging.decorators.SignatureDecorator;
import tech.indicio.ariesmobileagentandroid.messaging.decorators.ThreadDecorator;
import tech.indicio.ariesmobileagentandroid.messaging.decorators.TransportDecorator;

public class ConnectionResponse extends BaseMessage {
    @SerializedName("@type")
    public final static String type = "did:sov:BzCbsNYhMrjHiqZDTUASHg;spec/connections/1.0/response";

    @SerializedName("@id")
    public String id;

    @SerializedName("~thread")
    public ThreadDecorator thread;

    public String label;


    public Connection connection;

    @SerializedName("connection~sig")
    public SignatureDecorator signedConnection;

    public ConnectionResponse(String label, Connection connection) {
        this.label = label;
        this.connection = connection;
    }
}
