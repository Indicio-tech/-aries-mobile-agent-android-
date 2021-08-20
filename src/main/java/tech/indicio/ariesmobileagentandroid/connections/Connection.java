package tech.indicio.ariesmobileagentandroid.connections;

import com.google.gson.annotations.SerializedName;

import tech.indicio.ariesmobileagentandroid.connections.diddoc.DIDDoc;

public class Connection {
    @SerializedName("DID")
    String did;

    @SerializedName("DIDDoc")
    DIDDoc didDoc;
}
