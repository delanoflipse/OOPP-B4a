package GUI;

import database.Database;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import user.UserData;

import java.net.URL;
import java.util.ResourceBundle;

import tts.TTSHelper;

public class WelcomeScene extends UIScene implements Initializable {
    @FXML private Text errorText;
    @FXML private TextField input;
    @FXML private ImageView logoImage;
    @FXML private Button submitBtn, tutorBtn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        UI.setTitle("Stichting Lezen en Schrijven - Practice Program");

        // set image
        Image image = new Image("file:src/images/logo.png");
        logoImage.setImage(image);

        setButtonImage(submitBtn, "file:src/images/arrow.png");
        setButtonImage(tutorBtn, "file:src/images/tutor.png");
        TTSHelper.ttsfinal = "welcome to our practice program, please fill in your name";
        TTSHelper.tts.speak(TTSHelper.ttsfinal,false, TTSHelper.playtts);
    }

    @FXML
    protected void handleTutor(ActionEvent event) {
        UI.goToScene("admin");
    }

    @FXML
    protected void handleSubmit(ActionEvent event) {
        String value = input.getText();
        value = value.trim().replaceAll("[^A-Za-z]", "");

        if (value.length() == 0) {
            errorText.setText("Please fill in a valid name(only letters).");
            return;
        }

        UI.state.user = null;

        for (UserData user : Database.users) {
            if (user.name.toLowerCase().equals(value)) {
                UI.state.user = user;
                System.out.println(user);
                break;
            }
        }

        if (UI.state.user == null) {
            UI.state.user = new UserData();
            UI.state.user.name = value;
            UI.state.user.filename = value.toLowerCase() + ".userdata";
        }

        System.out.println("Selected user " + UI.state.user.name);
        TTSHelper.toggle(UI.state.user.getBoolPreference("useTTS"));

        UI.goToScene("startmenu");
    }

    @FXML
    protected void SUBMITTTSButton(){
        TTSHelper.tts.speak("SUBMIT",false, TTSHelper.playtts);
    }


    @FXML
    protected void TUTORTTSButton(){
        TTSHelper.tts.speak("TUTOR",false, TTSHelper.playtts);
    }
}
