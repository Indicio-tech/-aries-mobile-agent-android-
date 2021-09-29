package tech.indicio.ariesmobileagentandroid.admin;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

import tech.indicio.ariesmobileagentandroid.admin.messages.BaseAdminConfirmationMessage;
import tech.indicio.ariesmobileagentandroid.connections.ConnectionRecord;
import tech.indicio.ariesmobileagentandroid.messaging.decorators.ThreadDecorator;
import tech.indicio.ariesmobileagentandroid.storage.BaseRecord;

//Returned for adminBasicMessages that are not fetching an object (Deleted, Sent etc...)
public class AdminMessageConfirmationRecord extends BaseRecord {
    public static final String type = "admin_confirmation";
    public ConnectionRecord adminConnection;

    public Date createdAt;
    public BaseAdminConfirmationMessage message;
    public String messageType;

    @SerializedName("~thread")
    public ThreadDecorator thread;

    public AdminMessageConfirmationRecord(BaseAdminConfirmationMessage message, ConnectionRecord adminConnection) {
        this.message = message;
        this.thread = message.thread;
        this.messageType = message.getType();
        this.adminConnection = adminConnection;
    }

    public String getType() {
        return type;
    }
}
