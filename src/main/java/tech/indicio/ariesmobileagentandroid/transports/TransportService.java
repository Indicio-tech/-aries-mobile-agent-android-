package tech.indicio.ariesmobileagentandroid.transports;

import android.util.Log;

public class TransportService {

    private static final String TAG = "AMAA-TransportService";

    public static void send(byte[] message, String endpoint){
        Log.d(TAG, "Sending Message to Endpoint '" + endpoint + "'");

        //TODO: filter endpoint url by protocol and determine which transport to use
        HTTPTransport.send(message, endpoint);
    }
}
