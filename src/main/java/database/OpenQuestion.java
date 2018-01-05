package database;

/**
 * Open question with 1 answer
 */
public class OpenQuestion extends Question {
    public String correctAnswer;

    public OpenQuestion() {}

    public void setCorrectAnswer(String answer) {
        correctAnswer = answer.trim().toLowerCase();
    }

    public boolean isCorrect(String answer) {
        return correctAnswer.equals(answer.trim().toLowerCase());
    }
}