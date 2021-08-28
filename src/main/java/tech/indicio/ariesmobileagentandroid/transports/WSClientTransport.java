package tech.indicio.ariesmobileagentandroid.transports;

import android.util.Log;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;
import tech.indicio.ariesmobileagentandroid.messaging.MessageReceiver;

public class WSClientTransport {
    private static final String TAG = "AMAA-WSClientTransport";
    private MessageReceiver messageReceiver;

    private HashMap<String, WebSocket> socketMap = new HashMap();

    public WSClientTransport(MessageReceiver messageReceiver) {
        this.messageReceiver = messageReceiver;
    }

    public void send(byte[] message, String endpoint){
        WebSocket socket = getSocket(endpoint);
        socket.send(new ByteString(message));
    }

    private WebSocket getSocket(String endpoint){
        if(socketMap.containsKey(endpoint)){
            return socketMap.get(endpoint);
        }else{
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder().url(endpoint).build();
            WebSocketListener listener = new AriesSocketListener();
            WebSocket ws = client.newWebSocket(request,listener);
            socketMap.put(endpoint, ws);
            return ws;
        }
    }

    private class AriesSocketListener extends WebSocketListener {
        public void onClosed(@NotNull WebSocket webSocket, int code, @NotNull String reason) {
            Log.d(TAG,"Socket has been closed: "+code+": "+reason);
        }

        public void onClosing(@NotNull WebSocket webSocket, int code, @NotNull String reason) {
            super.onClosing(webSocket, code, reason);
        }


        public void onFailure(@NotNull WebSocket webSocket, @NotNull Throwable t, @Nullable Response response) {
            super.onFailure(webSocket, t, response);
        }

        public void onMessage(@NotNull WebSocket webSocket, @NotNull String message) {
            Log.d(TAG, "WS Message received");
            messageReceiver.receiveMessage(message.getBytes());
        }

        public void onMessage(@NotNull WebSocket webSocket, @NotNull ByteString message) {
            Log.d(TAG, "WS Message received");
            messageReceiver.receiveMessage(message.toByteArray());
        }

        public void onOpen(@NotNull WebSocket webSocket, @NotNull Response response) {
            Log.d(TAG, "Socket opened");
        }
    }

}
