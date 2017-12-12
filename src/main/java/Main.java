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
        System.out.println("1. Is the answer you're about to add a correct or wrong answer?");
        String typeanswer1 = input.nextLine();
        System.out.println("1. What is the first answer you'd like to add?");
        String answer1 = input.nextLine();

        if(typeanswer1.contains("correct")) {
            typeanswer1 = "correctanswer: " + answer1;
        }
        else{
            typeanswer1 = "answer: " + answer1;
        }

        System.out.println("2. Is the answer you're about to add a correct or wrong answer?");
        String typeanswer2 = input.nextLine();
        System.out.println("2. What is the second answer you'd like to add?");
        String answer2 = input.nextLine();

        if(typeanswer2.contains("correct")) {
            typeanswer2 = "correctanswer: " + answer2;
        }
        else{
            typeanswer2 = "answer: " + answer2;
        }

        System.out.println("3. Is the answer you're about to add a correct or wrong answer?");
        String typeanswer3 = input.nextLine();
        System.out.println("3. What is the third answer you'd like to add?");
        String answer3 = input.nextLine();

        if(typeanswer3.contains("correct")) {
            typeanswer3 = "correctanswer: " + answer3;
        }
        else{
            typeanswer3 = "answer: " + answer3;
        }

        System.out.println("4. Is the answer you're about to add a correct or wrong answer?");
        String typeanswer4 = input.nextLine();
        System.out.println("4. What is the fourth answer you'd like to add?");
        String answer4 = input.nextLine();

        if(typeanswer4.contains("correct")) {
            typeanswer4 = "correctanswer: " + answer4;
        }
        else{
            typeanswer4 = "answer: " + answer4;
        }

        System.out.println("Would you like to add another answer? Answer with: Yes or no");
        String adding1 = input.nextLine();

        if(adding1.contains("no")){
            String fullquestion = ("type: " + type1 + "\n" + "question: " + question1 + "\n   " + typeanswer1 + "\n   " + typeanswer2
                    + "\n   " + typeanswer3  + "\n   " + typeanswer4);
            System.out.println(fullquestion);

        }

        if(adding1.contains("no")){
            System.exit(0);
        }

        System.out.println("5. Is the answer you're about to add a correct or wrong answer?");
        String typeanswer5 = input.nextLine();
        System.out.println("5. What is the fifth answer you'd like to add?");
        String answer5 = input.nextLine();

        if(typeanswer5.contains("correct")) {
            typeanswer5 = "correctanswer: " + answer5;
        }
        else{
            typeanswer5 = "answer: " + answer5;
        }

        System.out.println("Would you like to add another answer? Answer with: Yes or no");
        String adding2 = input.nextLine();

        if(adding2.contains("no")){
            String fullquestion = ("type: " + type1 + "\n" + "question: " + question1 + "\n   " + typeanswer1 + "\n   " + typeanswer2
                    + "\n   " + typeanswer3  + "\n   " + typeanswer4  + "\n   " + typeanswer5);
            System.out.println(fullquestion);

        }

        if(adding2.contains("no")){
            System.exit(0);
        }

        System.out.println("6. Is the answer you're about to add a correct or wrong answer?");
        String typeanswer6 = input.nextLine();
        System.out.println("6. What is the sixth answer you'd like to add?");
        String answer6 = input.nextLine();

        if(typeanswer6.contains("correct")) {
            typeanswer6 = "correctanswer: " + answer6;
        }
        else{
            typeanswer6 = "answer: " + answer6;
        }

        String fullquestion = ("type: " + type1 + "\n" + "question: " + question1 + "\n   " + typeanswer1 + "\n   " + typeanswer2
                + "\n   " + typeanswer3  + "\n   " + typeanswer4  + "\n   " + typeanswer5 + "\n   " + typeanswer6);
        System.out.println(fullquestion);
    }
}
