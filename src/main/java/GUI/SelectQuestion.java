package GUI;

import database.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import tts.ttshelper;
import user.UserDateScore;

import java.net.URL;
import java.util.ArrayList;

import java.util.ResourceBundle;

public class SelectQuestion extends UIScene implements Initializable {
    private ClickQuestion question;
    private ArrayList<Question> questions;
    private int index;
    private Image qimage;
    private double imgratio;
    private int currentScore;
    private int currentTotal;

    @FXML private ImageView logoImage1;
    @FXML private ImageView qimagev;
    @FXML private Text questionText;
    @FXML private Text questionTitle;
    @FXML private Button submitButton;
    @FXML private Pane imagepane;
    @FXML private Text responseText;
    @FXML private Circle selected;
    @FXML private Rectangle correct;
    @FXML private VBox imageBox;
    @FXML private Button ttsBtn;

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
        question = (ClickQuestion) questions.get(index);
        currentScore = (int) UI.state.context.get("score");
        currentTotal = (int) UI.state.context.get("total");

        setTTSbutton();

        // set text
        questionText.setText("Question " + (1 + index));
        questionTitle.setText(question.text);

        qimage = new Image("file:src/images/" + question.image);
        qimagev.setImage(qimage);
        qimagev.fitHeightProperty().bind(imageBox.heightProperty());
        ttshelper.ttsfinal = "question" + ttshelper.tripleAsText((index + 1),false) + "," + question.text;
        ttshelper.tts.speak(ttshelper.ttsfinal,false,ttshelper.playtts);
    }

    @FXML
    private void setBeginCoordinates(double X, double Y) {
        if (done) return;

        //Read and set the coordinates at the beginning
        selected.setCenterX(X);
        selected.setCenterY(Y);
        selected.setRadius(4);
    }

    @FXML
    private void setCoordinates(MouseEvent e) {
        if (done) return;
        //image is changed by this ratio
        imgratio = qimage.getHeight() / qimagev.getFitHeight();
        double X = e.getX();
        double Y = e.getY();
        //If the mouse is in the image
        if (X >= 0.0 && X <= qimagev.getFitHeight()/qimage.getHeight()*qimage.getWidth()) {
            //Set the X coordinate of the circle
            selected.setCenterX(X);
        }
        //Otherwise set is to the boundary
        else {
            if (X <= 0) {
                selected.setCenterX(0);
            }
            if (X > qimagev.getFitHeight()/qimage.getHeight()*qimage.getWidth()) {
                selected.setCenterX(qimagev.getFitHeight()/qimage.getHeight()*qimage.getWidth());
            }
        }
        //Same things for the Y value
        if (Y >= 0 && Y <= qimagev.getFitHeight()) {
            selected.setCenterY(Y);
        } else {
            if (Y <= 0) {
                selected.setCenterY(0);
                Y = 0;
            }
            if (Y > qimagev.getFitHeight()) {
                selected.setCenterY(qimagev.getFitHeight());
            }
        }
    }

    @FXML
    private void beginDrag(MouseEvent e) {
        //At the start of a drag set rectangle size to 0 and set coordinates
        //Also set the color back
        if (done) return;
        setBeginCoordinates(e.getX(), e.getY());
        selected.setFill(Color.YELLOW);
    }

    @FXML
    private void endDrag() {
        //Enable the submit button and make the color a bit darker
        if (done) return;
        submitButton.setDisable(false);
    }

    @FXML
    protected void handleSubmit(ActionEvent event) {
        if (done) {
            handleContinue();
            return;
        }

        UI.state.context.set("total", ++currentTotal);

        //image is changed by this ratio
        imgratio = qimage.getHeight() / qimagev.getFitHeight();
        boolean isCorrect = question.isCorrect(selected.getCenterX() * imgratio, selected.getCenterY() * imgratio);

        responseText.setVisible(true);
        submitButton.setText("continue");
        done = true;

        if (isCorrect) {
            responseText.setFill(Color.DARKGREEN);
            responseText.setText("That is correct. Click continue to go to the next question.");
            ttshelper.ttsfinal = "That is correct. Click continue to go to the next question.";
            ttshelper.tts.speak(ttshelper.ttsfinal,false,ttshelper.playtts);
            selected.setFill(Color.DARKGREEN);
            UI.state.context.set("score", currentScore + 10);
        } else {
            responseText.setFill(Color.FIREBRICK);
            responseText.setText("That is incorrect, you can see the answer on the image now.");
            ttshelper.ttsfinal = "That is incorrect, you can see the answer on the image now.";
            ttshelper.tts.speak(ttshelper.ttsfinal,false,ttshelper.playtts);
            correct.setX(question.topLeft.x / imgratio);
            correct.setY(question.topLeft.y / imgratio);
            correct.setWidth((question.bottomRight.x - question.topLeft.x) / imgratio);
            correct.setHeight((question.bottomRight.y - question.topLeft.y) / imgratio);
            correct.setFill(Color.FIREBRICK);
        }
    }

    private void handleContinue() {
        if (questions.size() - index <= 2) {
            saveScore();

            UI.goToScene("result");
        } else {
            UI.state.context.set("index", ++index);
            UI.goToScene("imagequestions");
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

    //button tts
    @FXML
    protected void SUBMITTTSButton () {
        if (responseText.isVisible()){
            ttshelper.tts.speak("Continue", false, ttshelper.playtts);
        } else{
            ttshelper.tts.speak("submit", false, ttshelper.playtts);
        }
    }

    @FXML
    protected void EXITTTSButton(){
        ttshelper.tts.speak("exit",false,ttshelper.playtts);
    }

    @FXML
    protected void handleTTSButton(){
        ttshelper.toggletts();
    }

    @FXML
    protected void TTSTTSButton() {
        ttshelper.tts.speak("disable spoken text", false, ttshelper.playtts);
    }

    @FXML
    protected void toggleTTS(MouseEvent event) {
        boolean val = UI.state.user.getBoolPreference("useTTS");
        UI.state.user.setPreference("useTTS", val ? "false" : "true");
        UI.state.user.save();
        setTTSbutton();
    }

    private void setTTSbutton() {
        if (UI.state.user.getBoolPreference("useTTS")) {
            ttsBtn.setText("Disable spoken text");
            setButtonImage(ttsBtn, "file:src/images/speakeron.png");
        } else {
            ttsBtn.setText("Use spoken text");
            setButtonImage(ttsBtn, "file:src/images/speakeroff.png");
        }
    }
}
