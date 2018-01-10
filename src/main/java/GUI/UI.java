package GUI;

import database.Database;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class UI extends Application {
    StackPane root;
    Stage stage;
    Scene scene;

    UIState state;

    public void setTitle(String title) {
        stage.setTitle(title);
    }

    public void setCSS(String file) {
        scene.getStylesheets().add("file:src/stylesheets/" + file);
    }

    @Override
    public void start(Stage primaryStage) {
        state = new UIState();
        
        stage = primaryStage;
        root = new StackPane();
        scene = new Scene(root, 1280, 720);
        stage.setScene(scene);

        goToScene(new WelcomeScene(), null);
        primaryStage.show();
    }

    public void goToScene(UIScene guiscene, UIContext context) {
        root.getChildren().clear();
        scene.getStylesheets().clear();

        guiscene.render(this, context == null ? new UIContext() : context);
    }
}
