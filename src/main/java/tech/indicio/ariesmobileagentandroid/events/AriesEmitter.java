package tech.indicio.ariesmobileagentandroid.events;

import java.util.ArrayList;

public class AriesEmitter {
    private final ArrayList<AriesListener> listeners = new ArrayList<>();

    public void registerListener(AriesListener listener) {
        this.listeners.add(listener);
    }

    public void removeListener(AriesListener listener) {
        this.listeners.remove(listener);
    }

    public void emitEvent(AriesEvent event) {
        for (AriesListener listener : listeners) {
            listener.onEvent(event);
        }
    }
}
