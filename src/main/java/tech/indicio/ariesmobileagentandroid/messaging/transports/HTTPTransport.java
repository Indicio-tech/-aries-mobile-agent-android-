package tech.indicio.ariesmobileagentandroid.messaging.transports;

import android.util.Log;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import tech.indicio.ariesmobileagentandroid.messaging.MessageReceiver;

public class HTTPTransport {

    public static final MediaType JSON = MediaType.get("application/ssi-agent-wire");
    private static final String TAG = "AMAA-HTTPTransport";
    private MessageReceiver messageReceiver;

    public HTTPTransport(MessageReceiver messageReceiver) {
        this.messageReceiver = messageReceiver;
    }

    public void send(byte[] message, String endpoint) throws IOException {
        Log.d(TAG, "Sending Message via HTTP to endpoint '" + endpoint + "'");

        String messageString = new String(message);
        Log.d(TAG, messageString);
        post(messageString, endpoint);
    }

    private void post(String json, String url) throws IOException {
        OkHttpClient client = new OkHttpClient();

        RequestBody body = RequestBody.create(json, JSON);

        Log.d(TAG, "Sending POST request: "+body);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .addHeader("Content-Type", "application/ssi-agent-wire")
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                Log.d(TAG, "Received HTTP Response Code: "+response.code());

                if(response.body().contentLength() > 0){
                    messageReceiver.receiveMessage(response.body().bytes());
                }else{
                    Log.d(TAG, "Response body is empty.");
                }
            }
        });
    }
}
