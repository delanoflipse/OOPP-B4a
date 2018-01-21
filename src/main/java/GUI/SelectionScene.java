package GUI;

import database.Database;
import database.Question;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

public class SelectionScene extends UIScene implements Initializable {

    @FXML private ImageView logoImage1, logoImage2;
    @FXML private Text diffText;
    @FXML private Button returnBtn;

    private String selectedValue = "";
    private String[] possibleValues = {"Easy", "Medium", "Hard"};

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // set images
        Image image = new Image("file:src/images/logo.png");
        logoImage1.setImage(image);
        logoImage2.setImage(image);

        setButtonImage(returnBtn, "file:src/images/arrowleft.png");

        selectedValue = possibleValues[0];
        diffText.setText(selectedValue);
    }

    @FXML protected void handleStart(ActionEvent event) {
        int level = java.util.Arrays.asList(possibleValues).indexOf(selectedValue) + 1;
        String type = (String) UI.state.context.get("type");
        Database.loadDatabase();
        ArrayList<Question> questions = Database.getQuestionsForLevel(level, type);

        if (questions.size() == 0) {
            System.out.println("No questions for level " + level + " with type " + type);
            UI.goToScene("startmenu");
            return;
        }

        // setup context
        UI.state.setContext(
                new UIContext()
                        .set("questions", questions)
                        .set("index", 0)
                        .set("score", 0)
                        .set("date", getDate())
        );

        switch (type) {
            case "ClickQuestion":
                UI.goToScene("imagequestions");
                break;
            case "TextQuestion":
                UI.goToScene("mcquestions");
                break;
            case "GUIQuestion":
                UI.goToScene("guiquestions");
                break;
            default:
                System.out.println("I have no idea what to do, going back");
                UI.goToScene("startmenu");
                break;
        }
    }

    @FXML protected void handleBack(ActionEvent event) {
        // setup context
        UI.goToScene("startmenu");
    }

    @FXML protected void selectNext(ActionEvent event) {
        int index = java.util.Arrays.asList(possibleValues).indexOf(selectedValue);
        index++;
        if (index >= possibleValues.length) {
            index = 0;
        }
        selectedValue = possibleValues[index];
        diffText.setText(selectedValue);
    }

    @FXML protected void selectPrev(ActionEvent event) {
        int index = java.util.Arrays.asList(possibleValues).indexOf(selectedValue);
        index--;
        if (index < 0) {
            index = possibleValues.length - 1;
        }
        selectedValue = possibleValues[index];
        diffText.setText(selectedValue);
    }

    private String getDate() {
        String date = new SimpleDateFormat("EEEE d MMMM").format(new Date());
        String time = new SimpleDateFormat("h:mm a").format(new Date());
        return date + " at " + time;
    }
}
