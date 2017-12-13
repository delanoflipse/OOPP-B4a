package main;

import GUI.StartMenu;
import main.Database;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        new Thread() {
            @Override
            public void run() {
                javafx.application.Application.launch(StartMenu.class);
            }
        }.start();
//        askMultipleChoichequestions();
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
}
