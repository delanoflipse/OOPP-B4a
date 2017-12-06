package GUI;

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
        titlegrid.add(logov, 0,0);
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

        Button MCbutton = new Button();
        MCbutton.setOnAction(e -> {
            askMCquestions(primaryStage);
        });
        MCbutton.setMinSize(300,300);
        centergrid.add(MCbutton,0,0);

        titlegrid.setGridLinesVisible(false);
        centergrid.setGridLinesVisible(false);

        Scene scene = new Scene(rootpane, 1280, 720);
        scene.getStylesheets().add("file:src/stylesheets/start_menu.css");
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    public static void askMCquestions(Stage primaryStage) {
        Database.loadDatabase();
        ArrayList<Question> questionlist = Database.getQuestionsForLevel(1);

        StackPane rootpane = new StackPane();
        GridPane titlegrid = new GridPane();
        GridPane centergrid = new GridPane();
        rootpane.getChildren().addAll(titlegrid, centergrid);

        primaryStage.setTitle("Stichting Lezen en Schrijven - Practice Program");
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
        titlegrid.add(logov, 0,0);

        Text title = new Text("Multiple Choiche Questions");
        title.setId("Title");
        titlegrid.add(title, 1, 0);

        centergrid.setAlignment(Pos.CENTER_LEFT);
        centergrid.setHgap(30);
        centergrid.setVgap(10);
        centergrid.setPadding(new Insets(25, 25, 25, 25));

        int i=1;
        for (Question question : questionlist) {
            TextQuestion q = (TextQuestion) question;
            ToggleGroup answergroup = new ToggleGroup();
            ArrayList<RadioButton> answerbuttons = new ArrayList<RadioButton>();

            Text qtitle = new Text("Question"+i);
            centergrid.add(qtitle, 0, 0);

            Text qtext = new Text(q.text);
            centergrid.add(qtext, 0, 1);
            for (Answer answer : q.answers){
                TextAnswer tanswer = (TextAnswer) answer;
                answerbuttons.add(new RadioButton(tanswer.text));
            }
            int j=1;
            for (RadioButton button: answerbuttons){
                button.setToggleGroup(answergroup);
                j++;
                centergrid.add(button, 0, j);
            }
            Button submit = new Button("Submit");
            final Text response = new Text();
            boolean clicked = false;
            submit.setOnAction( new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event){
                    int selected = answerbuttons.indexOf((RadioButton) answergroup.getSelectedToggle());
                    if (q.isCorrect(selected)) {
                        response.setFill(Color.DARKGREEN);
                        response.setText("That is correct. We'll go to the next question");
                        try {Thread.sleep(3000);}
                        catch (Exception exception) {
                         System.out.println("woops");
                        }

                    } else {
                        response.setFill(Color.FIREBRICK);
                        response.setText("That is incorrect. The answer was ... We'll go to the next question");
                        try {Thread.sleep(3000);}
                        catch (Exception exception) {
                         System.out.println("woops");
                        }
                    }
                }
            });
            centergrid.add(submit, 1, j+1);

            i++;

            Scene scene = new Scene(rootpane, 1280, 720);
            //scene.getStylesheets().add("file:///C:/Users/woute/Documents/Github/OOPP-B4a/src/main/java/GUI/start_menu.css");
            primaryStage.setScene(scene);
            primaryStage.show();

        }

    }

}
