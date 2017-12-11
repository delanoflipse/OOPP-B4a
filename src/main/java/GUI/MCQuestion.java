package GUI;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import main.*;

import java.util.ArrayList;

public class MCQuestion {

    private static int i;
    private static int score;
    private static Text response;
    private static ArrayList<RadioButton> answerbuttons;
    private static ToggleGroup answergroup;
    private static GridPane centergrid;
    private static ArrayList<Question> questionlist;
    private static Button submit;
    private static Stage primaryStage;
    private static StackPane root;
    private static Scene startMenu;

    public static void askQuestions(Stage stage, Scene start){
        primaryStage = stage;
        startMenu = start;
        Database.loadDatabase();
        questionlist = Database.getQuestionsForLevel(1);
        score = 0;
        root = new StackPane();

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

        Text title = new Text("Multiple Choice Questions");
        title.setId("Title");
        titlegrid.add(title, 1, 0);

        root.getChildren().add(titlegrid);

        centergrid = new GridPane();
        centergrid.setAlignment(Pos.CENTER_LEFT);
        centergrid.setHgap(30);
        centergrid.setVgap(10);
        centergrid.setPadding(new Insets(25, 25, 25, 25));
        TextQuestion q = (TextQuestion) questionlist.get(0);
        answergroup = new ToggleGroup();
        answerbuttons = new ArrayList<RadioButton>();

        Text qtitle = new Text("Question 1");
        qtitle.setId("qtitle");
        centergrid.add(qtitle, 0, 0);

        Text qtext = new Text(q.text);
        qtext.setId("qtext");
        centergrid.add(qtext, 0, 1);
        for (Answer answer : q.answers) {
            TextAnswer tanswer = (TextAnswer) answer;
            answerbuttons.add(new RadioButton(tanswer.text));
        }
        int j = 1;
        for (RadioButton button : answerbuttons) {
            button.setToggleGroup(answergroup);
            j++;
            centergrid.add(button, 0, j);
        }
        response = new Text("");
        response.setId("responsetext");
        centergrid.add(response, 0, j + 1);

        i=0;
        Button next = new Button("Continue");

        submit = new Button("Submit");
        submit.setOnAction(e -> {
            if (answerbuttons.indexOf(answergroup.getSelectedToggle()) != -1) {
                int selected = answerbuttons.indexOf(answergroup.getSelectedToggle());
                if (questionlist.get(i).isCorrect(selected)) {
                    addScore();
                    response.setFill(Color.DARKGREEN);
                    response.setText("That is correct. Click continue to go to the next question");
                } else {
                    for (Answer answer : ((TextQuestion) questionlist.get(i)).answers) {
                        TextAnswer tanswer = (TextAnswer) answer;
                        if (tanswer.getCorrect()) {
                            final String answertext = tanswer.text;
                            response.setFill(Color.FIREBRICK);
                            response.setText("That is incorrect. The answer was:\n" + answertext + "\nClick continue to go to the next question");
                        }
                    }
                }
                final int col = centergrid.getColumnIndex(submit);
                final int row = centergrid.getRowIndex(submit);
                centergrid.add(next, col+1, row);
                centergrid.getChildren().remove(submit);
            }
        });
        centergrid.add(submit, 0, j+1);

        next.setOnAction(e -> showNextQuestion());

        root.getChildren().add(centergrid);
        Scene scene = new Scene(root, primaryStage.getWidth(), primaryStage.getHeight());
        scene.getStylesheets().add("file:src/stylesheets/MCquestions.css");
        primaryStage.setScene(scene);

    }

    private static void addScore(){
        score++;
    }

    private static void showNextQuestion(){
        i++;
        if (i >= questionlist.size()){
            done(centergrid, questionlist, primaryStage);
            return;
        }

        centergrid.getChildren().clear();
        TextQuestion q = (TextQuestion) questionlist.get(i);
        Text qtitle = new Text("Question " + (i+1));
        qtitle.setId("qtitle");
        centergrid.add(qtitle, 0, 0);

        Text qtext = new Text(q.text);
        qtext.setId("qtext");
        centergrid.add(qtext, 0, 1);
        answerbuttons.clear();
        for (Answer answer : q.answers) {
            TextAnswer tanswer = (TextAnswer) answer;
            answerbuttons.add(new RadioButton(tanswer.text));
        }
        int j = 1;
        for (RadioButton button : answerbuttons) {
            button.setToggleGroup(answergroup);
            j++;
            centergrid.add(button, 0, j);
        }
        response.setText("");
        centergrid.add(response, 0, j + 1);
        centergrid.add(submit, 0, j+1);
    }

    private static void done(GridPane centergrid, ArrayList<Question> questionlist, Stage primaryStage){
        Text end = new Text("That were all the question, well done!");
        Text endscore = new Text("Your score is: " + score + " out of " + questionlist.size());
        Text back = new Text("You will be redirected to the startscreen, when you click exit.");

        end.setId("end");
        endscore.setId("end");
        back.setId("end");

        centergrid.getChildren().clear();
        centergrid.add(end, 0, 0);
        centergrid.add(endscore, 0, 1);
        centergrid.add(back, 0, 2);

        Button exit = new Button("Exit");
        exit.setOnAction(e -> primaryStage.setScene(startMenu));

        HBox exitbox = new HBox();
        exitbox.setAlignment(Pos.CENTER);
        exitbox.getChildren().add(exit);

        centergrid.add(exitbox ,0, 3);


    }

}
