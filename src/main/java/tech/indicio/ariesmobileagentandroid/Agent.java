package tech.indicio.ariesmobileagentandroid;


import android.util.Log;

import org.hyperledger.indy.sdk.IndyException;
import org.hyperledger.indy.sdk.pool.Pool;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

import tech.indicio.ariesmobileagentandroid.admin.Admin;
import tech.indicio.ariesmobileagentandroid.basicMessaging.BasicMessaging;
import tech.indicio.ariesmobileagentandroid.connections.ConnectionRecord;
import tech.indicio.ariesmobileagentandroid.connections.Connections;
import tech.indicio.ariesmobileagentandroid.events.AriesEmitter;
import tech.indicio.ariesmobileagentandroid.messaging.MessageReceiver;
import tech.indicio.ariesmobileagentandroid.messaging.MessageSender;
import tech.indicio.ariesmobileagentandroid.messaging.ThreadHandler;
import tech.indicio.ariesmobileagentandroid.storage.Storage;


public class Agent {
    private static final String TAG = "AMAA-Agent";
    public Connections connections;
    public BasicMessaging basicMessaging;
    public AriesEmitter eventEmitter;
    public Admin admin;
    private final Storage storage;
    private IndyWallet indyWallet;
    protected ThreadHandler threadHandler;
    private MessageReceiver messageReceiver;
    private MessageSender messageSender;
    private Pool pool;
    private String ledgerConfig;
    private String adminInvitationUrl = null;

    /**
     * @param configJson stringified json config file {
     *                   "agentId": String - identifier for indy wallet.
     *                   "walletKey": String - agent encryption key.
     *                   "ledgerConfig": (optional) ledger config json {
     *                   ledgerName: String - Name for ledger pool.
     *                   genesisFileLocation: String - File location of downloaded genesis file.
     *                   }
     *                   "adminInvitationUrl": (optional) String - the invitation URL of the admin connection
     *                   }
     */
    public Agent(String configJson) {
        //Load indy library
        Log.d(TAG, "Loading indy");
        System.loadLibrary("indy");
        Log.d(TAG, "Indy loaded, creating wallet...");


        try {
            JSONObject config = new JSONObject(configJson);

            String agentId = config.getString("agentId");
            String walletKey = config.getString("walletKey");
            this.indyWallet = new IndyWallet(agentId, walletKey);

            //Load pool if exists
            if (config.has("ledgerConfig")) {
                this.ledgerConfig = config.getString("ledgerConfig");
                this.pool = this.openPool(this.ledgerConfig);
            }

            if (config.has("adminInvitationUrl")) {
                this.adminInvitationUrl = config.getString("adminInvitationUrl");
            }

        } catch (Exception e) {
            IndySdkRejectResponse rejectResponse = new IndySdkRejectResponse(e);
            String code = rejectResponse.getCode();
            String json = rejectResponse.toJson();
            Log.e(TAG, "INDY ERROR");
            Log.e(TAG, code);
            Log.e(TAG, json);
            Log.e(TAG, e.getMessage());
        }

        //Create eventEmitter object
        this.eventEmitter = new AriesEmitter();

        //Register storage and record classes
        this.storage = new Storage(indyWallet, eventEmitter);
        storage.registerRecordClass(ConnectionRecord.type, ConnectionRecord.class);


        //Creating MessageReceiver and registering connections
        try {
            this.threadHandler = new ThreadHandler();
            this.messageReceiver = new MessageReceiver(this.indyWallet, this.threadHandler);
            this.messageSender = new MessageSender(this.indyWallet, this.messageReceiver, this.threadHandler); //Put transport service inside of messageSender

            this.connections = new Connections(this.indyWallet, this.messageSender, this.storage);
            this.messageReceiver.registerListener(this.connections);

            this.basicMessaging = new BasicMessaging(this.indyWallet, this.messageSender, this.storage, this.connections);
            this.messageReceiver.registerListener(this.basicMessaging);

            if (this.adminInvitationUrl == null)
                this.admin = new Admin(this.storage, this.messageSender, this.eventEmitter, this.connections);
            else
                this.admin = new Admin(this.storage, this.messageSender, this.eventEmitter, this.connections, this.adminInvitationUrl);
            this.messageReceiver.registerListener(this.admin);

            //Register Transports
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void createPool(String ledgerName, String poolConfig) throws IndyException, ExecutionException, InterruptedException {
        Log.d(TAG, "Creating ledger pool " + ledgerName);
        Pool.createPoolLedgerConfig(ledgerName, poolConfig).get();
        Log.d(TAG, "Ledger " + ledgerName + ": created");
    }

    private Pool openPool(String ledgerConfig) throws IndyException, ExecutionException, InterruptedException, JSONException {
        try {
            Pool pool;
            JSONObject ledgerObj = new JSONObject(ledgerConfig);
            String genesisFilePath = ledgerObj.getString("genesisFileLocation");
            String ledgerName = ledgerObj.getString("ledgerName");

            //Create Json string for ledger pool
            String poolConfig = new JSONObject()
                    .put("genesis_txn", genesisFilePath)
                    .toString();
            Log.d(TAG, poolConfig);

            try {
                //Open pool
                Log.d(TAG, "Opening ledger pool");
                pool = Pool.openPoolLedger(ledgerName, null).get();
            } catch (Exception e) {
                IndySdkRejectResponse rejectResponse = new IndySdkRejectResponse(e);
                if (rejectResponse.getCode().equals("300")) {
                    try {
                        createPool(ledgerName, poolConfig);
                        Log.d(TAG, "Retrying to open ledger pool");
                        pool = Pool.openPoolLedger(ledgerName, null).get();
                    } catch (IndyException f) {
                        throw f;
                    }
                } else {
                    Log.d(TAG, "code: " + rejectResponse.getCode());
                    Log.d(TAG, "Failed to open ledger pool, reason: " + rejectResponse.getMessage());
                    throw e;
                }
            }
            Log.d(TAG, "Ledger " + ledgerName + ": opened");
            return pool;
        } catch (Exception e) {
            throw e;
        }
    }


    public void deleteAgent() throws IndyException, ExecutionException, InterruptedException, JSONException {
        this.indyWallet.deleteWallet();
        if (this.pool != null) {
            JSONObject ledgerConfig = new JSONObject(this.ledgerConfig);
            Log.d(TAG, "Deleting ledger pool");
            Pool.deletePoolLedgerConfig(ledgerConfig.getString("ledgername")).get();
            Log.d(TAG, "Ledger pool deleted");
        }
    }

//    public void closeAgent() throws InterruptedException, ExecutionException, IndyException {
//        if (pool != null) {
//            this.pool.closePoolLedger().get();
//        }
//        if (wallet != null) {
//            this.wallet.closeWallet().get();
//        }
//
//    }
}

