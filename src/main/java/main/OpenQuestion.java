package main;
import java.util.ArrayList;

public class OpenQuestion extends Question {
    public String correctAnswer;

    public OpenQuestion() {

    }

    public void setCorrectAnswer(String answer) {
        correctAnswer = answer.trim().toLowerCase();
    }

    @Override
    public boolean isCorrect(String answer) {
        return correctAnswer.equals(answer.trim().toLowerCase());
    }
}