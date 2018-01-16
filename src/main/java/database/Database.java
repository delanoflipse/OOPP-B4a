package database;

import java.util.ArrayList;

/**
 * Database container class
 */
public class Database {
    /** list of questions */
    public static ArrayList<Question> questions;
    /** filename of the database */
    public static final String filename = "db.data";

    /**
     * Make the parser read and parse the database
     */
    public static void loadDatabase() {
        DatabaseParser parser = new DatabaseParser(filename);
        questions = parser.parse();
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
