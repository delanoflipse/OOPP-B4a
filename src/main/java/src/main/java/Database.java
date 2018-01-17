package src.main.java;

import java.util.ArrayList;

public class Database {
    public static ArrayList<Question> questions;
    public static final String filename = "db.data";

    public static void loadDatabase() {
        DatabaseParser parser = new DatabaseParser(filename);
        questions = parser.parse();
    }

    public static ArrayList<Question> getQuestionsForLevel(int level) {
        ArrayList<Question> set = new ArrayList<>();

        for (Question q : questions) {
            if (q.level == level) {
                set.add(q);
            }
        }

        return set;
    }
}
