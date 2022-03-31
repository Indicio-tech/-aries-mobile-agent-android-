package tech.indicio.ariesmobileagentandroid.admin.trustPing.messages;

import com.google.gson.annotations.SerializedName;

import java.util.UUID;

import tech.indicio.ariesmobileagentandroid.messaging.BaseMessage;

public class AdminResponseReceivedTrustPing extends BaseMessage{

    @SerializedName("@type")
    public final static String type = "https://github.com/hyperledger/aries-toolbox/tree/master/docs/admin-trustping/0.1/response-received";

    public AdminResponseReceivedTrustPing(String id){
        super();
        this.id = id;
    }
}