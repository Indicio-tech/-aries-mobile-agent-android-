package tech.indicio.ariesmobileagentandroid.admin.connections.messages;

import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;

import tech.indicio.ariesmobileagentandroid.messaging.BaseMessage;
import tech.indicio.ariesmobileagentandroid.messaging.decorators.ThreadDecorator;

public class ConnectionMessage extends BaseMessage {
    @SerializedName("@type")
    public final static String type = "https://github.com/hyperledger/aries-toolbox/tree/master/docs/admin-connections/0.1/connection";

    @SerializedName("@id")
    public String id;

    @SerializedName("~thread")
    public ThreadDecorator thread;

    public String label;
    public String state;

    @SerializedName("raw_repr")
    public JsonObject rawRepr;

    @SerializedName("connection_id")
    public String connectionId;

    @SerializedName("my_did")
    public String myDid;
}
