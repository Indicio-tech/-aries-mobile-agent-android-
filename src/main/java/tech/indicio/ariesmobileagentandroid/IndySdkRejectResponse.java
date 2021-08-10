package tech.indicio.ariesmobileagentandroid;

import android.util.Log;

import com.google.gson.Gson;

import org.hyperledger.indy.sdk.ErrorCode;
import org.hyperledger.indy.sdk.IndyException;

import java.util.concurrent.ExecutionException;

//Sourced from indy-sdk-react-native
// (https://github.com/hyperledger/indy-sdk-react-native/)
public class IndySdkRejectResponse {
    private static final String TAG = "AMAA-Agent";

    private String name = "IndyError";
    private int indyCode;
    private String indyName;
    private String message;
    private String indyCurrentErrorJson;
    private String indyMessage;
    private String indyBacktrace;

    public IndySdkRejectResponse(Throwable e) {
        // Indy bridge exposed API should return consistently only numeric code
        // When we don't get IndyException and Indy SDK error code we return zero as default
        indyCode = 0;

        if (e instanceof ExecutionException) {
            Throwable cause = e.getCause();
            if (cause instanceof IndyException) {
                IndyException indyException = (IndyException) cause;
                indyCode = indyException.getSdkErrorCode();

                ErrorCode errorCode = ErrorCode.valueOf(indyCode);
                indyName = errorCode.toString();
                message = indyName;
                // TODO: we can't extract indyCurrentErrorJson directly from indyError
                // So we would need to extract it ourelf as done here
                // https://github.com/hyperledger/indy-sdk/blob/bafa3bbcca2f7ef4cf5ae2aca01b1dbf7286b924/wrappers/java/src/main/java/org/hyperledger/indy/sdk/IndyException.java#L71-L83
                indyMessage = indyException.getSdkMessage();
                indyBacktrace = indyException.getSdkBacktrace();
            } else {
                Log.e(TAG, "Unhandled non IndyException", e);
            }
        } else {
            Log.e(TAG, "Unhandled non ExecutionException", e);
        }
    }

    public String getCode() {
        return String.valueOf(indyCode);
    }

    public String getMessage() {
        return message;
    }

    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
