import org.javalite.activejdbc.Base;

import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        askMultipleChoichequestions();
        addQuetions();
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

    public static void addQuestions(){
        Scanner input = new Scanner(System.in);

        System.out.println("1. What is the question you'd like to add?");
        String question1 = input.nextLine();
        System.out.println("2. What is the first wrong answer you'd like to add?");
        String answer1 = input.nextLine();
        System.out.println("3. What is the second wrong answer you'd like to add?");
        String answer2 = input.nextLine();
        System.out.println("4. What is the third wrong answer you'd like to add?");
        String answer3 = input.nextLine();
        System.out.println("5. What is the correct answer you'd like to add?");
        String correctanswer = input.nextLine();

        String fullquestion = (question1 + "\n" + answer1 + "\n" + answer2 + "\n" + answer3 + "\n" + correctanswer);
        System.out.println(fullquestion);
    }


}
