package GUI;

import database.Database;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javafx.event.ActionEvent;
import javafx.scene.text.Text;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

public class StartMenu extends UIScene implements Initializable {

    @FXML private Text userTitle;

    @FXML private ImageView logoImage1;
    @FXML private ImageView logoImage2;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // set images
        Image image = new Image("file:src/images/logo.png");
        logoImage1.setImage(image);
        logoImage2.setImage(image);

        //Set the title of the stage
        UI.setTitle("Stichting Lezen en Schrijven - Practice Program");
        UI.setCSS("startmenu.css");

        userTitle.setText("Welcome " + UI.state.user.name);
    }

    @FXML
    protected void handleMCButton(ActionEvent event) {
        // setup context
        UI.state.setContext(
                new UIContext()
                    .set("type", "TextQuestion")
        );

        // go to scene
        UI.goToScene("selection");
    }

    @FXML
    protected void handleIQButton(ActionEvent event) {
        // setup context
        UI.state.setContext(
                new UIContext()
                        .set("type", "ClickQuestion")
        );

        // go to scene
        UI.goToScene("selection");
    }
    @FXML
    protected void goToScores(ActionEvent event) {
        // go to scene
        UI.goToScene("scores");
    }

}
