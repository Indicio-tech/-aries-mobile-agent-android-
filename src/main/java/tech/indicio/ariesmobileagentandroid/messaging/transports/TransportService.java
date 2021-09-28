package tech.indicio.ariesmobileagentandroid.messaging.transports;

import android.util.Log;

import tech.indicio.ariesmobileagentandroid.connections.ConnectionRecord;
import tech.indicio.ariesmobileagentandroid.messaging.MessageReceiver;
import tech.indicio.ariesmobileagentandroid.messaging.MessageSender;

public class TransportService {

    private static final String TAG = "AMAA-TransportService";

    private final MessageReceiver messageReceiver;
    private final HTTPTransport httpTransport;
    private final WSClientTransport wsClientTransport;

    public TransportService(MessageReceiver messageReceiver, MessageSender messageSender) {
        this.messageReceiver = messageReceiver;
        this.wsClientTransport = new WSClientTransport(this.messageReceiver, messageSender);
        this.httpTransport = new HTTPTransport(this.messageReceiver);
    }

    public void send(byte[] message, String endpoint, ConnectionRecord connection) {
        try {
            //TODO: Alter inbound message passing strategy
            //TODO Replace with switch statement
            if(endpoint.startsWith("ws")){
                Log.d(TAG, "Sending Message to Endpoint '" + endpoint + "' over web sockets");
                this.wsClientTransport.send(message, endpoint, connection);
            }else{
                Log.d(TAG, "Sending Message to Endpoint '" + endpoint + "' over HTTP");
                this.httpTransport.send(message, endpoint);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
