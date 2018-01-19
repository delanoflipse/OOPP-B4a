package GUI;

import database.Database;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import user.UserData;
import user.UserDateScore;

import java.net.URL;
import java.util.*;

public class ScoreScene implements Initializable {

    @FXML
    GridPane scoreContainer;

    private final int MAX_SCORES = 10;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        UI.setCSS("base.css");
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
            return;
        }

        for (UserDateScore score : scores) {
            if (index++ == MAX_SCORES) {
                break;
            }

            scoreContainer.add(new Text(score.user.name), 0, index);
            scoreContainer.add(new Text(score.date), 1, index);
            scoreContainer.add(new Text(score.score + ""), 2, index);
        }
    }

    @FXML
    protected void handleReturn(ActionEvent event) {
        UI.goToScene("startmenu");
    }
}
