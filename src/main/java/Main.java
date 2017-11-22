import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int correctAnswers = 0;
        Question[] questions = {
                new Question("Wat is het antwoord?", new Answer[] {
                        new Answer("Ja."),
                        new Answer("Ja."),
                        new Answer("Nee.", true),
                        new Answer("Ja."),
                }),

                new Question("Wat is het tweede antwoord?", new Answer[] {
                        new Answer("Ja."),
                        new Answer("Ja."),
                        new Answer("Nee.", true),
                        new Answer("Ja."),
                })
        };

        for (int i = 0; i < questions.length; i++) {
            Question question = questions[i];
            System.out.println("Vraag " + (i + 1) + ": " + question.text);

            for (int j = 0; j < question.answers.length; j++) {
                Answer answer = question.answers[j];
                char x = (char) ('A' + j);
                System.out.println("\t " + x + ". " + answer.text);
            }

            boolean valid = false;

            while (!valid) {
                char c = input.nextLine().charAt(0);
                int index = (int) c - (int)'A';

                if (index < 0 || index > question.answers.length - 1 ) {
                    System.out.println("Please enter a valid answer");
                } else {
                    valid = true;

                    if (question.answers[index].correct) {
                        correctAnswers++;
                        System.out.println("That answer is correct");
                    } else {
                        System.out.println("That answer is incorrect");
                    }
                }
            }
        }

        System.out.println("Your score: " + correctAnswers + "/" + questions.length);
    }
}
