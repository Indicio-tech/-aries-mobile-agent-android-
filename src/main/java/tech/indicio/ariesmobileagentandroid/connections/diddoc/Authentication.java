package tech.indicio.ariesmobileagentandroid.connections.diddoc;

public class Authentication {
    public String type;
    public String publicKey;

    public Authentication(String type, String publicKey) {
        this.type = type;
        this.publicKey = publicKey;
    }
}
