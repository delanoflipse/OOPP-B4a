package GUI;

import database.Database;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import user.UserData;
import user.UserDateScore;

import java.net.URL;
import java.util.*;
import tts.TTSHelper;
public class ScoreScene extends UIScene implements Initializable {

    @FXML
    GridPane scoreContainer;


    @FXML private Button returnBtn;
    private final int MAX_SCORES = 10;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setButtonImage(returnBtn, "file:src/images/arrowleft.png");
        
        TTSHelper.ttsfinal = "these are the scores in the leaderboard , ";
        ArrayList<UserDateScore> scores = new ArrayList<>();
        for (UserData user: Database.users) {
            scores.addAll(user.scores);
        }

        Comparator<UserDateScore> comparator = (UserDateScore left, UserDateScore right) -> {
                return right.score - left.score; // use your logic
        };

        scores.sort(comparator);

        int index = 0;

        if (scores.size() == 0) {
            scoreContainer.add(new Text("No scores available"), 0, 1);
            TTSHelper.ttsfinal = "no scores availible";
            return;
        }

        for (UserDateScore score : scores) {
            if (index++ == MAX_SCORES) {
                break;
            }

            scoreContainer.add(new Text(score.user.name), 0, index);
            scoreContainer.add(new Text(score.date), 1, index);
            scoreContainer.add(new Text(score.score + ""), 2, index);
            TTSHelper.ttsfinal = TTSHelper.ttsfinal + score.user.name + " scored " + score.score + " points on " + score.date + " , ,";
        }
        TTSHelper.tts.speak(TTSHelper.ttsfinal);
    }

    @FXML
    protected void handleReturn(ActionEvent event) {
        UI.goToScene("startmenu");
    }

    //tts button

    @FXML
    protected void BACKTTSButton(){
        TTSHelper.tts.speak("go back");
    }
}
