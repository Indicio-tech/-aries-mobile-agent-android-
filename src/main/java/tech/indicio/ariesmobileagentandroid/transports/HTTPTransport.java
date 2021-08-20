package tech.indicio.ariesmobileagentandroid.transports;

import android.util.Log;

public class HTTPTransport {

    private static final String TAG = "AMAA-HTTPTransport";

    public static void send(byte[] message, String endpoint){
        Log.d(TAG, "Sending Message via HTTP to endpoint '" + endpoint + "'");

        String messageString = new String(message);

    }
}
