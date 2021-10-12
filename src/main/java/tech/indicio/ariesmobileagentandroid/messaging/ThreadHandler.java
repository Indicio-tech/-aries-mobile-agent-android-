package tech.indicio.ariesmobileagentandroid.messaging;

import android.util.Log;

import java.util.HashMap;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeoutException;

public class ThreadHandler {
    private static final String TAG = "AMAA - ThreadHandling";

    private HashMap<String, CompletableFuture<BaseMessage>> threadMap;

    public ThreadHandler(){
        this.threadMap = new HashMap<>();
    }

    protected void cacheMessageId(String messageId, CompletableFuture<BaseMessage> future){
        Log.d(TAG, "Caching thread ID: "+messageId);
        this.threadMap.put(messageId, future);

        //Thread to delete from cache after two minutes
        new Thread(()->{
            try {
                Thread.sleep(120000);
                if(this.threadMap.containsKey(messageId)){
                    this.threadMap.get(messageId).completeExceptionally(new TimeoutException("Timed out after 2 minutes"));
                    this.threadMap.remove(messageId);
                }
            } catch (Exception e) {
                Log.e(TAG, "Cache removal interrupted");
                e.printStackTrace();
            }
        }).start();

        return;
    }

    protected void completeThread(String threadId, BaseMessage message){
        if(this.threadMap.containsKey(threadId)){
            Log.d(TAG, "Completing thread: "+threadId);
            try{
                CompletableFuture future = this.threadMap.get(threadId);
                this.threadMap.remove(threadId);
                future.complete(message);
            }catch (Exception e){
                e.printStackTrace();
            }
        }else{
            Log.d(TAG, "Thread ID "+threadId+" not found in cache");
        }
    }
}
