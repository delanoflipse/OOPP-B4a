package GUI;

import database.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import user.UserDateScore;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
//import static GUI.StartMenu.playtts;
//import static GUI.StartMenu.ttsfinal;
//import static GUI.StartMenu.tts;
import static GUI.StartMenu.asText;

public class GUIQuestion extends UIScene implements Initializable {

    @FXML private ImageView logoImage1;
    @FXML private HBox menus;
    @FXML private Text questionText;
    @FXML private Text questionTitle;
    @FXML private Text responseText;
    @FXML private Button submitButton;

    private int currentScore;
    private int index;
    private ArrayList<ChoiceBox<Element>> choiceBoxList = new ArrayList<>();
    private ArrayList<Question> questions;
    private database.GUIQuestion question;

    private boolean done = false;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // set image
        Image image = new Image("file:src/images/logo.png");
        logoImage1.setImage(image);

        // set css
        UI.setCSS("questions.css");

        // get state values
        index = (int) UI.state.context.get("index");
        questions = (ArrayList<Question>) UI.state.context.get("questions");
        question = (database.GUIQuestion) questions.get(index);
        currentScore = (int) UI.state.context.get("score");

        // set text
        questionText.setText("Question " + (1 + index));
        questionTitle.setText(question.text);

        // create menus

        //variable to keep track at which dropdownbox we are
        int i = 0;
        //Add all dropdownmenus to the HBox
        for (Answer answer : question.answers) {
            menus.getChildren().add(makeMenu((DropDownHead) answer));
            i++;
        }


    }

    private ChoiceBox<Element> makeMenu(DropDownHead menu) {
        ChoiceBox<Element> choiceBox = new ChoiceBox<>();
        choiceBox.setValue(menu);
        choiceBox.getItems().add(menu);
        for (Element element : menu.getElements()) {
            choiceBox.getItems().add(element);
        }
        choiceBoxList.add(choiceBox);
        return choiceBox;
    }

    @FXML
    protected void handleSubmit() {
        if (done) {
            handleContinue();
            return;
        }

        //Set total correct answers
        int totalcorrect = 0;

        //Set required number of correct elements
        int required = 0;
        for (ChoiceBox<Element> choiceBox : choiceBoxList) {
            boolean correct = false;
            for (Element element : choiceBox.getItems()) {
                if (element.correct) {
                    correct = true;
                }
            }
            if (correct) {
                required++;
            }
        }

        //Check whether the correct answer is selected in one of the dropdownboxes
        for (ChoiceBox<Element> choiceBox : choiceBoxList) {
            if (choiceBox.getValue().correct) {
                totalcorrect++;
            }
        }

        responseText.setVisible(true);
        submitButton.setText("Continue");
        done = true;

        //If the answer is correct
        if (totalcorrect >= required) {
            responseText.setFill(Color.DARKGREEN);
            responseText.setText("That is correct! Click continue to go to the next question.");
            UI.state.context.set("score", currentScore + 10);
        }
        else {
            responseText.setFill(Color.FIREBRICK);
            responseText.setText("That is incorrect. Click continue to go to the next question");
        }

    }

    void handleContinue() {
        if (questions.size() - index <= 2) {
            saveScore();
            UI.goToScene("result");
        } else {
            UI.state.context.set("index", ++index);
            UI.goToScene("guiquestions");
        }
    }

    @FXML
    protected void handleExit(ActionEvent event) {
        saveScore();
        UI.goToScene("startmenu");
    }

    private void saveScore() {
        UserDateScore score = new UserDateScore();
        score.date = (String) UI.state.context.get("date");
        score.score = (int) UI.state.context.get("score");
        score.user = UI.state.user;

        UI.state.user.scores.add(score);
        UI.state.user.save();
    }




}




/**public class GUIQuestion {

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
        ttsfinal="welcome to the G U I questions . . ";
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

        next = new Button("Continue");
        next.setOnMouseEntered(e-> tts.speak("continue",false,playtts));
        next.setOnAction(e -> {
            index++;
            choiceBoxList.clear();
            showQuestion(index);
        });

        stop = new Button("Stop Quiz");
        stop.setOnMouseEntered(e-> tts.speak("Stop quiz",false,playtts));
        stop.setOnAction(e -> done());

        submit = new Button("Submit");
        submit.setOnMouseEntered(e->tts.speak("submit",false,playtts));
        submit.setOnAction(e -> checkAnswer());


        showQuestion(index);
    }

    public static void showQuestion(int index) {
        //empty the centergrid
        centergrid.getChildren().clear();

        //Set response text to nothing
        response.setText("");

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
        ttsfinal = ttsfinal + "please answer the questions by selecting the right menu items in one of the menus";
        centergrid.add(intro, 1, 0, i+1, 1);
        //Show the question
        Text questiontext = new Text(q.text);
        ttsfinal = ttsfinal + q.text;
        tts.speak(ttsfinal,false,playtts);
        ttsfinal = "";
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
        //Set total correct answers
        int totalcorrect = 0;

        //Set required number of correct elements
        int required = 0;
        for (ChoiceBox<Element> choiceBox : choiceBoxList) {
            boolean correct = false;
            for (Element element : choiceBox.getItems()) {
                if (element.correct) {
                    correct = true;
                }
            }
            if (correct) {
                required++;
            }
        }

        //Check whether the correct answer is selected in one of the dropdownboxes
        for (ChoiceBox<Element> choiceBox : choiceBoxList) {
            if (choiceBox.getValue().correct) {
                totalcorrect++;
            }
        }
        //If the answer is correct
        if (totalcorrect >= required) {
            score++;
            response.setFill(Color.DARKGREEN);
            response.setText("That is correct! Click continue to go to the next question.");
            tts.speak("That is correct. .  click continue to go to the next question",false,playtts);
        }
        else {
            response.setFill(Color.FIREBRICK);
            response.setText("That is incorrect. Click continue to go to the next question");
            tts.speak("that is incorrect . . click continue to go to the next question",false,playtts);
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
        tts.speak("That were all the question, well done! . " + "Your score is: . .  " + asText(score,false,"neg") + ". . out of . " + asText(total,false,"neg") + " . . You will be redirected to the startscreen, when you click exit.",false,playtts );
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
        exit.setOnMouseEntered(e->tts.speak("exit",false,playtts));
        exit.setOnAction(e -> StartMenu.display());

        //HBox to get the exit button in the center beneath the text
        HBox exitbox = new HBox();
        exitbox.setAlignment(Pos.CENTER);
        exitbox.getChildren().add(exit);

        //Add the HBox with the button in it
        centergrid.add(exitbox ,0, 3);
    }
}
**/