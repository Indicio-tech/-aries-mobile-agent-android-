package tech.indicio.ariesmobileagentandroid;


import android.system.ErrnoException;
import android.util.Log;
import org.hyperledger.indy.sdk.wallet.Wallet;

public class Agent {
    private static final String TAG = "AMAA-Agent";

    public Agent(){
        Log.d(TAG, "Here is a message");
        try{
            Wallet.createWallet("{'id':'abc'}", "{'key': '123'}");
        }catch(Exception e){
            e.printStackTrace();
        }

    }
}
