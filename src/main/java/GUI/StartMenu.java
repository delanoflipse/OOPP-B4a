package GUI;

import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class StartMenu extends Application {

    private static ImageView logov;
    private static ImageView logov2;
    private static ImageView exit;
    private static ImageView tutor;
    private static GridPane centergrid;
    private static GridPane titlegrid;
    private static Scene scene;
    private static Text title;
    private static VBox vboxLeft;
    private static VBox vboxRight;
    private static Button MCbutton;
    private static Button Selectimgbutton;
    private static Button GUIbutton;
    private static ImageView startQuiz;

    @Override
    public void start(Stage primaryStage) {
        //Set up the different panes
        BorderPane rootpane = new BorderPane();
        titlegrid = new GridPane();
        centergrid = new GridPane();
        rootpane.setTop(titlegrid);
        rootpane.setCenter(centergrid);

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

        vboxLeft = new VBox();
        vboxLeft.setPadding(new Insets(15, 12, 15, 20));
        vboxLeft.setSpacing(10);
        vboxLeft.setAlignment(Pos.BOTTOM_LEFT);

        vboxRight = new VBox();
        vboxRight.setPadding(new Insets(15, 20, 30, 12));
        vboxRight.setSpacing(10);
        vboxRight.setAlignment(Pos.BOTTOM_RIGHT);

        //Make the tutor button
        Image tutorButton = new Image("file:src/images/Tutor.png");
        tutor = new ImageView();
        tutor.setImage(tutorButton);
        tutor.setFitWidth(300);
        tutor.setPreserveRatio(true);
        tutor.setSmooth(true);
        tutor.setCache(true);
        tutor.setId("tutorButton");

        //Make the exit button
        Image exitButton = new Image("file:src/images/Exit.png");
        exit = new ImageView();
        exit.setImage(exitButton);
        exit.setFitWidth(300);
        exit.setPreserveRatio(true);
        exit.setSmooth(true);
        exit.setCache(true);
        exit.setId("exitButton");

        //Make the start quiz button
        Image startQuizButton = new Image("file:src/images/Start_quiz.png");
        startQuiz = new ImageView(startQuizButton);
        startQuiz.setFitWidth(300);
        startQuiz.setPreserveRatio(true);
        startQuiz.setSmooth(true);
        startQuiz.setCache(true);
        startQuiz.setId("startQuizButton");

        rootpane.setLeft(vboxLeft);
        rootpane.setRight(vboxRight);

        tutor.setOnMouseClicked(e -> {Admin.start(primaryStage);});

        exit.setOnMouseClicked(e -> {primaryStage.close();});


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
            //Set other CSS file
            scene.getStylesheets().clear();
            scene.getStylesheets().add("file:src/stylesheets/MCquestions.css");
            //Ask the questions
            levelmenu("mc");

        });

        Selectimgbutton.setOnAction(e -> {
            //Change the title
            titlegrid.setAlignment(Pos.TOP_LEFT);
            title.setText("Select Image Questions");
            //Remove the second logo
            //Remove buttons
            vboxLeft.getChildren().clear();
            vboxRight.getChildren().clear();
            titlegrid.getChildren().remove(logov2);
            //Set other CSS file
            scene.getStylesheets().clear();
            scene.getStylesheets().add("file:src/stylesheets/SelectQuestions.css");

            //Ask the questions
            levelmenu("image");
        });

        GUIbutton.setOnAction(e -> {
            //Change the title
            titlegrid.setAlignment(Pos.TOP_LEFT);
            title.setText("GUI Elements Questions");
            //Remove the second logo
            titlegrid.getChildren().remove(logov2);
            //Remove buttons
            vboxLeft.getChildren().clear();
            vboxRight.getChildren().clear();
            //Set other CSS file
            scene.getStylesheets().clear();
            scene.getStylesheets().add("file:src/stylesheets/GUIQuestions.css");

            //Ask the questions
            levelmenu("gui");
        });

        //Set the scene and size of the stage
        scene.getStylesheets().add("file:src/stylesheets/start_menu.css");
        primaryStage.setScene(scene);
        primaryStage.setHeight(720);
        primaryStage.setWidth(1280);

        //Display the menu
        display();

        //Fullscreen as default
        primaryStage.setFullScreen(true);

        //Make an icon for the program
        primaryStage.getIcons().add(new Image("file:src/images/logo.png"));

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
        vboxLeft.getChildren().add(exit);
        vboxRight.getChildren().add(tutor);
    }

    private static void levelmenu (String questiontype) {
        centergrid.getChildren().clear();
        Text chooseDifficulty = new Text("Select you difficulty");
        chooseDifficulty.setStyle("-fx-font-size: 38px;");
        Text levelText = new Text("      Easy      ");
        levelText.setStyle("-fx-font-size: 28px;");
        HBox hbox = new HBox();
        hbox.setAlignment(Pos.CENTER);

        Button lower = new Button("<");
        Button higher = new Button(">");

        hbox.getChildren().addAll(lower, levelText, higher);

        lower.setOnAction(e -> {
            switch(levelText.getText()) {
                case "      Easy      ":
                    levelText.setText("   Difficult   ");
                    break;
                case "   Average   ":
                    levelText.setText("      Easy      ");
                    break;
                case "   Difficult   ":
                    levelText.setText("   Average   ");
                    break;
            }
        });

        higher.setOnAction(e -> {
            switch (levelText.getText()) {
                case "      Easy      ":
                    levelText.setText("   Average   ");
                    break;
                case "   Average   ":
                    levelText.setText("   Difficult   ");
                    break;
                case "   Difficult   ":
                    levelText.setText("      Easy      ");
                    break;
            }
        });

        startQuiz.setOnMouseClicked(e -> {
            switch (levelText.getText()) {
                case "      Easy      ":
                    //Remove buttons
                    vboxLeft.getChildren().clear();
                    vboxRight.getChildren().clear();
                    goToQuestions(questiontype, 1);
                    break;
                case "   Average   ":
                    //Remove buttons
                    vboxLeft.getChildren().clear();
                    vboxRight.getChildren().clear();
                    goToQuestions(questiontype, 2);
                    break;
                case "   Difficult   ":
                    //Remove buttons
                    vboxLeft.getChildren().clear();
                    vboxRight.getChildren().clear();
                    goToQuestions(questiontype, 3);
                    break;
            }
        });

        centergrid.add(chooseDifficulty, 0, 0);
        centergrid.add(hbox, 0, 1);
        centergrid.add(startQuiz, 0, 3);
    }

    private static void goToQuestions(String type, int level) {
        switch (type) {
            case "mc":
                MCQuestion.askQuestions(centergrid, vboxLeft, level);
                break;
            case "image":
                SelectQuestion.askQuestions(centergrid, vboxLeft, level);
                break;
            case "gui":
                GUIQuestion.askQuestions(centergrid, level);
                break;
        }
    }

}
