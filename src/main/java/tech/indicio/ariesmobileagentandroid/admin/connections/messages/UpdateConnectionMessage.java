package tech.indicio.ariesmobileagentandroid.admin.connections.messages;

import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;

import java.util.UUID;

import tech.indicio.ariesmobileagentandroid.admin.messages.BaseOutboundAdminMessage;

public class UpdateConnectionMessage extends BaseOutboundAdminMessage {
    @SerializedName("@type")
    public final static String type = "https://github.com/hyperledger/aries-toolbox/tree/master/docs/admin-connections/0.1/update";

    @SerializedName("@id")
    public String id;

    @SerializedName("connection_id")
    public String connectionId;

    public String label;
    public String role;

    private UpdateConnectionMessage(String connectionId) {
        this.connectionId = connectionId;
        this.id = UUID.randomUUID().toString();
    }


    /**
     * @param config {
     *               String role (Optional): Modify role of connection
     *               String label (Optional): Modify label of connection
     *               }
     */
    public UpdateConnectionMessage(String connectionId, JsonObject config) {
        this(connectionId);
        if (config.has("role"))
            this.role = config.get("role").toString();
        if (config.has("label"))
            this.label = config.get("label").toString();
    }
}
