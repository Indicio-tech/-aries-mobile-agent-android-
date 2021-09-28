package tech.indicio.ariesmobileagentandroid.admin.basicMessaging.messages;

import com.google.gson.annotations.SerializedName;

import java.util.UUID;

import tech.indicio.ariesmobileagentandroid.messaging.BasicMessage;

public class AdminBasicMessage extends BasicMessage {
    @SerializedName("connection_id")
    public String connectionId;

    @SerializedName("message_id")
    public String messageId;

    public String state;

    public AdminBasicMessage(String content, String connectionId) {
        super(content);
        this.connectionId = connectionId;
        this.messageId = this.id = UUID.randomUUID().toString();
        this.state = "sent";
    }
}
