import java.util.ArrayList;

/**
 * This is the base question class
 */
public abstract class Question {
    public String text;
    public int level;

    public Question(String text, int level){
        this.text = text;
        this.level = level;
    }

    public abstract boolean isCorrect(int index);
}
