package GUI;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;


import java.util.ArrayList;

public class StartMenu extends Application {

    private static ImageView logov;
    private static ImageView logov2;
    private static GridPane centergrid;
    private static GridPane titlegrid;
    private static Scene scene;
    private static Text title;
    private static Button MCbutton;
    private static Button Selectimgbutton;
    private static Button GUIbutton;

    @Override
    public void start(Stage primaryStage) {
        //Set up the different panes
        StackPane rootpane = new StackPane();
        titlegrid = new GridPane();
        centergrid = new GridPane();
        rootpane.getChildren().addAll(titlegrid, centergrid);

        //Set the title of the stage
        primaryStage.setTitle("Stichting Lezen en Schrijven - Practice Program");
        //Some settings for the grid at the top
        titlegrid.setHgap(30);
        titlegrid.setVgap(10);
        titlegrid.setPadding(new Insets(25, 25, 25, 25));

        //Load logos for the title and add the first logo
        //Second logo is added in display() because only the start menu has two logos
        Image logo = new Image("file:src/images/logo.png");
        logov = new ImageView();
        logov.setImage(logo);
        logov.setFitWidth(100);
        logov.setPreserveRatio(true);
        logov.setSmooth(true);
        logov.setCache(true);
        titlegrid.add(logov, 0, 0);
        logov2 = new ImageView();
        logov2.setImage(logo);
        logov2.setFitWidth(100);
        logov2.setPreserveRatio(true);
        logov2.setSmooth(true);
        logov2.setCache(true);

        //Make the title text and add it
        title = new Text();
        title.setId("Title");
        titlegrid.add(title, 1, 0);

        //some setup for the grid in the center
        centergrid.setHgap(30);
        centergrid.setVgap(10);
        centergrid.setPadding(new Insets(25, 25, 25, 25));

        //Make the button for MCQuestion
        MCbutton = new Button("Multiple Choiche Questions");
        MCbutton.setMinSize(300, 300);
        MCbutton.setId("MCbutton");

        //Make the button for the ImageQuestions
        Selectimgbutton = new Button("Select part of Image Questions");
        Selectimgbutton.setMinSize(300, 300);
        Selectimgbutton.setId("SelectIMGButton");

        //Make button for the GUIQuestions
        GUIbutton = new Button("GUI Elements Question");
        GUIbutton.setMinSize(300, 300);
        GUIbutton.setId("GUIbutton");

        //Gridlines for debugging are off
        titlegrid.setGridLinesVisible(false);
        centergrid.setGridLinesVisible(false);

        //Make scene to display the panes in
        scene = new Scene(rootpane, 1280, 720);
      
        //Set actions for the buttons
        MCbutton.setOnAction(e -> {
            //Change the title
            titlegrid.setAlignment(Pos.TOP_LEFT);
            title.setText("Multiple Choiche Questions");
            //Remove the second logo
            titlegrid.getChildren().remove(logov2);
            //Ask the questions
            MCQuestion.askQuestions(centergrid);
            //Set other CSS file
            scene.getStylesheets().clear();
            scene.getStylesheets().add("file:src/stylesheets/MCquestions.css");
        });

        Selectimgbutton.setOnAction(e -> {
            //Change the title
            titlegrid.setAlignment(Pos.TOP_LEFT);
            title.setText("Select Image Questions");
            //Remove the second logo
            titlegrid.getChildren().remove(logov2);
            //Set other CSS file
            scene.getStylesheets().clear();
            //We dont have CSS for this question yet
            //scene.getStylesheets().add("file:src/stylesheets/");

            //Ask the questions
            GUI.SelectQuestion.askQuestions(centergrid);
        });

        GUIbutton.setOnAction(e -> {
            //Change the title
            titlegrid.setAlignment(Pos.TOP_LEFT);
            title.setText("GUI Elements Questions");
            //Remove the second logo
            titlegrid.getChildren().remove(logov2);
            //Set other CSS file
            scene.getStylesheets().clear();
            //We dont have CSS for this question yet
            //scene.getStylesheets().add("file:src/stylesheets/");

            //Ask the questions
            GUI.GUIQuestion.askQuestions(centergrid);
        });

        //Set the scene and size of the stage
        scene.getStylesheets().add("file:src/stylesheets/start_menu.css");
        primaryStage.setScene(scene);
        primaryStage.setHeight(720);
        primaryStage.setWidth(1280);

        //Display the menu
        display();

        //Show it all
        primaryStage.show();

    }

    public static void display(){
        //Set alignment titlegrid to center
        titlegrid.setAlignment(Pos.TOP_CENTER);

        //Change the title text
        title.setText("Practice Program");

        //Add the second logo
        titlegrid.add(logov2, 2, 0);

        //Set centergrid alignment
        centergrid.setAlignment(Pos.CENTER);

        //Set the CSS
        scene.getStylesheets().clear();
        scene.getStylesheets().add("file:src/stylesheets/start_menu.css");

        //Clear the centergrid and add the buttons
        centergrid.getChildren().clear();
        centergrid.add(MCbutton, 0, 0);
        centergrid.add(Selectimgbutton, 1, 0);
        centergrid.add(GUIbutton, 2, 0);
    }

}
