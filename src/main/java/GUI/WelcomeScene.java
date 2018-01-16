package GUI;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

public class WelcomeScene extends UIScene {
    private static Text welcomeText, labelText;
    private static TextField textInput;
    private static Button submitButton;

    public void render(UI gui, UIContext context) {
        gui.setTitle("Stichting Lezen en Schrijven - Practice Program");
        gui.setCSS("base.css");

        welcomeText = new Text("Welcome!");
        welcomeText.setId("title");

        if (context.has("test")) {
            welcomeText.setText("Welcome, " + (String) context.get("test") + "!");
        }

        labelText = new Text("Please fill in your name:");
        labelText.setId("label");

        textInput = new TextField();
        textInput.setPrefWidth(250);

        submitButton = new Button("Continue");
        submitButton.setMinSize(60, 40);
        submitButton.setId("btn");

        submitButton.setOnAction(e -> {
//            UI.goToScene(new StartMenu(), new UIContext()
//                    .set("test", textInput.getText())
//            );
        });

        GridPane pane = new GridPane();
        pane.setAlignment(Pos.CENTER);

        pane.setHgap(30);
        pane.setVgap(10);
        pane.setPadding(new Insets(25, 25, 25, 25));

        pane.add(welcomeText, 0, 0);
        pane.add(labelText, 0, 1);
        pane.add(textInput, 0, 2);
        pane.add(submitButton, 0, 3);

        gui.root.getChildren().add(pane);
    }
}
