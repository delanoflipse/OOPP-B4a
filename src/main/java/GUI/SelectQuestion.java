package GUI;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import main.*;

import java.util.ArrayList;

public class SelectQuestion {

    private static GridPane centergrid;
    private static ArrayList<main.SelectQuestion> questionlist;
    private static double ltX;
    private static double ltY;
    private static double rbX;
    private static double rbY;
    private static Rectangle selected = new Rectangle();
    private static ImageView qimagev;
    private static Image qimage;
    private static double ratio; //how much of the screen the image should be (1 is full 0 is nothing)
    private static int i;
    private static Button submit;
    private static Button next;
    private static Text response;
    private static main.SelectQuestion q;
    private static double imgratio;


    public static void askQuestions(GridPane grid) {
        ratio = .5;

        //Make a questionlist and add questions, will be done by the database in the future
        questionlist = new ArrayList<>();
        questionlist.add(new main.SelectQuestion(687.0, 855.0, 900.0, 1080.0,  "background.jpg", "Select the tree."));
        questionlist.add(new main.SelectQuestion(539.325, 1584.68, 621.44, 1626.85,  "Wally.jpg", "Where's Wally?"));

        //Set the private centergrid to the one given by the funtion parameter
        centergrid = grid;
        //Set alignment to center left
        centergrid.setAlignment(Pos.CENTER_LEFT);
        //Show the buttons
        makeButtons();
        //Show the image and the question
        showQuestion(0);
    }

    private static void showQuestion(int index) {
        //Clear the grid
        centergrid.getChildren().clear();
        //Get the question
        q = questionlist.get(index);
        //Show the question
        Text questiontext = new Text(q.getQuestion());
        centergrid.add(questiontext, 0, 0);
        //Load the image
        qimage = new Image("file:src/images/"+q.getImgname());
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

        //Add the rectangle to show what part is selected
        imagepane.getChildren().add(selected);

        //Set what should be done when someone clicks the image and moves the mouse
        qimagev.setOnMousePressed(e -> beginDrag(e));

        qimagev.setOnMouseDragged(e -> setCoordinates(e.getX(), e.getY()));

        qimagev.setOnMouseReleased(e -> endDrag(e));
        //If the rectangle is clicked the drag should start over again as well
        selected.setOnMousePressed(e -> beginDrag(e));

        //At the beginning the rectangle is not visible
        selected.setOpacity(0.5);
        selected.setHeight(0);
        selected.setWidth(0);

    }

    private static void setBeginCoordinates(double X, double Y) {
        //Read and set the coordinates at the beginning
        ltX = X;
        ltY = Y;
    }

    private static void setCoordinates(double X, double Y) {
        /**System.out.println("X: " + X);
        System.out.println("Y: " + Y + "\n");**/

        //If the mouse is in the image
        if (X >= 0.0 && X <= qimagev.getFitHeight()/qimage.getHeight()*qimage.getWidth()) {
            //Set the rbX to the current value
            rbX = X;
        }
        //Otherwise set is to the boundary
        else {
            if (X <= 0) {
                rbX = 0.0;
                X = 0;
            }
            if (X > qimagev.getFitHeight()/qimage.getHeight()*qimage.getWidth()) {
                rbX = qimagev.getFitHeight()/qimage.getHeight()*qimage.getWidth();
            }
        }
        //Set the width (and position) of the rectangle
        if (rbX - ltX < 0) {
            selected.setX(X);
            selected.setWidth(ltX - rbX);
        } else {
            selected.setWidth(rbX - ltX);
        }
        //Same things for the Y value
        if (Y >= 0 && Y <= qimagev.getFitHeight()) {
            rbY = Y;
        } else {
            if (Y <= 0) {
                rbY = 0.0;
                Y = 0;
            }
            if (Y > qimagev.getFitHeight()) {
                rbY = qimagev.getFitHeight();
            }
        }

        if (rbY - ltY < 0) {
            selected.setY(Y);
            selected.setHeight(ltY - rbY);
        } else {
            selected.setHeight(rbY - ltY);
        }
    }

    //Made a function so that i can be changed with a button press
    private static void incIndex() {
        i++;
    }

    private static void makeButtons(){
        //Button to go to the next question
        next = new Button("Continue");
        next.setOnAction(e -> {
            incIndex();
            showQuestion(i);
        });
        //Button to submit the answer
        submit = new Button("submit");
        submit.setOnAction(e -> {
            //just debugging things
            /**System.out.println("imgRatio: " + imgratio);
            System.out.println("ltY: " + selected.getY()*imgratio);
            System.out.println("ltX: " + selected.getX()*imgratio);
            System.out.println("rbY: " +(selected.getY()+selected.getHeight())*imgratio);
            System.out.println("rbX: " +(selected.getX()+selected.getWidth())*imgratio);**/
            //If the answer is correct
            if (q.isCorrect(selected.getY(), selected.getX(), selected.getY()+selected.getHeight(), selected.getX()+selected.getWidth(), imgratio)) {
                response.setFill(Color.DARKGREEN);
                response.setText("That is correct. Click continue to go to the next question.");
                selected.setFill(Color.DARKGREEN);
            }
            //If the answer is not correct
            else {
                response.setFill(Color.FIREBRICK);
                response.setText("That is incorrect, you can see the answer on the image now.\n" +
                        "Click \"continue\" to go to the next question.");
                selected.setX(q.getCorrectltX()/imgratio);
                selected.setY(q.getCorrectltY()/imgratio);
                selected.setWidth((q.getCorrectrbX() - q.getCorrectltX())/imgratio);
                selected.setHeight((q.getCorrectrbY() - q.getCorrectltY())/imgratio);
                selected.setFill(Color.FIREBRICK);
            }
            //Show the button to go to the next question
            centergrid.add(next, 0, 3);
        });
    }

    private static void beginDrag(MouseEvent e) {
        //At the start of a drag set rectangle size to 0 and set coordinates
        //Also set the color back
        selected.setX(e.getX());
        selected.setY(e.getY());
        selected.setWidth(0);
        selected.setHeight(0);
        setBeginCoordinates(e.getX(), e.getY());
        submit.setDisable(true);
        selected.setFill(Color.SKYBLUE);
    }

    private static void endDrag(MouseEvent e) {
        //If there is a rectangle
        if (selected.getWidth() >= 5 && selected.getHeight() >= 5) {
            //Enable the submit button and make the color a bit darker
            submit.setDisable(false);
            selected.setFill(Color.DEEPSKYBLUE);
            /**System.out.println("X: "+e.getX());
            System.out.println("Y: "+e.getY());**/
        }
    }

}
