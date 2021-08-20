package tech.indicio.ariesmobileagentandroid;

import android.util.Log;
import android.util.Pair;

import org.hyperledger.indy.sdk.IndyException;
import org.hyperledger.indy.sdk.anoncreds.Anoncreds;
import org.hyperledger.indy.sdk.did.Did;
import org.hyperledger.indy.sdk.did.DidResults;
import org.hyperledger.indy.sdk.wallet.Wallet;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

public class IndyWallet {
    private static final String TAG = "AMAA-IndyWallet";
    private Wallet wallet;
    public String agentId;

    private String walletKey;

    /**
     * @return walletConfig
     * @throws JSONException
     */
    private String getWalletConfig() throws JSONException {
        return new JSONObject()
                .put("id", this.agentId)
                .toString();
    }

    /**
     * @return walletCredentials
     * @throws JSONException
     */
    private String getWalletCredentials() throws JSONException {
        return new JSONObject()
                .put("key", this.walletKey)
                .toString();
    }

    public IndyWallet(String agentId, String walletKey) throws JSONException, InterruptedException, ExecutionException, IndyException {
        //Load wallet
        this.agentId = agentId;
        this.walletKey = walletKey;
        this.wallet = this.openWallet(getWalletConfig(), getWalletCredentials(), this.agentId);
    }

    private void createWallet(String walletConfig, String walletCredentials) throws IndyException, ExecutionException, InterruptedException {
        //Try to create wallet, will fail if wallet already exists
        Log.d(TAG, "Creating wallet");
        Wallet.createWallet(walletConfig, walletCredentials).get();
        Log.d(TAG, "Wallet created");
    }

    private Wallet openWallet(String walletConfig, String walletCredentials, String agentId) throws IndyException, ExecutionException, InterruptedException {
        //204 wallet not found
        //203 wallet already exists
        try {
            Wallet wallet;
            try {
                //Open wallet
                Log.d(TAG, "Wallet opening");
                wallet = Wallet.openWallet(walletConfig, walletCredentials).get();
            } catch (Exception e) {
                IndySdkRejectResponse rejectResponse = new IndySdkRejectResponse(e);
                if (rejectResponse.getCode().equals("204")) {
                    Log.d(TAG, "Wallet not found");
                    createWallet(walletConfig, walletCredentials);
                    //Open wallet
                    Log.d(TAG, "Retrying to open wallet");
                    wallet = Wallet.openWallet(walletConfig, walletCredentials).get();
                } else {
                    throw e;
                }
            }
            Log.d(TAG, "Wallet opened");
            createMasterSecret(wallet, agentId);
            return wallet;
        } catch (Exception e) {
            IndySdkRejectResponse rejectResponse = new IndySdkRejectResponse(e);
            Log.d(TAG, "Failed to open wallet, reason: " + rejectResponse.getMessage());
            throw e;
        }

    }

    private void createMasterSecret(Wallet wallet, String agentId) throws IndyException {
        try{
            Anoncreds.proverCreateMasterSecret(wallet, agentId);
        } catch (IndyException e) {
            IndySdkRejectResponse rejectResponse = new IndySdkRejectResponse(e);
            if (!rejectResponse.getCode().equals("404")) {
                throw e;
            }
        }
    }

    /**
     *
     * @return A Pair<String DID, String verkey>
     * @throws IndyException
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public Pair<String, String> generateDID() throws IndyException, ExecutionException, InterruptedException {
        DidResults.CreateAndStoreMyDidResult didResult = Did.createAndStoreMyDid(wallet, "{}").get();
        return new Pair<>(didResult.getDid(), didResult.getVerkey());
    }

}
