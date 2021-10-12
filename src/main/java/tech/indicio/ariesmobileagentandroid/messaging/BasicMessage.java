package tech.indicio.ariesmobileagentandroid.messaging;

import com.google.gson.annotations.SerializedName;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import tech.indicio.ariesmobileagentandroid.messaging.decorators.LocalizationDecorator;

public class BasicMessage extends BaseMessage {
    @SerializedName("@type")
    public final static String type = "did:sov:BzCbsNYhMrjHiqZDTUASHg;spec/basicmessage/1.0/message";

    @SerializedName("~l10n")
    public LocalizationDecorator l10n;

    //ISO 8601 Date format
    @SerializedName("sent_time")
    public String sentTime;

    public String content;

    public BasicMessage(String content) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss'Z'");
        this.id = UUID.randomUUID().toString();
        this.l10n = new LocalizationDecorator("en");
        this.sentTime = df.format(new Date());
        this.content = content;
    }
}
