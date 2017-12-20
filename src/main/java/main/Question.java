package main;
import java.util.ArrayList;

/**
 * This is the base question class
 */
public abstract class Question {
    public String text;
    public int level;
    public ArrayList<Answer> answers;

    public boolean isCorrect(int index) {return true;};
    public boolean isCorrect(String answer) {return true;};
    public boolean isCorrect(int x, int y) {return true;};
}
