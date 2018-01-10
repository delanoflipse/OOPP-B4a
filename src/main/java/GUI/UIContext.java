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
}
