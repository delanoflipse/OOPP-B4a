package GUI;

import database.Database;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javafx.event.ActionEvent;
import java.net.URL;
import java.util.ResourceBundle;

public class StartMenu extends UIScene implements Initializable {

    @FXML private ImageView logoImage1;
    @FXML private ImageView logoImage2;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // set images
        Image image = new Image("file:src/images/logo.png");
        logoImage1.setImage(image);
        logoImage2.setImage(image);
    }

    @Override
    public void setup() {
        //Set the title of the stage
        UI.setTitle("Stichting Lezen en Schrijven - Practice Program");
        UI.setCSS("start_menu.css");

        //Get the questions from the database
        Database.loadDatabase();
    }

    @FXML
    protected void handleMCButton(ActionEvent event) {
        // setup context
        UI.state.setContext(
                new UIContext()
                    .set("questions", Database.getQuestionsForLevel(1, "TextQuestion"))
                    .set("index", 0)
        );

        // go to scene
        UI.goToScene("mcquestions");
    }

    @FXML
    protected void handleIQButton(ActionEvent event) {
        // setup context
        UI.state.setContext(
                new UIContext()
                        .set("questions", Database.getQuestionsForLevel(1, "ClickQuestion"))
                        .set("index", 0)
        );

        // go to scene
        UI.goToScene("imagequestions");
    }

}
