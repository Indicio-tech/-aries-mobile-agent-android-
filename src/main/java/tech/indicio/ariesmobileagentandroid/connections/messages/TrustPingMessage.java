package tech.indicio.ariesmobileagentandroid.connections.messages;

import com.google.gson.annotations.SerializedName;

import java.util.UUID;

import tech.indicio.ariesmobileagentandroid.messaging.BaseMessage;
import tech.indicio.ariesmobileagentandroid.messaging.decorators.TimingDecorator;

public class TrustPingMessage extends BaseMessage {
    //These 2 are required
    @SerializedName("@type")
    public final static String type = "https://didcomm.org/trust_ping/1.0/ping";

    @SerializedName("@id")
    public String id;

    //These 3 are optional
    @SerializedName("response_requested")
    public boolean responseRequested;

    public String comment;

    @SerializedName("~timing")
    public TimingDecorator timing;

    public TrustPingMessage(boolean responseRequested, String comment){
        this.id = UUID.randomUUID().toString();
        this.responseRequested = responseRequested;
        this.comment = comment;
    }

    public TrustPingMessage(){
        this.id = UUID.randomUUID().toString();
    }

}
