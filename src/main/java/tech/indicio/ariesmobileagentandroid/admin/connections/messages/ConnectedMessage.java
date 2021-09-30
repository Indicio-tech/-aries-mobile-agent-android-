package tech.indicio.ariesmobileagentandroid.admin.connections.messages;

import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;

import tech.indicio.ariesmobileagentandroid.messaging.BaseMessage;

public class ConnectedMessage extends BaseMessage {
    @SerializedName("@type")
    public final static String type = "https://github.com/hyperledger/aries-toolbox/tree/master/docs/admin-connections/0.1/connected";

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
