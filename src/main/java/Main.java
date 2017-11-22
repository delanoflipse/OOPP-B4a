import org.javalite.activejdbc.Base;

import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Base.open("org.sqlite.JDBC", "jdbc:sqlite:db.sqlite3", "", "");
        Scanner input = new Scanner(System.in);
        int correctAnswers = 0;

        List<Question> questions = Question.findAll();

        int i = 1;
        for (Question question : questions) {
            List<Answer> answers = question.getAll(Answer.class);
            System.out.println("Vraag " + i + ": " + question.get("text"));

            int j = 0;
            for (Answer answer : answers) {
                char x = (char) ('A' + j);
                System.out.println("\t " + x + ". " + answer.get("text"));
                j++;
            }

            boolean valid = false;

            while (!valid) {
                char c = input.nextLine().charAt(0);
                int index = (int) c - (int)'A';

                if (index < 0 || index > answers.size() - 1 ) {
                    System.out.println("Please enter a valid answer");
                } else {
                    valid = true;

                    if ((int) answers.get(index).get("correct") == 1) {
                        correctAnswers++;
                        System.out.println("That answer is correct");
                    } else {
                        System.out.println("That answer is incorrect");
                    }
                }
            }

            i++;
        }

        System.out.println("Your score: " + correctAnswers + "/" + questions.size());

    }
}
