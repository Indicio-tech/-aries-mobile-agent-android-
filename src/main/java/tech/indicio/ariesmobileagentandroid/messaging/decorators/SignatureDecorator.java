package tech.indicio.ariesmobileagentandroid.messaging.decorators;

import com.google.gson.annotations.SerializedName;

public class SignatureDecorator {

    @SerializedName("@type")
    public String type;

    @SerializedName("sig_data")
    public String sigData;

    public String signature;
    public String signer;

    public SignatureDecorator(String type, String signature, String sigData, String signer) {
        this.type = type;
        this.signature = signature;
        this.sigData = sigData;
        this.signer = signer;
    }
}
