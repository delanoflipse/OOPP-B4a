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

public class StartMenu extends UIScene {

    private static ImageView logov;
    private static ImageView logov2;
    private static GridPane centergrid;
    private static GridPane titlegrid;
    private static Scene scene;
    private static Text title;
    private static Button MCbutton;
    private static Button Selectimgbutton;

    @Override
    public void render(UI gui, UIContext context) {
        //Set the title of the stage
        gui.setTitle("Stichting Lezen en Schrijven - Practice Program");
        gui.setCSS("start_menu.css");

        titlegrid = new GridPane();
        centergrid = new GridPane();

        //Some settings for the grid at the top
        titlegrid.setHgap(30);
        titlegrid.setVgap(10);
        titlegrid.setPadding(new Insets(25, 25, 25, 25));

        //Load logos for the title and add the first logo
        //Second logo is added in display() because only the start menu has two logos
        logov = createImageView("logo.png");
        titlegrid.add(logov, 0, 0);
        logov2 = createImageView("logo.png");

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

        //Gridlines for debugging are off
        titlegrid.setGridLinesVisible(false);
        centergrid.setGridLinesVisible(false);
      
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
            gui.scene.getStylesheets().clear();
            gui.scene.getStylesheets().add("file:src/stylesheets/MCquestions.css");
        });

        Selectimgbutton.setOnAction(e -> {
            //Change the title
            titlegrid.setAlignment(Pos.TOP_LEFT);
            title.setText("Select Image Questions");
            //Remove the second logo
            titlegrid.getChildren().remove(logov2);
            //Set other CSS file
            gui.scene.getStylesheets().clear();
            //We dont have CSS for this question yet
            //scene.getStylesheets().add("file:src/stylesheets/");

            //Ask the questions
            SelectQuestion.askQuestions(centergrid);
        });

        //Display the menu
        display();

        gui.root.getChildren().addAll(titlegrid, centergrid);

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

        //Clear the centergrid and add the buttons
        centergrid.getChildren().clear();
        centergrid.add(MCbutton, 0, 0);
        centergrid.add(Selectimgbutton, 1, 0);
    }

}
