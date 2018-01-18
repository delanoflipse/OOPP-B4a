package database;

import GUI.UIContext;
import user.UserData;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Database container class
 */
public class Database extends UIContext{
    /** list of questions */
    public static ArrayList<Question> questions = new ArrayList<>();
    public static ArrayList<UserData> users = new ArrayList<>();
    /** filename of the database */
    public static final String filename = "db.data";

    /**
     * Make the parser read and parse the database
     */
    public static void loadDatabase() {
        DatabaseParser parser = new DatabaseParser(filename);
        parser.parse();

        System.out.println("Found " + questions.size() + " questions.");
        System.out.println("Found " + users.size() + " users.");
    }

    /**
     * Get the question for a given question level
     * @param level Difficulty level
     * @return A list of questions
     */
    public static ArrayList<Question> getQuestionsForLevel(int level, String type) {
        ArrayList<Question> set = new ArrayList<>();

        // simple filter function
        for (Question q : questions) {
            if (q.level == level && q.getClass().getSimpleName().equals(type)) {
                set.add(q);
            }
        }

        return set;
    }
}
