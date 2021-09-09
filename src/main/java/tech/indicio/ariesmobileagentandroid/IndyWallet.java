package tech.indicio.ariesmobileagentandroid;

import android.util.Log;
import android.util.Pair;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.hyperledger.indy.sdk.IndyException;
import org.hyperledger.indy.sdk.anoncreds.Anoncreds;
import org.hyperledger.indy.sdk.crypto.Crypto;
import org.hyperledger.indy.sdk.did.Did;
import org.hyperledger.indy.sdk.did.DidResults;
import org.hyperledger.indy.sdk.non_secrets.WalletRecord;
import org.hyperledger.indy.sdk.non_secrets.WalletSearch;
import org.hyperledger.indy.sdk.wallet.Wallet;
import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class IndyWallet {
    private static final String TAG = "AMAA-IndyWallet";
    private final Wallet wallet;
    private final String walletKey;
    public String agentId;

    public IndyWallet(String agentId, String walletKey) throws JSONException, InterruptedException, ExecutionException, IndyException {
        //Load wallet
        this.agentId = agentId;
        this.walletKey = walletKey;
        this.wallet = this.openWallet(getWalletConfig(), getWalletCredentials(), this.agentId);
    }

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

    private void createWallet(String walletConfig, String walletCredentials) throws IndyException, ExecutionException, InterruptedException {
        //Try to create wallet, will fail if wallet already exists
        Log.d(TAG, "Creating wallet");
        Wallet.createWallet(walletConfig, walletCredentials).get();
        Log.d(TAG, "Wallet created");
    }

    public void deleteWallet() throws IndyException, JSONException, ExecutionException, InterruptedException {
        Log.d(TAG, "Deleting wallet");
        Wallet.deleteWallet(getWalletConfig(), getWalletCredentials()).get();
        Log.d(TAG, "Wallet deleted");
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
        try {
            Anoncreds.proverCreateMasterSecret(wallet, agentId);
        } catch (IndyException e) {
            IndySdkRejectResponse rejectResponse = new IndySdkRejectResponse(e);
            if (!rejectResponse.getCode().equals("404")) {
                throw e;
            }
        }
    }

    /**
     * @return A Pair<String DID, String verkey>
     * @throws IndyException
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public Pair<String, String> generateDID() throws IndyException, ExecutionException, InterruptedException {
        DidResults.CreateAndStoreMyDidResult didResult = Did.createAndStoreMyDid(wallet, "{}").get();
        return new Pair<>(didResult.getDid(), didResult.getVerkey());
    }


    public byte[] packMessage(String message, String[] recipientKeys, String senderVerkey) throws IndyException, ExecutionException, InterruptedException {
        Log.d(TAG, "Packing Message: ");

        Gson gson = new Gson();
        String recipientKeysString = gson.toJson(recipientKeys);

        Log.d(TAG, "Message: "+message);
        Log.d(TAG, "RecipientKeys: "+recipientKeysString);
        Log.d(TAG, "senderVerkey: "+senderVerkey);


        return Crypto.packMessage(this.wallet, recipientKeysString, senderVerkey, message.getBytes(StandardCharsets.UTF_8)).get();
    }

    public byte[] unpackMessage(byte[] messageBytes) throws IndyException, ExecutionException, InterruptedException {
        Log.d(TAG, "Unpacking Message");
        return Crypto.unpackMessage(this.wallet, messageBytes).get();
    }


    /**
     * @param type
     * @param id
     * @param value
     * @param tags
     */
    public void storeRecord(String type, String id, String value, JsonObject tags) throws IndyException, ExecutionException, InterruptedException {

        //Tags need to be saved as a string
        for(String key : tags.keySet()){
             JsonElement property = tags.get(key);
            tags.remove(key);
            tags.addProperty(key, property.toString().replaceAll("\"", ""));
        }

        Log.d(TAG, "Type:\n\t"+type+"\nID:\n\t"+id+"\nValue:\n\t"+value+"\nTags:\n\t"+tags.toString());

        WalletRecord.add(wallet, type, id, value, tags.toString()).get();
    }

    public String retrieveRecord(String type, String id) throws JSONException, IndyException, ExecutionException, InterruptedException {
        String config = new JSONObject()
                .put("retrieveType", true)
                .put("retrieveValue", true)
                .put("retrieveTags", true)
                .toString();
        return WalletRecord.get(wallet, type, id, config).get();
    }


    /**
     * @param type  Record type
     * @param query MongoDB style query to wallet record tags:
     *              {
     *              "tagName": "tagValue",
     *              $or: {
     *              "tagName2": { $regex: 'pattern' },
     *              "tagName3": { $gte: '123' },
     *              }
     *              }
     * @return ArrayList of search results
     */
    public ArrayList<String> searchByQuery(String type, JsonObject query, int limit) throws IndyException, JSONException, ExecutionException, InterruptedException {
        Gson gson = new Gson();
        JsonObject options = new JsonObject();
        options.addProperty("limit", limit);

        WalletSearch ws = WalletSearch.open(wallet, type, gson.toJson(query), gson.toJson(options)).get();
        ArrayList<String> queryResults = new ArrayList<>();

        String fetchedRecords = ws.fetchNextRecords(wallet, limit).get();
        JsonArray json = new JsonParser().parse(fetchedRecords).getAsJsonObject().get("records").getAsJsonArray();

        for (JsonElement record : json) {
            String stringJson = gson.toJson(record);
            String stringRecord = new JsonParser().parse(stringJson).getAsJsonObject().get("value").getAsString();
            Log.d(TAG, stringRecord);
            queryResults.add(stringRecord);
        }
        return queryResults;

    }

    public void updateRecord(String type, String id, String value, JsonObject tags) throws IndyException, ExecutionException, InterruptedException {

        JsonObject tagsClone = tags.deepCopy();
        //Tags need to be saved as a string
        for (String key : tagsClone.keySet()) {
            JsonElement property = tags.get(key);
            tags.remove(key);
            tags.addProperty(key, property.toString().replaceAll("\"", ""));
        }

        WalletRecord.updateValue(wallet, type, id, value).get();
        WalletRecord.updateTags(wallet, type, id, tagsClone.toString()).get();
    }

    public boolean verify(String signerVerkey, byte[] message, byte[] signature) throws IndyException, ExecutionException, InterruptedException {
        return Crypto.cryptoVerify(signerVerkey, message, signature).get();
    }


}
