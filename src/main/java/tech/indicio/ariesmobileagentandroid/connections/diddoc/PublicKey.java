package tech.indicio.ariesmobileagentandroid.connections.diddoc;

public class PublicKey {
    public String id;
    public String type;
    public String controller;
    public String publicKeyBase58;

    public PublicKey(String id, String type, String controller, String publicKeyBase58) {
        this.id = id;
        this.type = type;
        this.controller = controller;
        this.publicKeyBase58 = publicKeyBase58;
    }
}
