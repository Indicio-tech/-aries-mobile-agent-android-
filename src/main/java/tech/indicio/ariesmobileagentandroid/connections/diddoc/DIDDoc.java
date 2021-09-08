package tech.indicio.ariesmobileagentandroid.connections.diddoc;

import com.google.gson.annotations.SerializedName;

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

    public static DIDDoc createDefaultDIDDoc(String did, String verkey) {
        String context = "https://w3id.org/did/v1";
        String id = did;
        PublicKey publicKey = new PublicKey(id, "Ed25519VerificationKey2018", id, verkey);
        Authentication authentication = new Authentication("Ed25519SignatureAuthentication2018", id + "#1");
        IndyService service = new IndyService(did + "#did-communication",
                "did-communication",
                0,
                new String[]{verkey},
                new String[]{},
                "didcomm:transport/queue");

        DIDDoc didDoc = new DIDDoc(context,
                id,
                new PublicKey[]{publicKey},
                new Authentication[]{authentication},
                new IndyService[]{service});
        return didDoc;
    }

}
