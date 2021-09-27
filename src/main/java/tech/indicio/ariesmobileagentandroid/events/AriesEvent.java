package tech.indicio.ariesmobileagentandroid.events;

import tech.indicio.ariesmobileagentandroid.storage.BaseRecord;

public class AriesEvent {
    public BaseRecord updatedRecord;
    public BaseRecord previousRecord;
    public EventType eventType;
    public String recordType;

    public AriesEvent(BaseRecord newRecord) {
        this.updatedRecord = newRecord;
        this.eventType = EventType.RECORD_CREATION;
        this.recordType = newRecord.getType();
    }

    public AriesEvent(BaseRecord updatedRecord, BaseRecord previousRecord) {
        this.updatedRecord = updatedRecord;
        this.previousRecord = previousRecord;
        this.eventType = EventType.RECORD_UPDATE;
        this.recordType = updatedRecord.getType();
    }

    enum EventType {
        RECORD_UPDATE,
        RECORD_CREATION
    }
}
