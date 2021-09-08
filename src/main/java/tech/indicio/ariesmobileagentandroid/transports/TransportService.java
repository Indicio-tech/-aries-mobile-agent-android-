package tech.indicio.ariesmobileagentandroid.transports;

import android.util.Log;

import tech.indicio.ariesmobileagentandroid.messaging.MessageReceiver;

public class TransportService {

    private static final String TAG = "AMAA-TransportService";

    private final MessageReceiver messageReceiver;
    private final HTTPTransport httpTransport;

    public TransportService(MessageReceiver messageReceiver) {
        this.messageReceiver = messageReceiver;
        this.httpTransport = new HTTPTransport(this.messageReceiver);
    }

    public void send(byte[] message, String endpoint) {
        try {
            Log.d(TAG, "Sending Message to Endpoint '" + endpoint + "'");

            //TODO: filter endpoint url by protocol and determine which transport to use
            //TODO: Alter inbound message passing strategy
            this.httpTransport.send(message, endpoint);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
