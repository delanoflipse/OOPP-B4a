package database;

/**
 * A String:String value pair
 * Used in DatabaseParser
 */
public class KeyValuePair {
    public String key;
    public String value;

    public KeyValuePair(String key, String value) {
        this.key = key;
        this.value = value;
    }

    /**
     * Parse line to key:value pair
     * @param line Line with format "key:pair"
     * @return A pair, or null
     */
    public static KeyValuePair splitLine(String line, boolean useFirst) {
        if (line == null || line.length() == 0) {
            return null;
        }

        if (line.charAt(0) == '#') {
            return null;
        }

        int index = useFirst ? line.indexOf(':') : line.lastIndexOf(':');

        if (index == -1) {
            return null;
        }

        return new KeyValuePair(
                line.substring(0, index).trim(),
                line.substring(index + 1, line.length()).trim()
        );
    }

    public static KeyValuePair splitLine(String line) {
        return splitLine(line, true);
    }

    /**
     * Get value as integer
     * @return Integer from string
     */
    public int valueAsInt() {
        return Integer.parseInt(value.replaceAll("[^\\d]", ""));
    }
}
