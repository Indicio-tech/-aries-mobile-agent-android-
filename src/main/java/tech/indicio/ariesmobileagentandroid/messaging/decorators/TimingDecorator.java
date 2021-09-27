package tech.indicio.ariesmobileagentandroid.messaging.decorators;

import com.google.gson.annotations.SerializedName;

public class TimingDecorator {
    @SerializedName("out_time")
    public String outTime;

    @SerializedName("expires_time")
    public String expiresTime;

    @SerializedName("delay_milli")
    public int delayMilli;
}
