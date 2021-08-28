package tech.indicio.ariesmobileagentandroid.storage;

import com.google.gson.JsonObject;

public abstract class BaseRecord {
    public static String type;
    public String id;
    public JsonObject tags;

    public abstract String getType();
}
