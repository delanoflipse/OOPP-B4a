package GUI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;

public class UI extends Application {
    public static Pane root;
    public static Stage stage;
    public static Scene scene;

    public static UIState state;

    public static void setTitle(String title) {
        stage.setTitle(title);
    }

    public static void setCSS(String file) {
        scene.getStylesheets().add("file:src/stylesheets/" + file);
    }

    @Override
    public void start(Stage primaryStage) {
        // load basics filex
        state = new UIState();
        stage = primaryStage;
        root = new StackPane();
        scene = new Scene(root, 1280, 720);
        stage.setScene(scene);
        primaryStage.getIcons().add(new Image("file:src/images/logo.png"));

        // go to starting scene
        goToScene("welcome");

        primaryStage.show();
    }

    public static void goToScene(String sceneName) {
        try {
            // clear
            clearScene();
            clearCSS();
            setCSS("base.css");

            // load scene
            FXMLLoader loader = new FXMLLoader(UI.class.getResource("/scenes/" + sceneName + ".fxml"));
            Pane rt = (Pane) loader.load();

            // set variables
            scene.setRoot(rt);
            root = rt;

            // setup controller
//            UIScene controller = (UIScene) loader.getController();
//            controller.setup();
        } catch (IOException e) {
            System.out.println(e.getCause());
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println(e.getCause());
            System.out.println(e.getMessage());
        }
    }

    public static void clearScene() {
        root.getChildren().clear();
    }

    public static void clearCSS() {
        scene.getStylesheets().clear();
    }
}
