import org.javalite.activejdbc.Base;

import java.awt.image.DataBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        addQuestions();
    }

    public static void askMultipleChoichequestions(){

        Database.loadDatabase();

        Scanner input = new Scanner(System.in);
        int correctAnswers = 0;

        int i = 1;
        ArrayList<Question> list = Database.getQuestionsForLevel(1);

        for (Question q : list) {
            if (q instanceof TextQuestion) {
                TextQuestion question = (TextQuestion) q;
                System.out.println("Question " + i + ":");
                System.out.println(question.text);
                question.display();
                System.out.print("Your answer: ");

                char inputChar = input.nextLine().charAt(0);
                int inputIndex = (int) (inputChar - 'A');
                if (question.isCorrect(inputIndex)) {
                    correctAnswers++;
                    System.out.println("That answer is correct");
                } else {
                    System.out.println("That answer is incorrect");
                }

                i++;
            }
        }

        System.out.println("Your score: " + correctAnswers);

    }

    public static void addQuestions(){
        Scanner input = new Scanner(System.in);

        System.out.println("What kind of question do you want? Insert: text or image.");
        String type1 = input.nextLine();
        System.out.println("0. What is the question you'd like to add?");
        String question1 = input.nextLine();
        System.out.println("1. Is the answer you're about to add an correct or wrong answer?");
        String typeanswer1 = input.nextLine();
        System.out.println("1. What is the first answer you'd like to add?");
        String answer1 = input.nextLine();
        System.out.println("2. Is the answer you're about to add an correct or wrong answer?");
        String typeanswer2 = input.nextLine();
        System.out.println("2. What is the second answer you'd like to add?");
        String answer2 = input.nextLine();
        System.out.println("3. Is the answer you're about to add an correct or wrong answer?");
        String typeanswer3 = input.nextLine();
        System.out.println("3. What is the third answer you'd like to add?");
        String answer3 = input.nextLine();

        String correctanswer = input.nextLine();

        if(typeanswer1.contains("correct")) {
            typeanswer1 = "correctanswer: ";
        }
        else{
            typeanswer1 = "answer";
        }

        if(typeanswer2.contains("correct")) {
            typeanswer2 = "correctanswer: " + answer2;
        }
        else{
            typeanswer2 = "answer: " + answer2;
        }

        if(typeanswer3.contains("correct")) {
            typeanswer3 = "correctanswer: " + answer3;
        }
        else{
            typeanswer2 = "answer: " + answer3;
        }

        String fullquestion = ("type: " + type1 + "\n" + "question: " + question1 + "\n   " + typeanswer1 + answer1 + "\n   " + typeanswer2);
        System.out.println(fullquestion);
    }


}
