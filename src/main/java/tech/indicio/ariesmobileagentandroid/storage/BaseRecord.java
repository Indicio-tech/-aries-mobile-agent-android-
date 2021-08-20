package tech.indicio.ariesmobileagentandroid.storage;

import org.json.JSONObject;

public abstract class BaseRecord {
    public static String type;
    public String id;
    public JSONObject tags;

    public abstract String getType();
}
