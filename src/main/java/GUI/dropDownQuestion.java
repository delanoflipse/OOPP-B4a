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
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.scene.control.ChoiceBox;

public class dropDownQuestion extends Application{

    Stage window;
    Button submit;
    Label question;
    ChoiceBox<String> choiceBox;
    Label h1;
    Label info;
    Label navigation;
    StackPane root = new StackPane();
    Image logo = new Image("file:src/images/logo.png");
    ImageView logov = new ImageView();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        window = primaryStage;
        window.setTitle("title");

        choiceBox = new ChoiceBox<>();
        choiceBox.getItems().addAll("Homepage", "Parking", "Restaurant", "Staff", "Opening hours");
        choiceBox.setValue("Homepage");

        h1 = new Label("<b>Welcome to our site!</b>");
        info = new Label("lorem ipsum hihaho flasjdlkfjsdlkfjsdlfjlskdjflksajflsdflsdflksdjlkfsd");
        navigation = new Label("Where do you want to go?");
        question = new Label("Question: At what page do you find the parking information?");
        submit = new Button("Submit");
        submit.setOnMouseClicked(e -> {
            if (choiceBox.getValue() == "Parking") {
                AlertBox.Display("Nice!", "U did it, great job!!!");
            } else {
                AlertBox.Display("Aaaw", "That's incorrect, sorry!");
            }
        });

        GridPane titlegrid = new GridPane();
        logov.setImage(logo);
        logov.setFitWidth(100);
        logov.setPreserveRatio(true);
        logov.setSmooth(true);
        logov.setCache(true);
        titlegrid.setHgap(30);
        titlegrid.setVgap(10);
        titlegrid.setPadding(new Insets(25, 25, 25, 25));
        titlegrid.add(logov, 0, 0);

        Label caption = new Label("Question #");
        caption.setFont(new Font("Arial", 30));
        titlegrid.add(caption, 1, 0);

        GridPane layout = new GridPane();
        layout.setAlignment(Pos.CENTER_LEFT);
        layout.setHgap(30);
        layout.setVgap(10);
        layout.setPadding(new Insets(25, 25, 25, 25));
        layout.add(h1, 0, 0);
        layout.add(info, 0, 1);
        layout.add(navigation, 1, 0);
        layout.add(choiceBox, 1, 1);
        layout.add(question, 0, 2);
        layout.add(submit, 0, 3);
        root.getChildren().addAll(titlegrid, layout);
        Scene question1 = new Scene(root, 1920, 1000);
        window.setScene(question1);
        window.show();
    }
}
