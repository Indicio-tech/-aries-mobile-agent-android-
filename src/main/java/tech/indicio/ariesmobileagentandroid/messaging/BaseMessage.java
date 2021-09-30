package tech.indicio.ariesmobileagentandroid.messaging;

import com.google.gson.annotations.SerializedName;

public abstract class BaseMessage {
    public static String type;

    @SerializedName("@id")
    public String id;
}
