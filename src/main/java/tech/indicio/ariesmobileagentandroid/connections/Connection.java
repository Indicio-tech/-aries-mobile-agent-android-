package tech.indicio.ariesmobileagentandroid.connections;

import com.google.gson.annotations.SerializedName;

import tech.indicio.ariesmobileagentandroid.connections.diddoc.DIDDoc;

public class Connection {
    @SerializedName("DID")
    public String did;

    @SerializedName("DIDDoc")
    public DIDDoc didDoc;

    public Connection(String did, DIDDoc didDoc) {
        this.did = did;
        this.didDoc = didDoc;
    }
}
