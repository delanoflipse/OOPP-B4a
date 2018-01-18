package GUI;

import database.Database;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import user.UserData;

import java.net.URL;
import java.util.ResourceBundle;

public class WelcomeScene extends UIScene implements Initializable {
    @FXML private Text errorText;
    @FXML private TextField input;
    @FXML private ImageView logoImage;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        UI.setTitle("Stichting Lezen en Schrijven - Practice Program");
        UI.setCSS("base.css");

        // set image
        Image image = new Image("file:src/images/logo.png");
        logoImage.setImage(image);
    }

    @FXML
    protected void handleSubmit(ActionEvent event) {
        String value = input.getText();
        value = value.trim().replaceAll("[^A-Za-z]", "");

        if (value.length() == 0) {
            errorText.setText("Please fill in a valid name(only letters).");
            return;
        }

        for (UserData user : Database.users) {
            if (user.name.toLowerCase().equals(value)) {
                UI.state.user = user;
                System.out.println(user);
                break;
            }
        }

        if (UI.state.user == null) {
            UI.state.user = new UserData();
            UI.state.user.name = value;
            UI.state.user.filename = value.toLowerCase() + ".userdata";
        }

        System.out.println("Selected user " + UI.state.user.name);

        UI.goToScene("startmenu");
    }
}
