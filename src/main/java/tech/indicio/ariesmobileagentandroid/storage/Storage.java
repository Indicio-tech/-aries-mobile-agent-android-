package tech.indicio.ariesmobileagentandroid.storage;

import android.util.Log;

import com.google.gson.Gson;

import org.hyperledger.indy.sdk.IndyException;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.concurrent.ExecutionException;
import tech.indicio.ariesmobileagentandroid.IndyWallet;

public class Storage {
    private static final String TAG = "AMAA-Storage";
    private IndyWallet indyWallet;

    private HashMap<String, Class<? extends BaseRecord>> recordClasses = new HashMap<>();

    public Storage(IndyWallet indyWallet){

        this.indyWallet = indyWallet;
    }

    public void storeRecord(BaseRecord record) throws IndyException {
        Gson gson = new Gson();
        String type =  record.getType();
        String id = record.id;
        JSONObject tags = record.tags;
        String value = gson.toJson(record);

        //Logs for testing
        try {
            String prettyString = new JSONObject(value).toString(4);
            Log.d(TAG, "Storing record of type "+type+".\n"+prettyString);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        indyWallet.storeRecord(type, id, value, tags);
    }


    public BaseRecord retrieveRecord(String type, String id) throws IndyException, ExecutionException, InterruptedException, JSONException {
        Log.d(TAG, "Retrieving record of type "+type+"...");
        //Check if type exists on recordClass map
        if(this.recordClasses.containsKey(type)){
            Class<? extends BaseRecord> recordClass = this.recordClasses.get(type);
            //If we get a matching record type then we will fetch the record
            String storageResult = this.indyWallet.retrieveRecord(type, id);

            JSONObject storageJSON = new JSONObject(storageResult);
            String record = (String) storageJSON.get("value");


            //Logs for testing
            try {
                String prettyString = new JSONObject(record).toString(4);
                Log.d(TAG, "Retrieved record.\n"+prettyString);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            //Parse record to desired class and return it
            Gson gson = new Gson();
            return gson.fromJson(record, recordClass);
        }else{
            throw new Error("Unsupported record");
        }
    }

    public void registerRecordClass(String type, Class<? extends BaseRecord> recordClass){
        this.recordClasses.put(type, recordClass);
    }

}
