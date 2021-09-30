package tech.indicio.ariesmobileagentandroid.messaging;

import java.util.HashMap;

public abstract class MessageListener {

    protected abstract void callback(String type, BaseMessage message, String senderVerkey);

    protected abstract HashMap<String, Class<? extends BaseMessage>> getSupportedMessages();
}
