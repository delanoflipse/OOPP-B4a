package user;

/**
 * A user preference
 */
public class UserPreferenceValue {
    /** The stored string value */
    String value;

    /**
     * Return the value as int
     * @return Integer
     */
    public int valueAsInt() {
        return Integer.parseInt(value.replaceAll("[^\\d]", ""));
    }

    /**
     * Return value as boolean
     * @return Boolean
     */
    public boolean valueAsBoolean() {
        return value.toLowerCase().equals("true");
    }
}
