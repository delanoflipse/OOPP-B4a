package GUI;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Admin extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        //Set panes
        StackPane rootpane = new StackPane();
        GridPane titlegrid = new GridPane();
        GridPane centergrid = new GridPane();
        rootpane.getChildren().addAll(titlegrid, centergrid);

        //Set settings for titlegrid
        primaryStage.setTitle("Stichting Lezen en Schrijven - Admin");
        titlegrid.setAlignment(Pos.TOP_LEFT);
        titlegrid.setHgap(30);
        titlegrid.setVgap(10);
        titlegrid.setPadding(new Insets(25, 25, 25, 25));

        //Add logo
        Image logo = new Image("file:src/images/logo.png");
        ImageView logov = new ImageView();
        logov.setImage(logo);
        logov.setFitWidth(100);
        logov.setPreserveRatio(true);
        logov.setSmooth(true);
        logov.setCache(true);
        titlegrid.add(logov, 0, 0);

        //Add title
        Text title = new Text("Admin Program");
        title.setId("Title");
        titlegrid.add(title, 1, 0);

        //Set centergrid settings
        centergrid.setAlignment(Pos.CENTER_LEFT);
        centergrid.setHgap(30);
        centergrid.setVgap(10);
        centergrid.setPadding(new Insets(25, 25, 25, 25));

        //Add subtitle and info text
        Text subtitle = new Text("Here you can add new Questions for the practice program.");
        subtitle.setId("subtitle");
        Text info = new Text("Please click on the type of question you want to add.");
        info.setId("info");

        centergrid.add(subtitle, 1, 0);
        centergrid.add(info, 1, 1);

        //Define scene here so that we can use it in the button action
        Scene scene = new Scene(rootpane, 1920, 1080);

        //Set the button to go to the addMCquestion interface
        Button MCbut = new Button("1");
        MCbut.setOnAction(e -> {
            AddMCQuestion.AddQuestion(primaryStage, scene);
        });
        Label MCtext = new Label("Multiple Choiche Question");

        //Add the button and text
        centergrid.add(MCbut, 0, 2);
        centergrid.add(MCtext, 1, 2);

        //Set CSS and show the program
        scene.getStylesheets().add("file:src/stylesheets/admin.css");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
