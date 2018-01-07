package GUI;

import database.*;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.util.ArrayList;

public class SelectQuestion {

    private static GridPane centergrid;
    private static ArrayList<ClickQuestion> questionlist = new ArrayList<>();
    private static ImageView qimagev;
    private static Image qimage;
    private static Circle selected = new Circle();
    private static double ratio; //how much of the screen the image should be (1 is full 0 is nothing)
    private static int i;
    private static Button submit;
    private static Button next;
    private static Text response;
    private static ClickQuestion q;
    private static double imgratio;
    private static Rectangle correct = new Rectangle();
    private static int score;
    private static int totalanswered;
    private static Button stop;

    public static void askQuestions(GridPane grid) {
        ratio = .5;
        score = 0;
        totalanswered = 0;
        //Make a questionlist and add questions
        Database.loadDatabase();
        ArrayList<Question> allquestions = Database.getQuestionsForLevel(1);
        for (Question q : allquestions) {
            if (q instanceof ClickQuestion) {
                questionlist.add((ClickQuestion) q);
            }
        }

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
        if (index >= questionlist.size()) {
            done();
            return;
        }
        //Clear the grid
        centergrid.getChildren().clear();
        //Get the question
        q = questionlist.get(index);
        //Show the question
        Text questiontext = new Text(q.text);
        centergrid.add(questiontext, 0, 0, 2, 1);
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
        centergrid.add(imagepane, 0, 1, 2, 1);
        //Add the image to the pane
        imagepane.getChildren().add(qimagev);
        //Prepare response text
        response = new Text("");
        centergrid.add(response, 0, 2, 2, 1);
        //Disable the submit button
        submit.setDisable(true);
        centergrid.add(submit, 0, 2);
        centergrid.add(stop, 1, 2);

        //Add the circle to show what point is selected
        imagepane.getChildren().add(selected);
        imagepane.getChildren().add(correct);

        //Set what should be done when someone clicks the image and moves the mouse
        qimagev.setOnMousePressed(e -> beginDrag(e));

        qimagev.setOnMouseDragged(e -> setCoordinates(e.getX(), e.getY()));

        qimagev.setOnMouseReleased(e -> endDrag());
        //If the rectangle is clicked the drag should start over again as well
        selected.setOnMousePressed(e -> beginDrag(e));

        //At the beginning the circle is not visible
        selected.setFill(Color.YELLOW);
        selected.setRadius(0);

        correct.setOpacity(0.5);
        correct.setWidth(0);
        correct.setHeight(0);
    }

    private static void setBeginCoordinates(double X, double Y) {
        //Read and set the coordinates at the beginning
        selected.setCenterX(X);
        selected.setCenterY(Y);
        selected.setRadius(5);
    }

    private static void setCoordinates(double X, double Y) {
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

    private static void makeButtons(){
        //Button to go to the next question
        next = new Button("Continue");
        next.setOnAction(e -> {
            i++;
            showQuestion(i);
        });
        //Button to submit the answer
        submit = new Button("Submit");
        submit.setOnAction(e -> {
            totalanswered++;
            //If the answer is correct
            if (q.isCorrect(selected.getCenterX()*imgratio, selected.getCenterY()*imgratio)) {
                response.setFill(Color.DARKGREEN);
                response.setText("That is correct. Click continue to go to the next question.");
                selected.setFill(Color.DARKGREEN);
                score++;
            }
            //If the answer is not correct
            else {
                response.setFill(Color.FIREBRICK);
                response.setText("That is incorrect, you can see the answer on the image now.\n" +
                        "Click \"continue\" to go to the next question.");
                correct.setX(q.topLeft.x/imgratio);
                correct.setY(q.topLeft.y/imgratio);
                correct.setWidth((q.bottomRight.x - q.topLeft.x)/imgratio);
                correct.setHeight((q.bottomRight.y - q.topLeft.y)/imgratio);
                correct.setFill(Color.FIREBRICK);
            }
            //Show the button to go to the next question
            centergrid.add(next, 0, 3);
            centergrid.getChildren().remove(submit);
            centergrid.getChildren().remove(stop);
            centergrid.add(stop, 1, 3);
        });
        stop = new Button("Stop Quiz");
        stop.setOnAction(e -> done());
    }

    private static void beginDrag(MouseEvent e) {
        //At the start of a drag set rectangle size to 0 and set coordinates
        //Also set the color back
        setBeginCoordinates(e.getX(), e.getY());
        submit.setDisable(true);
        selected.setFill(Color.YELLOW);
    }

    private static void endDrag() {
        //Enable the submit button and make the color a bit darker
        submit.setDisable(false);
    }

    private static void incScore(){
        score++;
    }

    private static void done() {
        //Make the texts for the ending
        Text end = new Text("That were all the question, well done!");
        Text endscore = new Text("Your score is: " + score + " out of " + totalanswered);
        Text back = new Text("You will be redirected to the startscreen, when you click exit.");

        //Set IDs for CSS
        end.setId("end");
        endscore.setId("end");
        back.setId("end");

        //Clear centergrid and add the texts
        centergrid.getChildren().clear();
        centergrid.add(end, 0, 0);
        centergrid.add(endscore, 0, 1);
        centergrid.add(back, 0, 2);

        //Make the exit button and set the action
        Button exit = new Button("Exit");
        exit.setOnAction(e -> StartMenu.display());

        //HBox to get the exit button in the center beneath the text
        HBox exitbox = new HBox();
        exitbox.setAlignment(Pos.CENTER);
        exitbox.getChildren().add(exit);

        //Add the HBox with the button in it
        centergrid.add(exitbox ,0, 3);
    }


}
