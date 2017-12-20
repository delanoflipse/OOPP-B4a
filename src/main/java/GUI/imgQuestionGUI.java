package GUI;

import GUI.AlertBox;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class imgQuestionGUI extends Application{

    Stage window;
    Button nextQuestion;
    Label question;
    Image questionImg;
    ImageView questionImgV;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        window = primaryStage;
        window.setTitle("title");

        question = new Label("Click me!");
        questionImg = new Image("file:src/images/knul.jpg");
        questionImgV = new ImageView();
        questionImgV.setImage(questionImg);
        questionImgV.setFitWidth(500);
        questionImgV.setFitHeight(500);
        questionImgV.setPreserveRatio(false);
        questionImgV.setSmooth(true);
        questionImgV.setCache(true);
        questionImgV.setOnMouseClicked(e -> AlertBox.Display("Nice!", "U did it, great job!!!"));

        GridPane layout = new GridPane();
        layout.setAlignment(Pos.CENTER_LEFT);
        layout.setHgap(30);
        layout.setVgap(10);
        layout.setPadding(new Insets(25, 25, 25, 25));
        layout.add(questionImgV, 0, 0);
        layout.add(question, 0, 1);
        Scene question1 = new Scene(layout, 1000, 1000);
        window.setScene(question1);
        window.show();
    }
}
