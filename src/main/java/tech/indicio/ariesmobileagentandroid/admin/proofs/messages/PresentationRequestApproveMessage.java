package tech.indicio.ariesmobileagentandroid.admin.proofs.messages;

import android.util.Log;

import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;

import java.security.Key;
import java.util.UUID;

import tech.indicio.ariesmobileagentandroid.admin.messages.BaseOutboundAdminMessage;
import tech.indicio.ariesmobileagentandroid.admin.proofs.eventRecords.AdminMatchingCredentialsRecord;
import tech.indicio.ariesmobileagentandroid.admin.proofs.proofObjects.MatchingCredentials;
import tech.indicio.ariesmobileagentandroid.admin.proofs.proofObjects.PresentationRequest;
import tech.indicio.ariesmobileagentandroid.messaging.BaseMessage;

public class PresentationRequestApproveMessage  extends BaseOutboundAdminMessage {
    @SerializedName("@type")
    public final static String type = "did:sov:BzCbsNYhMrjHiqZDTUASHg;spec/admin-holder/0.1/presentation-request-approve";

    @SerializedName("@id")
    public String id;

    @SerializedName("presentation_exchange_id")
    public String presentationExchangeId;

    @SerializedName("self_attested_attributes")
    public JsonObject selfAttestedAttributes;

    @SerializedName("requested_attributes")
    public JsonObject requestedAttributes;

    @SerializedName("requested_predicates")
    public JsonObject requestedPredicates;

    public String comment;

    //TODO - Remove client side caching for soon to be added presentation request in MatchingCredentialsMessage
    public PresentationRequestApproveMessage(AdminMatchingCredentialsRecord record, PresentationRequest presentationRequest){
        this.id = UUID.randomUUID().toString();
        this.presentationExchangeId = record.presentationExchangeId;
        this.requestedAttributes = new JsonObject();
        this.requestedPredicates = new JsonObject();
        this.selfAttestedAttributes = new JsonObject();
        for(MatchingCredentials match : record.matchingCredentials){
            String referent = match.credInfo.referent;
            for(String specifier : match.credInfo.attrs.keySet()){
                if(!requestedAttributes.has(specifier)){
                    if(presentationRequest.requestedAttributes.has(specifier)){
                        JsonObject credDetails = new JsonObject();
                        credDetails.addProperty("cred_id", referent);
                        credDetails.addProperty("revealed", true);
                        requestedAttributes.add(specifier, credDetails);
                    }else if(presentationRequest.requestedPredicates.has(specifier)){
                        JsonObject credDetails = new JsonObject();
                        credDetails.addProperty("cred_id", referent);
                        requestedPredicates.add(specifier, credDetails);
                    }
                }
            }
        }
    }
}
