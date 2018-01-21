package GUI;

import database.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import user.UserDateScore;
import tts.TTSHelper;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;


public class GUIQuestion extends UIScene implements Initializable {

    @FXML private ImageView logoImage1;
    @FXML private HBox menus;
    @FXML private Text questionText;
    @FXML private Text questionTitle;
    @FXML private Text responseText;
    @FXML private Button submitButton;
    @FXML private Button ttsBtn;

    private int currentScore;
    private int index;
    private int currentTotal;
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
        currentTotal = (int) UI.state.context.get("total");

        handleTTSbutton(ttsBtn);

        // set text
        questionText.setText("Question " + (1 + index));
        questionTitle.setText(question.text);
        TTSHelper.ttsfinal = "question" + TTSHelper.tripleAsText((1 + index),false) + question.text;
        TTSHelper.tts.speak(TTSHelper.ttsfinal);
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

        UI.state.context.set("total", ++currentTotal);

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
            TTSHelper.ttsfinal = " That is correct! Click continue to go to the next question." ;
            TTSHelper.tts.speak(TTSHelper.ttsfinal);
            UI.state.context.set("score", currentScore + 10);
        }
        else {
            responseText.setFill(Color.FIREBRICK);
            responseText.setText("That is incorrect. Click continue to go to the next question");
            TTSHelper.ttsfinal = " That is incorrect, Click continue to go to the next question." ;
            TTSHelper.tts.speak(TTSHelper.ttsfinal);
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

    //tts for buttons
    @FXML
    protected void SUBMITTTSButton () {
        if (responseText.isVisible()){
            TTSHelper.tts.speak("Continue");
        } else{
            TTSHelper.tts.speak("submit");
        }
    }


    @FXML
    protected void EXITTTSButton(){
        TTSHelper.tts.speak("exit");
    }


    @FXML
    protected void TTSTTSButton() {
        TTSHelper.tts.speak("disable spoken text");
    }


}



