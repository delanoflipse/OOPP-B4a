package GUI;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javafx.event.ActionEvent;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

import tts.TTSHelper;


public class StartMenu extends UIScene implements Initializable {

    @FXML private Text userTitle;
    @FXML private ImageView logoImage1;
    @FXML private ImageView logoImage2;

    @FXML private Button scoreBtn, returnBtn, ttsBtn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // set images
        Image image = new Image("file:src/images/logo.png");
        logoImage1.setImage(image);
        logoImage2.setImage(image);

        //Set the title of the stage
        UI.setTitle("Stichting Lezen en Schrijven - Practice Program");
        UI.setCSS("startmenu.css");

        setButtonImage(scoreBtn, "file:src/images/scores.png");
        setButtonImage(returnBtn, "file:src/images/arrowleft.png");

        userTitle.setText("Welcome " + UI.state.user.name);
        TTSHelper.ttsfinal = "welcome " + UI.state.user.name;
        TTSHelper.tts.speak(TTSHelper.ttsfinal);
        handleTTSbutton(ttsBtn);
    }

    @FXML
    protected void handleMCButton(ActionEvent event) {
        // setup context
        UI.state.setContext(
                new UIContext()
                    .set("type", "TextQuestion")
        );

        // go to scene
        UI.goToScene("selection");
    }

    //tts for buttons
    @FXML
    protected void MCTTSButton() {
        TTSHelper.tts.speak("multiple choice questions");
    }

    @FXML
    protected void TTSTTSButton() {
        TTSHelper.tts.speak("disable spoken text");
    }

    @FXML
    protected void GUITTSButton(){
        TTSHelper.tts.speak("g u i questions");
    }

    @FXML
    protected void IMGTTSButton() {
        TTSHelper.tts.speak("image questions");
    }

    @FXML
    protected void BACKTTSButton() {
        TTSHelper.tts.speak("GO BACK");
    }

    @FXML
    protected void SCORESTTSButton() {
        TTSHelper.tts.speak("SCORES");
    }


    @FXML
    protected void handleIQButton(ActionEvent event) {
        // setup context
        UI.state.setContext(
                new UIContext()
                        .set("type", "ClickQuestion")
        );

        // go to scene
        UI.goToScene("selection");
    }

    @FXML
    protected void handleGUIButton(ActionEvent event) {
        // setup context
        UI.state.setContext(
                new UIContext()
                        .set("type", "GUIQuestion")
        );

        // go to scene
        UI.goToScene("selection");
    }

    @FXML
    protected void goToScores(ActionEvent event) {
        // go to scene
        UI.goToScene("scores");
    }

    @FXML
    protected void handleReturn(ActionEvent event) {
        // go to scene
        UI.goToScene("welcome");
    }

}
