package tech.indicio.ariesmobileagentandroid.admin.messages;

import com.google.gson.annotations.SerializedName;

import tech.indicio.ariesmobileagentandroid.messaging.BaseMessage;
import tech.indicio.ariesmobileagentandroid.messaging.decorators.ThreadDecorator;

public abstract class BaseAdminConfirmationMessage extends BaseMessage {
    @SerializedName("~thread")
    public ThreadDecorator thread;

    public String getType() {
        return type;
    }
}
