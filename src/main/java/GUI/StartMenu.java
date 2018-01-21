package GUI;

import database.Database;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javafx.event.ActionEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

import tts.ttshelper;


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
        setTTSbutton();

        userTitle.setText("Welcome " + UI.state.user.name);
        ttshelper.ttsfinal = "welcome " + UI.state.user.name;
        ttshelper.tts.speak(ttshelper.ttsfinal,false,ttshelper.playtts);
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
        ttshelper.tts.speak("multiple choice questions",false,ttshelper.playtts);
    }

    @FXML
    protected void TTSTTSButton() {

            ttshelper.tts.speak("disable spoken text", false, ttshelper.playtts);
    }

    @FXML
    protected void GUITTSButton(){
        ttshelper.tts.speak("g u i questions",false,ttshelper.playtts);
    }

    @FXML
    protected void IMGTTSButton() {
        ttshelper.tts.speak("image questions",false,ttshelper.playtts);
    }

    @FXML
    protected void BACKTTSButton() {
        ttshelper.tts.speak("GO BACK",false,ttshelper.playtts);
    }

    @FXML
    protected void SCORESTTSButton() {
        ttshelper.tts.speak("SCORES",false,ttshelper.playtts);
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
    protected void handleTTSButton(){
        ttshelper.toggletts();
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
