import org.javalite.activejdbc.Base;

import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        askMultipleChoichequestions();
    }

    public static void askMultipleChoichequestions(){
        Base.open("org.sqlite.JDBC", "jdbc:sqlite:db.sqlite3", "", "");
        List<Question> questions = Question.findAll();

        Scanner input = new Scanner(System.in);

        int correctAnswers = 0;

        int i = 1;
        for (Question question : questions) {

            System.out.println("Question " + i + ":");
            System.out.println(question);

            char inputchar = input.nextLine().charAt(0);

            if (question.answer_correct(inputchar, input)) {
                correctAnswers++;
                System.out.println("That answer is correct");
            } else {
                System.out.println("That answer is incorrect");
            }
            input = new Scanner (System.in);

            i++;
        }

        System.out.println("Your score: " + correctAnswers + "/" + questions.size());
    }
}
