package tech.indicio.ariesmobileagentandroid.admin.basicMessaging.messages;

import com.google.gson.annotations.SerializedName;

import tech.indicio.ariesmobileagentandroid.admin.messages.BaseAdminConfirmationMessage;
import tech.indicio.ariesmobileagentandroid.messaging.BaseMessage;
import tech.indicio.ariesmobileagentandroid.messaging.BasicMessage;
import tech.indicio.ariesmobileagentandroid.messaging.decorators.ThreadDecorator;

public class DeletedBasicMessage extends BaseAdminConfirmationMessage {
    @SerializedName("@type")
    public final static String type = "https://github.com/hyperledger/aries-toolbox/tree/master/docs/admin-basicmessage/0.1/deleted";

    @SerializedName("@id")
    public String id;

    @SerializedName("connection_id")
    public String connectionId;

    public AdminBasicMessage[] deleted;

    public String getType(){
        return type;
    }
}
