package main;
import java.util.ArrayList;

/**
 * This is the base question class
 */
public abstract class Question {
    public String text;
    public int level;
    public ArrayList<Answer> answers;

    //public abstract boolean isCorrect(int index);
}
