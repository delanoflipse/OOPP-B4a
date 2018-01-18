package GUI;


import database.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class SelectQuestion extends UIScene implements Initializable {

    private static GridPane centergrid;
    private static ArrayList<ClickQuestion> questionlist = new ArrayList<>();
    private static double ratio; //how much of the screen the image should be (1 is full 0 is nothing)
    private static int i;
    private static Button submit;
    private static Button next;
    private static Text response;
    private static ClickQuestion q;

    private ClickQuestion question;
    private ArrayList<Question> questions;
    private int index;
    private Image qimage;
    private double imgratio;

    @FXML private ImageView logoImage1;
    @FXML private ImageView qimagev;
    @FXML private Text questionText;
    @FXML private Text questionTitle;
    @FXML private Button submitButton;
    @FXML private Button continueButton;
    @FXML private Button exitButton;
    @FXML private Pane imagepane;
    @FXML private Text responseText;
    @FXML private Circle selected;
    @FXML private Rectangle correct;

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
        question = (ClickQuestion) questions.get(index);

        // set text
        questionText.setText("Question " + (1 + index));
        questionTitle.setText(question.text);

        qimage = new Image("file:src/images/" + question.image);
        qimagev.setImage(qimage);
        qimagev.setFitHeight(350);

        //image is changed by this ratio
        imgratio = qimage.getHeight()/qimagev.getFitHeight();

        //Make a clickable pane as big as the image
        imagepane.setPrefHeight(qimagev.getFitHeight());
        imagepane.setPrefWidth(qimage.getWidth() * imgratio);
    }

    private void showQuestion(int index) {
        //Clear the grid
        centergrid.getChildren().clear();
        //Get the question
        q = questionlist.get(index);
        //Show the question
        Text questiontext = new Text(q.text);
        centergrid.add(questiontext, 0, 0);
        //Load the image
        qimage = new Image("file:src/images/"+q.image);
        qimagev = new ImageView(qimage);
        qimagev.setFitHeight(centergrid.getHeight()*ratio);
        qimagev.setPreserveRatio(true);
        qimagev.setSmooth(true);

        //image is changed by this ratio
        imgratio = qimage.getHeight()/qimagev.getFitHeight();
        //Make a clickable pane as big as the image
        Pane imagepane = new Pane();
        imagepane.setPrefHeight(qimagev.getFitHeight());
        imagepane.setPrefWidth(qimage.getWidth()*imgratio);
        centergrid.add(imagepane, 0, 1);
        //Add the image to the pane
        imagepane.getChildren().add(qimagev);
        //Prepare response text
        response = new Text("");
        centergrid.add(response, 1, 2);
        //Disable the submit button
        submit.setDisable(true);
        centergrid.add(submit, 0, 2);

        //Add the circle to show what point is selected
        imagepane.getChildren().add(selected);
        imagepane.getChildren().add(correct);

        //At the beginning the circle is not visible
        selected.setFill(Color.YELLOW);
        selected.setRadius(0);

        correct.setOpacity(0.5);
        correct.setWidth(0);
        correct.setHeight(0);
    }

    @FXML
    private void setBeginCoordinates(double X, double Y) {
        //Read and set the coordinates at the beginning
        selected.setCenterX(X);
        selected.setCenterY(Y);
        selected.setRadius(5);
    }

    @FXML
    private void setCoordinates(MouseEvent e) {
        double X = e.getX();
        double Y = e.getY();
        //If the mouse is in the image
        if (X >= 0.0 && X <= qimagev.getFitHeight()/qimage.getHeight()*qimage.getWidth()) {
            //Set the X coordinate of the circle
            selected.setCenterX(X);
        }
        //Otherwise set is to the boundary
        else {
            if (X <= 0) {
                selected.setCenterX(0);
            }
            if (X > qimagev.getFitHeight()/qimage.getHeight()*qimage.getWidth()) {
                selected.setCenterX(qimagev.getFitHeight()/qimage.getHeight()*qimage.getWidth());
            }
        }
        //Same things for the Y value
        if (Y >= 0 && Y <= qimagev.getFitHeight()) {
            selected.setCenterY(Y);
        } else {
            if (Y <= 0) {
                selected.setCenterY(0);
                Y = 0;
            }
            if (Y > qimagev.getFitHeight()) {
                selected.setCenterY(qimagev.getFitHeight());
            }
        }
    }

    @FXML
    private void beginDrag(MouseEvent e) {
        //At the start of a drag set rectangle size to 0 and set coordinates
        //Also set the color back
        setBeginCoordinates(e.getX(), e.getY());
        submitButton.setDisable(true);
        selected.setFill(Color.YELLOW);
    }

    @FXML
    private void endDrag() {
        //Enable the submit button and make the color a bit darker
        submitButton.setDisable(false);
    }

    @FXML
    protected void handleSubmit(ActionEvent event) {
//        Toggle ans = answergroup.getSelectedToggle();
//        if (ans == null) {
//            responseText.setText("Please select a value!");
//            responseText.setVisible(true);
//            return;
//        }

//        String val = (String) ans.getUserData();
//        boolean correct = question.isCorrect(val);
        boolean correct = true;

        continueButton.setVisible(true);
        responseText.setVisible(true);
        submitButton.setDisable(true);

        if (correct) {
            responseText.setFill(Color.DARKGREEN);
            responseText.setText("That is correct. Click continue to go to the next question");
        } else {
            responseText.setFill(Color.FIREBRICK);
            responseText.setText("That is incorrect. Click continue to go to the next question");
        }
    }

    @FXML
    protected void handleContinue(ActionEvent event) {
        if (questions.size() - index <= 2) {
            UI.goToScene("startmenu");
        } else {
            UI.state.context.set("index", ++index);
            UI.goToScene("imagequestions");
        }
    }
    @FXML
    protected void handleExit(ActionEvent event) {
        UI.goToScene("startmenu");
    }


}
