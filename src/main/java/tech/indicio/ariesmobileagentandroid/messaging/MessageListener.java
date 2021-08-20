package tech.indicio.ariesmobileagentandroid.messaging;

import android.util.Pair;

import java.sql.Array;
import java.util.ArrayList;
import java.util.HashMap;

public abstract class MessageListener {

    public abstract void _callback(String type, BaseMessage message);
    public abstract HashMap<String, Class<? extends BaseMessage>> _getSupportedMessages();
}
