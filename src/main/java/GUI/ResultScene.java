package GUI;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import tts.TTSHelper;

import static javafx.collections.FXCollections.observableArrayList;

import java.net.URL;
import java.util.ResourceBundle;

public class ResultScene extends UIScene implements Initializable {

    @FXML
    private ImageView logoImage1, logoImage2;
    @FXML private Button returnBtn;
    @FXML private PieChart pieChart;
    @FXML private Text scoreText;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // set image
        Image image = new Image("file:src/images/logo.png");
        logoImage1.setImage(image);
        logoImage2.setImage(image);

        setButtonImage(returnBtn, "file:src/images/arrow.png");

        int currentScore = (int) UI.state.context.get("score");

        TTSHelper.ttsfinal = "your score was ," + TTSHelper.tripleAsText(currentScore,false);
        TTSHelper.tts.speak(TTSHelper.ttsfinal,false, TTSHelper.playtts);

        scoreText.setText("Your score was: "+currentScore);

        int currentTotal = (int) UI.state.context.get("total") * 10;

        ObservableList<PieChart.Data> pieChartData = observableArrayList(
                new PieChart.Data("Correct", currentScore),
                new PieChart.Data("Wrong", currentTotal-currentScore));
        pieChart.setData(pieChartData);
        pieChart.setStartAngle(90);
        pieChart.setLabelsVisible(false);
        pieChart.setTitle("Results");
    }

    @FXML
    protected void handleReturn(ActionEvent event) {
        UI.goToScene("startmenu");
    }

    //tts buttons

    @FXML
    protected void RETURNTTSButton(){
        TTSHelper.tts.speak("go back to the main screen",false, TTSHelper.playtts);
    }
}
