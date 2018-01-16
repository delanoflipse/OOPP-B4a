package GUI;

import database.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.lang.reflect.Array;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class MCQuestion extends UIScene implements Initializable {

    @FXML private ImageView logoImage1;
    @FXML private VBox buttonstack;
    @FXML private Text questionText;
    @FXML private Text questionTitle;

    private ToggleGroup answergroup = new ToggleGroup();

    private ArrayList<Question> questions;
    private int index;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // set image
        Image image = new Image("file:src/images/logo.png");
        logoImage1.setImage(image);
    }

    public void setup() {
        // set css
        UI.setCSS("MCquestions.css");

        // get state values
        index = (int) UI.state.context.get("index");
        questions = (ArrayList<Question>) UI.state.context.get("questions");
        TextQuestion question = (TextQuestion) questions.get(index);

        // set text
        questionText.setText("Question " + (1 + index));
        questionTitle.setText(question.text);

        // create buttons
        for (TextAnswer answer : question.answers) {
            RadioButton btn = new RadioButton(answer.text);
            btn.setToggleGroup(answergroup);
            buttonstack.getChildren().add(btn);
        }
    }

    @FXML
    protected void handleSubmit(ActionEvent event) {
        //TODO: check correct

        if (questions.size() - index <= 2) {
            UI.goToScene("startmenu");
        } else {
            UI.state.context.set("index", ++index);
            UI.goToScene("mcquestions");
        }
    }
}
