package tech.indicio.ariesmobileagentandroid.messaging;

import java.util.HashMap;

public abstract class MessageListener {

    public abstract void _callback(String type, BaseMessage message, String senderVerkey);

    public abstract HashMap<String, Class<? extends BaseMessage>> _getSupportedMessages();
}
