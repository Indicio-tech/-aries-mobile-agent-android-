package tech.indicio.ariesmobileagentandroid.messaging.decorators;

import com.google.gson.annotations.SerializedName;

public class TransportDecorator {
    @SerializedName("return_route")
    public String returnRoute;

    public TransportDecorator(String returnRoute) {
        this.returnRoute = returnRoute;
    }
}
