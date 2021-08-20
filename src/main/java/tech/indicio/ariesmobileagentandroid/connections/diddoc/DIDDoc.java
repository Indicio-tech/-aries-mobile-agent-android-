package tech.indicio.ariesmobileagentandroid.connections.diddoc;

import android.service.quickaccesswallet.WalletCard;

import com.google.gson.annotations.SerializedName;

import org.hyperledger.indy.sdk.anoncreds.Anoncreds;
import org.hyperledger.indy.sdk.did.Did;

public class DIDDoc {

    @SerializedName("@context")
    public String context;

    public String id;
    public PublicKey[] publicKey;
    public Authentication[] authentication;
    public IndyService[] service;

    public DIDDoc(String context, String id, PublicKey[] publicKey, Authentication[] authentication, IndyService[] service) {
        this.context = context;
        this.id = id;
        this.publicKey = publicKey;
        this.authentication = authentication;
        this.service = service;
    }

    public static DIDDoc createDefaultDIDDoc(){
        String context = "https://w3id.org/did/v1";

    }

}
