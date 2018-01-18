package GUI;

import database.*;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.util.ArrayList;


public class GUIQuestion {

    private static GridPane centergrid;
    private static ArrayList<database.GUIQuestion> questionlist = new ArrayList<>();
    private static int score;
    private static int total;
    private static int index;
    private static Button next;
    private static Button stop;
    private static Text response = new Text("");
    private static database.GUIQuestion q;
    private static ArrayList<ChoiceBox<Element>> choiceBoxList = new ArrayList<>();
    private static Button submit;


    public static void askQuestions(GridPane grid) {
        index = 0;
        score = 0;
        total = 0;
        centergrid = grid;
        centergrid.setAlignment(Pos.CENTER_LEFT);
        //Get the questions from the database
        Database.loadDatabase();
        ArrayList<Question> allquestions = Database.getQuestionsForLevel(1);

        //Get all GUI questions
        for (Question q: allquestions) {
            if (q instanceof database.GUIQuestion) {
                questionlist.add((database.GUIQuestion) q);
            }
        }

        /**DropDownHead menu = new DropDownHead("Companies");
        menu.getElements().add(new Element("Company one"));
        menu.getElements().add(new Element("Company two"));
        menu.getElements().add(new Element("Company three"));

        DropDownHead menu2 = new DropDownHead("Schools");
        menu2.getElements().add(new Element("School one"));
        menu2.getElements().add(new Element("School two"));
        menu2.getElements().add(new Element("School three"));

        DropDownHead menu3 = new DropDownHead("Places");
        menu3.getElements().add(new Element("Place one"));
        menu3.getElements().add(new Element("Place two"));
        menu3.getElements().add(new Element("Place three"));
        menu3.getElements().get(0).correct = true;

        database.GUIQuestion guiQuestion = new database.GUIQuestion();
        guiQuestion.level = 1;
        guiQuestion.text = "Where can we find Place one?";
        guiQuestion.answers.add(menu);
        guiQuestion.answers.add(menu2);
        guiQuestion.answers.add(menu3);
        questionlist.add(guiQuestion);**/

        next = new Button("Continue");
        next.setOnAction(e -> {
            index++;
            choiceBoxList.clear();
            showQuestion(index);
        });

        stop = new Button("Stop Quiz");
        stop.setOnAction(e -> done());

        submit = new Button("Submit");
        submit.setOnAction(e -> checkAnswer());


        showQuestion(index);
    }

    public static void showQuestion(int index) {
        //empty the centergrid
        centergrid.getChildren().clear();

        //Set response text to nothing
        response.setText("");
        response.setId("responsetext");

        if (index >= questionlist.size()) {
            done();
            return;
        }

        //Get the question
        q = questionlist.get(index);

        //variable to keep track at which dropdownbox we are
        int i = 0;
        //Add all dropdownmenus to the grid
        for (Answer answer : q.answers) {
            centergrid.add(makeMenu((DropDownHead) answer), i+2, 2);
            i++;
        }

        //ColSpan i so that menus don't appear 'behind' the text
        //Intro text
        Text intro = new Text("Please answer the question by selecting the right menu item (in one of the menus)");
        centergrid.add(intro, 1, 0, i+1, 1);
        //Show the question
        Text questiontext = new Text(q.text);
        centergrid.add(questiontext, 1, 1, i+1, 1);

        //Add response text
        centergrid.add(response, 1, 3, i+1, 1);

        //Add stop button
        centergrid.add(stop, 0, 4);

        //Add submit button
        centergrid.add(submit, 1, 4);


    }

    private static ChoiceBox<Element> makeMenu(DropDownHead menu) {
        ChoiceBox<Element> choiceBox = new ChoiceBox<>();
        choiceBox.setValue(menu);
        choiceBox.getItems().add(menu);
        for (Element element : menu.getElements()) {
            choiceBox.getItems().add(element);
        }
        choiceBoxList.add(choiceBox);
        return choiceBox;
    }

    private static void checkAnswer() {
        total++;
        boolean correct = false;
        //Check whether the correct answer is selected in one of the dropdownboxes
        for (ChoiceBox<Element> choiceBox : choiceBoxList) {
            if (choiceBox.getValue().correct) {
                correct = true;
            }
        }
        //If the answer is correct
        if (correct) {
            score++;
            response.setFill(Color.LAWNGREEN);
            response.setText("That is correct! Click continue to go to the next question.");
        }
        else {
            for (ChoiceBox<Element> choiceBox : choiceBoxList) {
                for (int i = 0; i < choiceBox.getItems().size(); i++){
                    if (choiceBox.getItems().get(i).correct) {
                        String answer = choiceBox.getItems().get(i).toString();
                        String domain = choiceBox.getItems().get(0).toString();
                        response.setFill(Color.FIREBRICK);
                        response.setText("That is incorrect.\nYou should have selected " + answer + " inside the " + domain + " menu\nClick continue to go to the next question");
                    }
                }
            }
        }

        centergrid.getChildren().remove(submit);
        centergrid.add(next, 1, 4);

    }

    private static void done() {
        questionlist.clear();

        //To make sure next time it starts with the first question
        index=0;

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
