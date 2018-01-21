package user;

import database.KeyValuePair;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * User data class
 */
public class UserData {
    /** User name */
    public String name;
    /** User filename */
    public String filename;
    /** Scores */
    public ArrayList<UserDateScore> scores;
    /** Preferences */
    private Map<String, UserPreferenceValue> preferences;

    public UserData() {
        scores = new ArrayList<>();
        preferences = new HashMap<>();
        setBasePreferences();

    }

    public void setBasePreferences() {
        setPreference("useTTS", "true");
        setPreference("showInList", "true");
        setPreference("isTutor", "false");
    }

    /**
     * Get integer preference
     * @param key The preference name
     * @return An int
     */
    public int getIntPreference(String key) {
        return preferences.get(key).valueAsInt();
    }

    /**
     * Get a String preference(default)
     * @param key The preference name
     * @return A String
     */
    public String getPreference(String key) {
        return preferences.get(key).value;
    }

    /**
     * Get boolean preference
     * @param key The preference name
     * @return A boolean
     */
    public Boolean getBoolPreference(String key) {
        UserPreferenceValue pref = preferences.get(key);
        if (pref == null) {
            System.out.println(key + " is not set");
            return false;
        } else {
            return pref.valueAsBoolean();
        }
    }

    public void setPreference(String key, String value) {
        preferences.put(key, new UserPreferenceValue(value));
    }

    /**
     * Parse a userdata file and return a new UserData instance
     * @param file The filename
     * @return a UserData instance
     */
    public static UserData parse(String file) {
        UserData data = new UserData();
        data.filename = file.toLowerCase();
        String line;
        // 0 = init values
        // 1 = scores
        // 2 = preferences
        int state = 0;
        KeyValuePair pair = null;

        try (BufferedReader br = new BufferedReader(new FileReader(data.filename))) {
            // for each line in the file
            while ((line = br.readLine()) != null) {
                pair = KeyValuePair.splitLine(line, false);
                int newState = state;
                if (line.equals("")) {
                    continue;
                }

                switch (state) {
                    case 0:
                        if (line.equals("scores")) {
                            newState = 1;
                            break;
                        }

                        if (pair.key.equals("name")) {
                            data.name = pair.value;
                        }

                        break;
                    case 1:
                        if (line.equals("preferences")) {
                            newState = 2;
                            break;
                        }

                        if (pair == null) {
                            break;
                        }

                        UserDateScore newScore = new UserDateScore();
                        newScore.date = pair.key;
                        newScore.score = pair.valueAsInt();
                        newScore.user = data;
                        data.scores.add(newScore);
                        break;
                    case 2:
                        if (pair == null) {
                            break;
                        }

                        UserPreferenceValue pref = new UserPreferenceValue();
                        pref.value = pair.value;
                        data.preferences.put(pair.key, pref);
                        break;
                }

                state = newState;
            }

            br.close();
        } catch (IOException e) {
            System.out.println("File does not yet exist");
//            return null;
        }

        return data;
    }

    /**
     * Save the file
     */
    public void save() {
        try {
            PrintWriter writer = new PrintWriter(filename, "UTF-8");
            writer.println("name: " + name);
            writer.println("scores");

            for (UserDateScore score : scores) {
                writer.println(score.date + ":" + score.score);
            }

            writer.println("preferences");

            for (Map.Entry<String, UserPreferenceValue> entry : preferences.entrySet()) {
                String key = entry.getKey();
                UserPreferenceValue value = entry.getValue();
                writer.println(key + ":" + value.value);
            }

            writer.close();
        } catch (IOException e) {
            System.out.println("Something went wrong writing the user file!");
        }
    }

}
