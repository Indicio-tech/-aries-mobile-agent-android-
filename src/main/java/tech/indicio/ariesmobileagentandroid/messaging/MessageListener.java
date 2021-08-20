package tech.indicio.ariesmobileagentandroid.messaging;

import java.util.HashMap;

public abstract class MessageListener {

    public abstract void _callback(String type, BaseMessage message);

    public abstract HashMap<String, Class<? extends BaseMessage>> _getSupportedMessages();
}
