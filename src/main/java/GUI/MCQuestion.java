package GUI;

import database.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import user.UserDateScore;

import java.lang.reflect.Array;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class MCQuestion extends UIScene implements Initializable {

    @FXML private ImageView logoImage1;
    @FXML private VBox buttonstack;
    @FXML private Text questionText;
    @FXML private Text questionTitle;
    @FXML private Text responseText;
    @FXML private Button continueButton;
    @FXML private Button submitButton;

    private ToggleGroup answergroup = new ToggleGroup();
    private TextQuestion question;
    private ArrayList<Question> questions;
    private int index, currentScore;

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
        question = (TextQuestion) questions.get(index);
        currentScore = (int) UI.state.context.get("score");

        // set text
        questionText.setText("Question " + (1 + index));
        questionTitle.setText(question.text);

        // create buttons
        for (TextAnswer answer : question.answers) {
            RadioButton btn = new RadioButton(answer.text);
            btn.setToggleGroup(answergroup);
            btn.setUserData(answer.text);
            buttonstack.getChildren().add(btn);
        }
    }

    @FXML
    protected void handleSubmit(ActionEvent event) {
        if (done) {
            handleContinue();
            return;
        }

        Toggle ans = answergroup.getSelectedToggle();
        if (ans == null) {
            responseText.setText("Please select a value!");
            responseText.setVisible(true);
            return;
        }

        String val = (String) ans.getUserData();
        boolean correct = question.isCorrect(val);

        responseText.setVisible(true);
        submitButton.setText("Continue");
        done = true;

        if (correct) {
            responseText.setFill(Color.DARKGREEN);
            responseText.setText("That is correct. Click continue to go to the next question");
            UI.state.context.set("score", currentScore + 10);
        } else {
            responseText.setFill(Color.FIREBRICK);
            responseText.setText("That is incorrect. The answer was:\n" + question.getCorrectAnswer().text + "\nClick continue to go to the next question");
        }
    }

    void handleContinue() {
        if (questions.size() - index <= 2) {
            saveScore();
            UI.goToScene("startmenu");
        } else {
            UI.state.context.set("index", ++index);
            UI.goToScene("mcquestions");
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
