package tech.indicio.ariesmobileagentandroid.connections.diddoc;

public class IndyService {
    public String id;
    public String type;
    public int priority;
    public String[] recipientKeys;
    public String[] routingKeys;
    public String serviceEndpoint;

    public IndyService(String id, String type, int priority, String[] recipientKeys, String[] routingKeys, String serviceEndpoint) {
        this.id = id;
        this.type = type;
        this.priority = priority;
        this.recipientKeys = recipientKeys;
        this.routingKeys = routingKeys;
        this.serviceEndpoint = serviceEndpoint;
    }
}
