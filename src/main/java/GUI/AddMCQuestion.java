package GUI;

import database.Answer;
import database.Database;
import database.TextAnswer;
import database.TextQuestion;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import javax.xml.crypto.Data;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AddMCQuestion  extends UIScene implements Initializable {

    @FXML private ImageView logoImage1;
    @FXML private TextField questionField;
    @FXML private TextField levelField;
    @FXML private GridPane answerGrid;

    private ToggleGroup answergroup = new ToggleGroup();
    private ArrayList<AnswerGroup> answers = new ArrayList<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // set image
        Image image = new Image("file:src/images/logo.png");
        logoImage1.setImage(image);

        addAnswerGroup(null);
    }

    @FXML
    private void addAnswerGroup(ActionEvent e) {
        AnswerGroup ng = new AnswerGroup();
        int index = answers.size() + 1;
        answerGrid.add(ng.removeButton, 0, index);
        answerGrid.add(ng.textField, 1, index);
        answerGrid.add(ng.checkButton, 2, index);
        ng.checkButton.setToggleGroup(answergroup);
        ng.removeButton.setOnAction((ActionEvent event) -> {
            answerGrid.getChildren().remove(ng.checkButton);
            answerGrid.getChildren().remove(ng.textField);
            answerGrid.getChildren().remove(ng.removeButton);
            answers.remove(ng);
        });

        ng.checkButton.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable,
                                Boolean oldValue, Boolean newValue) {
                ng.correct = newValue;
            }
        });

        answers.add(ng);
    }

    @FXML
    protected void handleExit(ActionEvent e) {
        UI.goToScene("admin");
    }

    @FXML
    protected void handleSave(ActionEvent e) {
        RadioButton ans = (RadioButton) answergroup.getSelectedToggle();
        String question = questionField.getText();
        String leveltxt = levelField.getText();

        if (ans == null || question.length() == 0 || leveltxt.length() == 0) {
            return;
        }
        int level;

        try {
            level = Integer.parseInt(leveltxt);
        } catch (NumberFormatException error) {
            return;
        }

        if (level < 1 || level > 3) {
            return;
        }

        TextQuestion q = new TextQuestion();
        q.level = level;
        q.text = question;

        for (AnswerGroup ag: answers) {
            TextAnswer ta = new TextAnswer();
            ta.text = ag.textField.getText();
            ta.correct = ag.correct;
            q.answers.add(ta);
        }

        Database.questions.add(q);

        try {
            PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter("db.data", true)));
            writer.print("\n");

            //Write all the data
            writer.println("#Question Added by the MC UI");
            writer.println("type: multiple choice");
            writer.println("level: " + q.level);
            writer.println("question: "+ q.text);

            for (TextAnswer ta: q.answers) {
                if (ta.correct) {
                    writer.println("correctanswer: " + ta.text);
                } else {
                    writer.println("answer: " + ta.text);
                }
            }

            //close the writer
            writer.close();
            //go back to the start menu
            UI.goToScene("admin");
        } catch (IOException err) {
            //Print the exception message
            System.out.println(err.getMessage());
        }

    }
}

class AnswerGroup {
    public TextField textField;
    public Button removeButton;
    public RadioButton checkButton;
    public boolean correct = false;

    public AnswerGroup() {
        this.textField = new TextField();
        this.removeButton = new Button("X");
        this.checkButton = new RadioButton();

        this.removeButton.getStyleClass().add("red");
        this.removeButton.getStyleClass().add("small");
    }
}
