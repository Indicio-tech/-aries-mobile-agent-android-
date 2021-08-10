package tech.indicio.ariesmobileagentandroid;


import android.util.Log;
import org.hyperledger.indy.sdk.wallet.Wallet;

public class Agent {
    private static final String TAG = "AMAA-Agent";

    public Agent(){
        try{
            Log.d(TAG, "Loading indy");
            System.loadLibrary("indy");
            Log.d(TAG, "Indy loaded, creating wallet...");

            Wallet.createWallet("{\"id\":\"ioahsfajklshfkj1212\"}", "{\"key\": \"123\"}").get();
            Log.d(TAG, "Wallet created");
            Wallet wallet = Wallet.openWallet("{\"id\":\"ioahsfajklshfkj\"}", "{\"key\": \"123\"}").get();
            Log.d(TAG, "Wallet opened");
        }catch(Exception e){
            IndySdkRejectResponse rejectResponse = new IndySdkRejectResponse(e);
            String code = rejectResponse.getCode();
            String json = rejectResponse.toJson();
            Log.e(TAG, "INDY ERROR");
            Log.e(TAG, code);
            Log.e(TAG, json);
            Log.e(TAG, e.getMessage());
        }

    }
}
