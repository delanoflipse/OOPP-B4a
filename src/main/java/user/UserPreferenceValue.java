package user;

public class UserPreferenceValue {
    String value;
    public int valueAsInt() {
        return Integer.parseInt(value.replaceAll("[^\\d]", ""));
    }
    public boolean valueAsBoolean() {
        return value.toLowerCase().equals("true");
    }
}
