package tech.indicio.ariesmobileagentandroid.admin.basicMessaging.eventRecords;

import android.util.Log;

import com.google.gson.JsonObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import tech.indicio.ariesmobileagentandroid.admin.basicMessaging.messages.AdminBasicMessage;
import tech.indicio.ariesmobileagentandroid.admin.basicMessaging.messages.ReceivedBasicMessages;
import tech.indicio.ariesmobileagentandroid.connections.ConnectionRecord;
import tech.indicio.ariesmobileagentandroid.storage.BaseRecord;

public class AdminBasicMessagesRecord extends BaseRecord {
    public static final String type = "admin_basic_messages";
    public ConnectionRecord adminConnection;
    public ReceivedBasicMessages messageObject;
    public AdminBasicMessage[] basicMessagesArray;
    public String threadId;
    public HashMap<String, List<AdminBasicMessage>> basicMessages;

    public AdminBasicMessagesRecord(ReceivedBasicMessages message, ConnectionRecord adminConnection) {
        this.adminConnection = adminConnection;
        this.messageObject = message;
        this.basicMessagesArray = sortBasicMessages(message.messages);
        this.threadId = message.thread.thid;
        this.id = message.id;
        this.tags = new JsonObject();
        tags.addProperty("adminConnection", adminConnection.id);

        this.basicMessages = new HashMap<>();
        for (AdminBasicMessage bm : basicMessagesArray) {
            List<AdminBasicMessage> list;
            if (this.basicMessages.containsKey(bm.connectionId)) {
                list = this.basicMessages.get(bm.connectionId);
            } else {
                list = new ArrayList<AdminBasicMessage>();
                this.basicMessages.put(bm.connectionId, list);
            }
            list.add(bm);
        }
    }

    public String getType() {
        return type;
    }

    private AdminBasicMessage[] sortBasicMessages(AdminBasicMessage[] messages) {
        AdminBasicMessage[] newArr = messages;
        try {
            ArrayList<AdminBasicMessage> messageList = new ArrayList(Arrays.asList(messages));

            Collections.sort(messageList, (Comparator<AdminBasicMessage>) (m1, m2) -> {
                try {
                    DateFormat format = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss.SS", Locale.ENGLISH);
                    Date date1 = format.parse(m1.sentTime);
                    Date date2 = format.parse(m2.sentTime);
                    Log.d("AAA", String.valueOf(date1.compareTo(date2)));
                    return date1.compareTo(date2);
                } catch (Exception e) {
                    e.printStackTrace();
                    return 0;
                }
            });

            newArr = (AdminBasicMessage[]) messageList.toArray(newArr);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return newArr;
    }

}
