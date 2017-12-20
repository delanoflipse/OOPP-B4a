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

    private static Stage primaryStage;
    private static StackPane root = new StackPane();
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


    public static void askQuestions(Stage stage) {
        primaryStage = stage;

        ratio = 1;

        questionlist = new ArrayList<main.SelectQuestion>();

        questionlist.add(new main.SelectQuestion(687.0, 855.0, 900.0, 1080.0, 50.0, "background.jpg", "Select the tree."));
        questionlist.add(new main.SelectQuestion(539.325, 1584.68, 621.44, 1626.85, 20.0, "Wally.jpg", "Where's Wally?"));

        GridPane titlegrid = new GridPane();
        titlegrid.setAlignment(Pos.TOP_LEFT);
        titlegrid.setHgap(30);
        titlegrid.setVgap(30);
        titlegrid.setPadding(new Insets(25, 25, 25, 25));

        Image logo = new Image("file:src/images/logo.png");
        ImageView logov = new ImageView();
        logov.setImage(logo);
        logov.setFitWidth(100);
        logov.setPreserveRatio(true);
        logov.setSmooth(true);
        logov.setCache(true);
        titlegrid.add(logov, 0, 0);

        Text title = new Text("Select Image Questions");
        title.setId("Title");
        titlegrid.add(title, 1, 0);

        root.getChildren().add(titlegrid);

        centergrid = new GridPane();
        centergrid.setAlignment(Pos.CENTER_LEFT);
        centergrid.setHgap(30);
        centergrid.setVgap(10);
        centergrid.setPadding(new Insets(25, 25, 25, 25));

        root.getChildren().add(centergrid);
        Scene scene = new Scene(root, primaryStage.getWidth(), primaryStage.getHeight());
        primaryStage.setScene(scene);

        makeButtons();

        showQuestion(0);
    }

    private static void showQuestion(int index) {
        centergrid.getChildren().clear();

        q = questionlist.get(index);

        Text questiontext = new Text(q.getQuestion());
        centergrid.add(questiontext, 0, 0);

        qimage = new Image("file:src/images/"+q.getImgname());
        qimagev = new ImageView(qimage);
        qimagev.setFitHeight(primaryStage.getHeight()*ratio);
        qimagev.setPreserveRatio(true);
        qimagev.setSmooth(true);

        //image is changed by this ratio
        imgratio = qimage.getHeight()/qimagev.getFitHeight();

        Pane imagepane = new Pane();
        imagepane.setPrefHeight(qimagev.getFitHeight());
        imagepane.setPrefWidth(qimage.getWidth()*imgratio);
        centergrid.add(imagepane, 0, 1);

        imagepane.getChildren().add(qimagev);

        response = new Text("");
        centergrid.add(response, 1, 2);

        submit.setDisable(true);
        centergrid.add(submit, 0, 2);


        imagepane.getChildren().add(selected);

        qimagev.setOnMousePressed(e -> beginDrag(e));

        //qimagev.setOnDragDetected(e -> {
        //    System.out.println("Drag Begon!");
        //    selected.setX(e.getX());
        //    selected.setY(e.getY());
        //    setBeginCoordinates(e.getX(), e.getY());
        //    submit.setDisable(false);
        //    selected.setFill(Color.SKYBLUE);
        //});

        qimagev.setOnMouseDragged(e -> setCoordinates(e.getX(), e.getY()));

        qimagev.setOnMouseReleased(e -> endDrag(e));

        selected.setOnMousePressed(e -> beginDrag(e));

        selected.setOpacity(0.5);
        selected.setHeight(0);
        selected.setWidth(0);

    }

    private static void setBeginCoordinates(double X, double Y) {
        ltX = X;
        ltY = Y;
    }

    private static void setCoordinates(double X, double Y) {
        //System.out.println("X: " + X);
        //System.out.println("Y: " + Y + "\n");

        if (X >= 0.0 && X <= qimagev.getFitHeight()/qimage.getHeight()*qimage.getWidth()) {
            rbX = X;
        }
        else {
            if (X <= 0) {
                rbX = 0.0;
                X = 0;
            }
            if (X > qimagev.getFitHeight()/qimage.getHeight()*qimage.getWidth()) {
                rbX = qimagev.getFitHeight()/qimage.getHeight()*qimage.getWidth();
            }
        }

        if (rbX - ltX < 0) {
            selected.setX(X);
            selected.setWidth(ltX - rbX);
        } else {
            selected.setWidth(rbX - ltX);
        }

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

    private static void incIndex() {
        i++;
    }

    private static void makeButtons(){
        next = new Button("Continue");
        next.setOnAction(e -> {
            incIndex();
            showQuestion(i);
        });

        submit = new Button("submit");
        submit.setOnAction(e -> {
            //just debugging things
            System.out.println("imgRatio: " + imgratio);
            System.out.println("ltY: " + selected.getY()*imgratio);
            System.out.println("ltX: " + selected.getX()*imgratio);
            System.out.println("rbY: " +(selected.getY()+selected.getHeight())*imgratio);
            System.out.println("rbX: " +(selected.getX()+selected.getWidth())*imgratio);
            if (q.isCorrect(selected.getY(), selected.getX(), selected.getY()+selected.getHeight(), selected.getX()+selected.getWidth(), imgratio)) {
                response.setFill(Color.DARKGREEN);
                response.setText("That is correct. Click continue to go to the next question.");
                selected.setFill(Color.DARKGREEN);
            }
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
            centergrid.add(next, 0, 3);
        });
    }

    private static void beginDrag(MouseEvent e) {
        selected.setX(e.getX());
        selected.setY(e.getY());
        selected.setWidth(0);
        selected.setHeight(0);
        setBeginCoordinates(e.getX(), e.getY());
        submit.setDisable(true);
        selected.setFill(Color.SKYBLUE);
    }

    private static void endDrag(MouseEvent e) {
        if (selected.getWidth() >= 5 && selected.getHeight() >= 5) {
            submit.setDisable(false);
            selected.setFill(Color.DEEPSKYBLUE);
            System.out.println("X: "+e.getX());
            System.out.println("Y: "+e.getY());
        }
    }

}
