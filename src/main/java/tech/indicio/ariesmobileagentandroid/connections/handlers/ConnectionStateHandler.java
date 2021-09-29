package tech.indicio.ariesmobileagentandroid.connections.handlers;

import java.util.UUID;

import tech.indicio.ariesmobileagentandroid.connections.ConnectionRecord;

public abstract class ConnectionStateHandler {
    public String id = UUID.randomUUID().toString();

    public abstract void onStateChange(
            ConnectionRecord connectionRecord,
            ConnectionRecord previousRecord
    );
}
