package GUI;

import database.*;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.util.ArrayList;

public class MCQuestion {

    private static int i;
    private static int score;
    private static int total;
    private static Text response;
    private static ArrayList<RadioButton> answerbuttons = new ArrayList<>();
    private static ToggleGroup answergroup = new ToggleGroup();
    private static GridPane centergrid;
    private static ArrayList<TextQuestion> questionlist = new ArrayList<>();
    private static Button submit;
    private static Button stop;

    public static void askQuestions(GridPane grid){
        centergrid = grid;
        //Get the questions from the database
        Database.loadDatabase();
        ArrayList<Question> allquestions = Database.getQuestionsForLevel(1);

        //Get all mc questions
        for (Question q: allquestions) {
            if (q instanceof TextQuestion) {
                questionlist.add((TextQuestion) q);
            }
        }

        //Set score to 0 for the beginning
        score = 0;

        //Set new alignment for the grid
        centergrid.setAlignment(Pos.CENTER_LEFT);

        //Make the response text
        response = new Text();
        response.setId("responsetext");

        //Make the button for going to the next question
        Button next = new Button("Continue");

        //Make button for stopping the quiz
        stop = new Button("Stop Quiz");
        stop.setOnAction(e -> done());

        //Make button to submit the question and set the action
        submit = new Button("Submit");
        submit.setOnAction(e -> {
            total++;
            //If there is an answer selected
            if (answerbuttons.indexOf(answergroup.getSelectedToggle()) != -1) {
                //Get the index fo the selected answer
                int selected = answerbuttons.indexOf(answergroup.getSelectedToggle());
                //If the selected answer is correct
                if (((TextQuestion) questionlist.get(i)).isCorrect(selected)) {
                    //Increase score
                    addScore();
                    //Set response text
                    response.setFill(Color.LAWNGREEN);
                    response.setText("That is correct. Click continue to go to the next question");
                } else {
                    for (Answer answer : ((TextQuestion) questionlist.get(i)).answers) {
                        //Get correct answer
                        TextAnswer tanswer = (TextAnswer) answer;
                        if (tanswer.correct) {
                            //Set response text with the correct answer in it
                            final String answertext = tanswer.text;
                            response.setFill(Color.FIREBRICK);
                            response.setText("That is incorrect. The answer was:\n" + answertext + "\nClick continue to go to the next question");
                        }
                    }
                }
                //Add the continue button to the grid
                final int col = centergrid.getColumnIndex(submit);
                final int row = centergrid.getRowIndex(submit);
                centergrid.add(next, col, row+1);
                centergrid.getChildren().remove(stop);
                centergrid.add(stop, col+1, row+1);
                centergrid.getChildren().remove(submit);
            }
        });

        //Set action for continue button (Showing the next question)
        next.setOnAction(e -> showNextQuestion());

        //Show the first question
        i=-1;
        showNextQuestion();
    }

    private static void addScore(){
        score++;
    }

    private static void showNextQuestion(){
        //Clear the centergrid
        centergrid.getChildren().clear();
        //Increase i to get the next question
        i++;
        //Check whether there is a next question
        if (i >= questionlist.size()){
            //If not: give the end screen
            done();
            return;
        }

        //Display which question we'recurrently at
        TextQuestion q = (TextQuestion) questionlist.get(i);
        Text qtitle = new Text("Question " + (i+1));
        qtitle.setId("qtitle");
        centergrid.add(qtitle, 0, 0);

        //Display the question itself
        Text qtext = new Text(q.text);
        qtext.setId("qtext");
        centergrid.add(qtext, 0, 1, 2, 1);
        //Clear the answerbuttons list
        answerbuttons.clear();
        //Add the new buttons with the right texts behind it
        for (Answer answer : q.answers) {
            TextAnswer tanswer = (TextAnswer) answer;
            answerbuttons.add(new RadioButton(tanswer.text));
        }
        int j = 1;
        //Add every button to the togglegroup, so that only one can eb selected at a time
        //And add the buttons to the centergrid
        for (RadioButton button : answerbuttons) {
            button.setToggleGroup(answergroup);
            j++;
            centergrid.add(button, 0, j, 2, 1);
        }
        //Make the response text 'disappear'
        response.setText("");
        //Add the response text and submit button on the right place
        centergrid.add(response, 0, j + 1, 2, 1);
        centergrid.add(submit, 0, j+1);
        centergrid.add(stop, 0, j+2);
    }

    private static void done(){
        questionlist.clear();
        //Make the texts for the ending
        Text end = new Text("That were all the question, well done!");
        Text endscore = new Text("Your score is: " + score + " out of " + total);
        Text back = new Text("You will be redirected to the startscreen, when you click exit.");

        //Set IDs for CSS
        end.setId("end");
        endscore.setId("end");
        back.setId("end");

        //Clear centergrid and add the texts
        centergrid.getChildren().clear();
        centergrid.add(end, 0, 0);
        centergrid.add(endscore, 0, 1);
        centergrid.add(back, 0, 2);

        //Make the exit button and set the action
        Button exit = new Button("Exit");
        exit.setOnAction(e -> StartMenu.display());

        //HBox to get the exit button in the center beneath the text
        HBox exitbox = new HBox();
        exitbox.setAlignment(Pos.CENTER);
        exitbox.getChildren().add(exit);

        //Add the HBox with the button in it
        centergrid.add(exitbox ,0, 3);

    }

}
