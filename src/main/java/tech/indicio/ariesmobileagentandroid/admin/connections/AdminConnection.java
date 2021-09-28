package tech.indicio.ariesmobileagentandroid.admin.connections;

import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;

import tech.indicio.ariesmobileagentandroid.admin.connections.messages.ConnectedMessage;

public class AdminConnection {
    @SerializedName("their_did")
    public String theirDid;

    @SerializedName("connection_id")
    public String connectionId;

    public String label;

    public String state;

    @SerializedName("raw_repr")
    public JsonObject rawRepr;

    @SerializedName("my_did")
    public String myDid;

    public AdminConnection(ConnectedMessage message){
        this.theirDid = message.theirDid;
        this.connectionId = message.connectionId;
        this.label = message.label;
        this.state = message.state;
        this.rawRepr = message.rawRepr;
        this.myDid = message.myDid;
    }
}
