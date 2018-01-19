package GUI;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class AddMCQuestion {

    private static int answerindex = 1;
    private static GridPane centergrid;
    private static ArrayList<RadioButton> correctanswerlist = new ArrayList<>();
    private static ArrayList<TextField> answerfields = new ArrayList<>();
    private static Button addanswerfieldbutton;
    private static Button savebutton;
    private static Button cancelbutton;
    private static ArrayList<RadioButton> levelbuttons = new ArrayList<>();
    private static TextField questionfield;
    private static ToggleGroup correctgroup = new ToggleGroup();

    public static void AddQuestion(GridPane grid) {
        //Initialize pane
        centergrid = grid;
        centergrid.getChildren().clear();

        //Set centergrid alignment
        centergrid.setAlignment(Pos.TOP_LEFT);

        //Add intro text
        Text intro = new Text("Please fill in the following fields:\nYou can click \"New Answer\" to add a new answer.\nClick save to store the question in the database.");
        centergrid.add(intro, 1, 0, 4, 3);

        //Add Question label and textfield
        Label questionlabel = new Label("Question:");
        questionfield = new TextField();
        centergrid.add(questionlabel, 0, 3);
        centergrid.add(questionfield, 1, 3);

        //Add buttons for the level of the question
        Label levellabel = new Label("Level:");
        RadioButton level1button = new RadioButton("1");
        RadioButton level2button = new RadioButton("2");
        RadioButton level3button = new RadioButton("3");
        ToggleGroup levelgroup = new ToggleGroup();
        level1button.setToggleGroup(levelgroup);
        level2button.setToggleGroup(levelgroup);
        level3button.setToggleGroup(levelgroup);
        levelbuttons.add(level1button);
        levelbuttons.add(level2button);
        levelbuttons.add(level3button);
        centergrid.add(levellabel, 0, 4);
        centergrid.add(level1button, 1, 4);
        centergrid.add(level2button, 1, 5);
        centergrid.add(level3button, 1, 6);

        //Add one answerfield and the introtext for how to say which answer is correct.
        Text answercorrectText = new Text("Here you can add the answers. Select the button with Correct? at the answer which is correct.");
        centergrid.add(answercorrectText, 1, 7);

        //Making actions and text of the buttons at the bottom of the screen
        addanswerfieldbutton = new Button("New Answer");
        addanswerfieldbutton.setOnAction(e -> addAnswerField());

        savebutton = new Button("Save Question");
        savebutton.setOnAction(e -> {
            saveQuestion();
        });

        cancelbutton = new Button("Cancel");
        cancelbutton.setOnAction(e -> Admin.display());

        //Now that the buttons exist we can show the answerfield
        //This also shows the buttons
        addAnswerField();
    }

    //Makes a new answerfield
    private static void addAnswerField(){
        //Make new label, textfield and radio button
        Label answerlabel = new Label("Answer "+answerindex+":");
        TextField answerfield = new TextField();
        RadioButton correct = new RadioButton("Correct Asnwer");
        //Add new label, field and button to the screen
        centergrid.add(answerlabel, 0, 7+answerindex);
        centergrid.add(answerfield, 1, 7+answerindex);
        centergrid.add(correct, 2, 7+answerindex);
        //Add radiobutton to the List and the ToggleGroup
        correctanswerlist.add(correct);
        correct.setToggleGroup(correctgroup);
        //Add the field to the List
        answerfields.add(answerfield);
        answerindex++;
        showButtons();
    }

    //Shows the button at the bottom again so that they don't go through answerfields
    private static void showButtons() {
        // remove the buttons
        centergrid.getChildren().removeAll(addanswerfieldbutton, savebutton, cancelbutton);

        //Add the buttons again at the right place
        centergrid.add(addanswerfieldbutton, 1, 7+answerindex);
        centergrid.add(savebutton, 0, 9+answerindex);
        centergrid.add(cancelbutton, 1, 9+answerindex);
    }

    //Makes the new question and saves it in the database
    private static void saveQuestion(){
        try {
            //Read the data:

            //Read question
            String question = questionfield.getText();

            //Read level
            int level = 0;
            int i=1;
            for (RadioButton button : levelbuttons) {
                if (button.isSelected()) {
                    level = i;
                }
                i++;
            }

            //Read answers
            ArrayList<String> answers = new ArrayList<>();
            for (TextField answerfield : answerfields) {
                answers.add(answerfield.getText());
            }

            //Read correct answer
            int correct = 0;
            i = 0;
            for (RadioButton button : correctanswerlist) {
                if (button.isSelected()) {
                    correct = i;
                }
                i++;
            }

            //We add new data at the end of the file
            PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter("db.data", true)));
            writer.print("\n");
            //Write all the data
            writer.println("#Question Added by the MC GUI");
            writer.println("type: multiple choice");
            writer.println("level: "+level);
            writer.println("question: "+question);
            i=0;
            for (String answer: answers) {
                if (i==correct) {
                    writer.print("correct");
                }
                writer.println("answer: " + (char)(65+i)+". "+answer);
                i++;
            }
            //close the writer
            writer.close();
            //go back to the start menu
            quit();
        } catch (IOException e) {
            //Print the exception message
            System.out.println(e.getMessage());
        }
    }

    //Go back to the start screen
    private static void quit(){
        //prepare new screen
        centergrid.getChildren().clear();
        centergrid.setAlignment(Pos.TOP_LEFT);

        //Show the MC question just made (to be implemented)

        Admin.display();
    }

}
