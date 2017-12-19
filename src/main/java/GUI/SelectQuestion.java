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


    public static void askQuestions(Stage stage) {
        primaryStage = stage;

        questionlist = new ArrayList<main.SelectQuestion>();

        questionlist.add(new main.SelectQuestion(229.0, 285.0, 300.0, 360.0, 20.0, "background.jpg", "Select the top loft corner"));

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

        Image qimage = new Image("file:src/images/"+q.getImgname());
        ImageView qimagev = new ImageView(qimage);
        qimagev.setFitHeight(primaryStage.getHeight()*0.5);
        qimagev.setPreserveRatio(true);
        qimagev.setSmooth(true);

        Pane imagepane = new Pane();
        imagepane.setPrefHeight(qimagev.getFitHeight());
        imagepane.setPrefWidth(qimagev.getFitWidth());
        centergrid.add(imagepane, 0, 1);

        imagepane.getChildren().add(qimagev);

        Text response = new Text("");
        centergrid.add(response, 1, 2);

        Button submit = new Button("submit");
        submit.setOnAction(e -> {
            if (q.isCorrect(ltY, ltX, rbY, rbX)) {
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


        imagepane.getChildren().add(selected);

        qimagev.setOnDragDetected(e -> {
            System.out.println("Drag Begon!");
            setBeginCoordinates(e.getX(), e.getY());
            selected.setX(e.getX());
            selected.setY(e.getY());
            selected.setOpacity(0.5);
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
            if (!centergrid.getChildren().contains(submit)) {
                centergrid.add(submit, 0, 2);
            }
            selected.setFill(Color.DEEPSKYBLUE);
        });


    }

    private static void setBeginCoordinates(double X, double Y) {
        ltX = X;
        ltY = Y;
    }

    private static void setCoordinates(double X, double Y) {
        if (X >= ltX) {
            rbX = X;
        }
        else {
            rbX = ltX;
            ltX = X;
            selected.setX(X);
        }
        selected.setWidth(rbX - ltX);
        if (Y >= ltY) {
            rbY = Y;
        }
        else {
            rbY = ltY;
            ltY = Y;
            selected.setY(Y);
        }
        selected.setHeight(rbY - ltY);
    }

}
