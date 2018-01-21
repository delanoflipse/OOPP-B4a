package GUI;

import java.util.HashMap;
import java.util.Map;

public class UIContext {
    private Map<String, Object> values;

    public UIContext() {
        values = new HashMap<>();
    }

    public UIContext set(String key, Object value) {
        values.put(key, value);
        return this;
    }

    public boolean has(String key) {
        return values.containsKey(key);
    }

    public Object get(String key) {
        return values.get(key);
    }

    public String getString(String key) {
        return (String) values.get(key);
    }

    public boolean getBoolean(String key) {
        return (boolean) values.get(key);
    }

    public int getInteger(String key) {
        return (int) values.get(key);
    }
}
