package tech.indicio.ariesmobileagentandroid.admin.connections.messages;

import com.google.gson.annotations.SerializedName;

import java.util.UUID;

import tech.indicio.ariesmobileagentandroid.admin.messages.BaseOutboundAdminMessage;

public class ReceiveInvitationMessage extends BaseOutboundAdminMessage {
    @SerializedName("@type")
    public final static String type = "https://github.com/hyperledger/aries-toolbox/tree/master/docs/admin-connections/0.1/receive-invitation";

    @SerializedName("auto_accept")
    public boolean autoAccept;

    @SerializedName("mediation_id")
    public String mediationId;

    public String invitation;

    public ReceiveInvitationMessage(String invitationUrl) {
        this.id = UUID.randomUUID().toString();
        this.invitation = invitationUrl;
        this.autoAccept = true; //default autoAccept to true
    }

    public ReceiveInvitationMessage(String invitation, boolean autoAccept) {
        this(invitation);
        this.autoAccept = autoAccept;
    }

    public ReceiveInvitationMessage(String invitation, String mediationId) {
        this(invitation);
        this.mediationId = mediationId;
    }

    public ReceiveInvitationMessage(String invitation, boolean autoAccept, String mediationId) {
        this(invitation, autoAccept);
        this.mediationId = mediationId;
    }
}
