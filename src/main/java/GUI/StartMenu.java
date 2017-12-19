package GUI;

import com.sun.management.VMOption;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.RadioButton;
import javafx.scene.paint.Color;
import main.*;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;


import java.util.ArrayList;

public class StartMenu extends Application {

    @Override
    public void start(Stage primaryStage) {
        StackPane rootpane = new StackPane();
        GridPane titlegrid = new GridPane();
        GridPane centergrid = new GridPane();
        rootpane.getChildren().addAll(titlegrid, centergrid);

        primaryStage.setTitle("Stichting Lezen en Schrijven - Practice Program");
        titlegrid.setAlignment(Pos.TOP_CENTER);
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
        ImageView logov2 = new ImageView();
        logov2.setImage(logo);
        logov2.setFitWidth(100);
        logov2.setPreserveRatio(true);
        logov2.setSmooth(true);
        logov2.setCache(true);
        titlegrid.add(logov2, 2, 0);

        Text title = new Text("Practice Program");
        title.setId("Title");
        titlegrid.add(title, 1, 0);

        centergrid.setAlignment(Pos.CENTER);
        centergrid.setHgap(30);
        centergrid.setVgap(10);
        centergrid.setPadding(new Insets(25, 25, 25, 25));

        Button MCbutton = new Button("Multiple Choiche Questions");
        MCbutton.setMinSize(300, 300);
        MCbutton.setId("MCButton");
        centergrid.add(MCbutton, 0, 0);

        Button Selectimgbutton = new Button("Select part of Image Questions");
        Selectimgbutton.setMinSize(300, 300);
        Selectimgbutton.setId("SelectIMGButton");
        centergrid.add(Selectimgbutton, 1, 0);

        titlegrid.setGridLinesVisible(false);
        centergrid.setGridLinesVisible(false);

        Scene scene = new Scene(rootpane, 1280, 720);
        MCbutton.setOnAction(e -> {
            MCQuestion.askQuestions(primaryStage, scene);
        });
        Selectimgbutton.setOnAction(e -> {
            GUI.SelectQuestion.askQuestions(primaryStage);
        });

        scene.getStylesheets().add("file:src/stylesheets/start_menu.css");
        primaryStage.setScene(scene);
        primaryStage.setHeight(720);
        primaryStage.setWidth(1280);
        primaryStage.show();

    }

}
