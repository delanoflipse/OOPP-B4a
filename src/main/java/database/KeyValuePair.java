package database;

/**
 * A String:String value pair
 * Used in DatabaseParser
 */
class KeyValuePair {
    String key;
    String value;

    public KeyValuePair(String key, String value) {
        this.key = key;
        this.value = value;
    }

    /**
     * Get value as integer
     * @return Integer from string
     */
    public int valueAsInt() {
        return Integer.parseInt(value.replaceAll("[^\\d]", ""));
    }
}
