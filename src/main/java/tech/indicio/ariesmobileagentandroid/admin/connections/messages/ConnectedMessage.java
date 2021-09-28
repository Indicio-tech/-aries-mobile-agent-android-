package tech.indicio.ariesmobileagentandroid.admin.connections.messages;

import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;

import tech.indicio.ariesmobileagentandroid.admin.connections.AdminConnection;
import tech.indicio.ariesmobileagentandroid.messaging.BaseMessage;
import tech.indicio.ariesmobileagentandroid.messaging.decorators.ThreadDecorator;

public class ConnectedMessage extends BaseMessage {
    @SerializedName("@type")
    public final static String type = "https://github.com/hyperledger/aries-toolbox/tree/master/docs/admin-connections/0.1/connected";

    @SerializedName("@id")
    public String id;

    @SerializedName("connection_id")
    public String connectionId;

    public String label;

    @SerializedName("my_did")
    public String myDid;

    public String state;

    @SerializedName("their_did")
    public String theirDid;

    @SerializedName("raw_repr")
    public JsonObject rawRepr;
}
