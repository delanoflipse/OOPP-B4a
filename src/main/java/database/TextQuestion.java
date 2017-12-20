package database;

import java.util.ArrayList;

/**
 * Multiple choice question
 */
public class TextQuestion extends Question {
    public ArrayList<TextAnswer> answers;

    public TextQuestion() {
        this.answers = new ArrayList<TextAnswer>();
    }

    public void addAnswer(TextAnswer answer) {
        answers.add(answer);
    }

    @Override
    public boolean isCorrect(int index) {
        if (answers.size() > index) {
            return answers.get(index).correct;
        }

        return false;
    }
}
