package GUI;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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


    public static void askQuestions(Stage stage) {
        primaryStage = stage;

        ratio = 0.5;

        questionlist = new ArrayList<main.SelectQuestion>();

        questionlist.add(new main.SelectQuestion(229.0, 285.0, 300.0, 360.0, 20.0, "background.jpg", "Select the tree."));

        GridPane titlegrid = new GridPane();
        titlegrid.setAlignment(Pos.TOP_LEFT);
        titlegrid.setHgap(30);
        titlegrid.setVgap(10);
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

        showQuestion(0);
    }

    private static void showQuestion(int index) {
        centergrid.getChildren().clear();

        main.SelectQuestion q = questionlist.get(index);

        Text questiontext = new Text(q.getQuestion());
        centergrid.add(questiontext, 0, 0);

        qimage = new Image("file:src/images/"+q.getImgname());
        qimagev = new ImageView(qimage);
        qimagev.setFitHeight(primaryStage.getHeight()*ratio);
        qimagev.setPreserveRatio(true);
        qimagev.setSmooth(true);

        Pane imagepane = new Pane();
        imagepane.setPrefHeight(qimagev.getFitHeight());
        imagepane.setPrefWidth(qimage.getWidth()*ratio);
        centergrid.add(imagepane, 0, 1);

        imagepane.getChildren().add(qimagev);

        Text response = new Text("");
        centergrid.add(response, 1, 2);

        Button submit = new Button("submit");
        submit.setOnAction(e -> {
            if (q.isCorrect(selected.getY(), selected.getX(), selected.getY()+selected.getHeight(), selected.getX()+selected.getWidth())) {
                response.setFill(Color.DARKGREEN);
                response.setText("That is correct. Click continue to go to the next question.");
                selected.setFill(Color.DARKGREEN);
            }
            else {
                response.setFill(Color.FIREBRICK);
                response.setText("That is incorrect, you can see the answer on the image now.\n" +
                "Click \"continue\" to go to the next question.");
                selected.setX(q.getCorrectltX());
                selected.setY(q.getCorrectltY());
                selected.setWidth(q.getCorrectrbX() - q.getCorrectltX());
                selected.setHeight(q.getCorrectrbY() - q.getCorrectltY());
                selected.setFill(Color.FIREBRICK);
            }
        });

        submit.setDisable(true);
        centergrid.add(submit, 0, 2);


        imagepane.getChildren().add(selected);

        qimagev.setOnDragDetected(e -> {
            System.out.println("Drag Begon!");
            selected.setX(e.getX());
            selected.setY(e.getY());
            setBeginCoordinates(e.getX(), e.getY());
            submit.setDisable(false);
            selected.setFill(Color.SKYBLUE);
        });

        qimagev.setOnMouseDragged(e -> {
            setCoordinates(e.getX(), e.getY());
        });

        qimagev.setOnMouseReleased(e -> {
            System.out.println("Drag Done!");
            System.out.println("ltX: "+ltX);
            System.out.println("ltY: "+ltY);
            System.out.println("rbX: "+rbX);
            System.out.println("rbY: "+rbY);
            submit.setDisable(false);
            selected.setFill(Color.DEEPSKYBLUE);
        });

        selected.setOpacity(0.5);
        selected.setHeight(0);
        selected.setWidth(0);

    }

    private static void setBeginCoordinates(double X, double Y) {
        ltX = X;
        ltY = Y;
    }

    private static void setCoordinates(double X, double Y) {
        System.out.println("X: " + X);
        System.out.println("Y: " + Y + "\n");

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
        }
        else {
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

}
