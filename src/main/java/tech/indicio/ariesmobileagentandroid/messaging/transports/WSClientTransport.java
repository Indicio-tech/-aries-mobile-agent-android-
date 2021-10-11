package tech.indicio.ariesmobileagentandroid.messaging.transports;

import android.util.Log;
import android.util.Pair;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;
import tech.indicio.ariesmobileagentandroid.connections.ConnectionRecord;
import tech.indicio.ariesmobileagentandroid.connections.messages.TrustPingMessage;
import tech.indicio.ariesmobileagentandroid.messaging.MessageReceiver;
import tech.indicio.ariesmobileagentandroid.messaging.MessageSender;

public class WSClientTransport {
    private static final String TAG = "AMAA-WSClientTransport";
    private final HashMap<String, Pair<WebSocket, AriesSocketListener>> socketMap = new HashMap();
    private final MessageReceiver messageReceiver;
    private final MessageSender messageSender;

    public WSClientTransport(MessageReceiver messageReceiver, MessageSender messageSender) {
        this.messageReceiver = messageReceiver;
        this.messageSender = messageSender;
    }

    public void send(byte[] message, String endpoint, ConnectionRecord connection) {
        WebSocket socket = getSocket(endpoint, connection);
        Log.d(TAG, "Sending message to " + endpoint);
        socket.send(new ByteString(message));
        while (socket.queueSize() > 0) {
        }
        Log.d(TAG, "Message sent");
    }

    private WebSocket getSocket(String endpoint, ConnectionRecord connection) {
        if (socketMap.containsKey(endpoint)) {
            Pair<WebSocket, AriesSocketListener> socketPair = socketMap.get(endpoint);
            WebSocket socket = socketPair.first;
            AriesSocketListener listener = socketPair.second;
            if (!listener.connections.containsKey(connection.id)) {
                listener.connections.put(connection.id, connection);
            }
            return socket;
        } else {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder().url(endpoint).build();
            HashMap<String, ConnectionRecord> connections = new HashMap<>();
            connections.put(connection.id, connection);
            AriesSocketListener listener = new AriesSocketListener(endpoint, connections);
            WebSocket ws = client.newWebSocket(request, listener);
            socketMap.put(endpoint, new Pair<>(ws, listener));
            return ws;
        }
    }

    private class AriesSocketListener extends WebSocketListener {
        private final HashMap<String, ConnectionRecord> connections;
        private final boolean failed; //If the listener has previously failed
        public String endpoint;
        private boolean hasConnected = false;

        private AriesSocketListener(String endpoint, HashMap<String, ConnectionRecord> connections, boolean failed) {
            this.endpoint = endpoint;
            this.connections = connections;
            this.failed = failed;
        }

        public AriesSocketListener(String endpoint, HashMap<String, ConnectionRecord> connections) {
            this(endpoint, connections, false);
        }

        public void onClosed(@NotNull WebSocket webSocket, int code, @NotNull String reason) {
            Log.d(TAG, "Socket for endpoint " + endpoint + " has been closed: " + code + ": " + reason);
        }

        public void onClosing(@NotNull WebSocket webSocket, int code, @NotNull String reason) {
            super.onClosing(webSocket, code, reason);
        }


        public void onFailure(@NotNull WebSocket webSocket, @NotNull Throwable t, @Nullable Response response) {
            super.onFailure(webSocket, t, response);
            if (hasConnected) {
                Log.d(TAG, "Websocket failure for endpoint: " + endpoint + "\n" + t.getMessage());

                //OnFail recreate socket
                socketMap.remove(endpoint);
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder().url(endpoint).build();
                AriesSocketListener listener = new AriesSocketListener(endpoint, connections, true);
                WebSocket ws = client.newWebSocket(request, listener);
                socketMap.put(endpoint, new Pair<>(ws, listener));
            }
        }

        public void onMessage(@NotNull WebSocket webSocket, @NotNull String message) {
            Log.d(TAG, "WS String message received for endpoint: " + endpoint);
            messageReceiver.receiveMessage(message.getBytes());
        }

        public void onMessage(@NotNull WebSocket webSocket, @NotNull ByteString message) {
            Log.d(TAG, "WS ByteString message received for endpoint: " + endpoint);
            messageReceiver.receiveMessage(message.toByteArray());
        }

        public void onOpen(@NotNull WebSocket webSocket, @NotNull Response response) {
            this.hasConnected = true;
            Log.d(TAG, "Socket opened for endpoint: " + endpoint);
            if (this.failed) {
                //TODO - Move trust ping creation outside of transport
                Log.d(TAG, "Sending trust pings");
                TrustPingMessage tp = new TrustPingMessage("all");
                for (ConnectionRecord connection : this.connections.values()) {
                    try {
                        messageSender.sendMessage(tp, connection);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                Log.d(TAG, "Trust pings sent");
            }
        }
    }

}
