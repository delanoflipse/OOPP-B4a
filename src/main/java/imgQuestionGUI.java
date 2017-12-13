import javafx.application.Application;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

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

        question = new Label("Click my eyes");
        questionImg = new Image("file:///C:/Documents/knul.jpg");
        questionImgV = new ImageView();
        questionImgV.setImage(questionImg);
        questionImgV.setFitWidth(500);
        questionImgV.setFitHeight(500);
        questionImgV.setPreserveRatio(false);
        questionImgV.setSmooth(true);
        questionImgV.setCache(true);
        questionImgV.setOnMouseClicked(e -> AlertBox.Display("Nice!", "U did it, great job!!!"));

        StackPane layout = new StackPane();
        layout.getChildren().addAll(question, questionImgV);
        Scene question1 = new Scene(layout, 300, 250);
        window.setScene(question1);
        window.show();
    }
}
