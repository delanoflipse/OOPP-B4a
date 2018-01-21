package GUI;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import tts.ttshelper;

import java.net.URL;
import java.util.ResourceBundle;

public class ResultScene extends UIScene implements Initializable {

    @FXML
    private ImageView logoImage1, logoImage2;
    @FXML private Button returnBtn;
    @FXML private Text text;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // set image
        Image image = new Image("file:src/images/logo.png");
        logoImage1.setImage(image);
        logoImage2.setImage(image);

        setButtonImage(returnBtn, "file:src/images/arrow.png");

        int currentScore = (int) UI.state.context.get("score");
        text.setText("" + currentScore);
        ttshelper.ttsfinal = "your score was ," + ttshelper.tripleAsText(currentScore,false);
        ttshelper.tts.speak(ttshelper.ttsfinal,false,ttshelper.playtts);
    }

    @FXML
    protected void handleReturn(ActionEvent event) {
        UI.goToScene("startmenu");
    }

    //tts buttons

    @FXML
    protected void RETURNTTSButton(){
        ttshelper.tts.speak("go back to the main screen",false,ttshelper.playtts);
    }
}
