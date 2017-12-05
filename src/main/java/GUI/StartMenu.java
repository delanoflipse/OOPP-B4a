package GUI;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class StartMenu extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        StackPane rootpane = new StackPane();
        GridPane titlegrid = new GridPane();
        GridPane centergrid = new GridPane();
        rootpane.getChildren().addAll(titlegrid, centergrid);

        primaryStage.setTitle("Stichting Lezen en Schrijven - Practice Program");
        titlegrid.setAlignment(Pos.TOP_CENTER);
        titlegrid.setHgap(30);
        titlegrid.setVgap(10);
        titlegrid.setPadding(new Insets(25, 25, 25, 25));
        titlegrid.setGridLinesVisible(true);

        Image logo = new Image("file:///E:/Documents/TU/OOPP-B4a/src/main/java/GUI/logo.png");
        ImageView logov = new ImageView();
        logov.setImage(logo);
        logov.setFitWidth(100);
        logov.setPreserveRatio(true);
        logov.setSmooth(true);
        logov.setCache(true);
        titlegrid.add(logov, 0,0);
        ImageView logov2 = new ImageView();
        logov2.setImage(logo);
        logov2.setFitWidth(100);
        logov2.setPreserveRatio(true);
        logov2.setSmooth(true);
        logov2.setCache(true);
        titlegrid.add(logov2, 2, 0);

        Text title = new Text("Practice Program");
        title.setId("Title");
        titlegrid.add(title, 1, 0);

        centergrid.setAlignment(Pos.CENTER);
        centergrid.setHgap(30);
        centergrid.setVgap(10);
        centergrid.setPadding(new Insets(25, 25, 25, 25));
        centergrid.setGridLinesVisible(true);

        Image question1 = new Image("file:///E:/Documents/TU/OOPP-B4a/src/main/java/GUI/logo.png");
        ImageView question1v = new ImageView();
        question1v.setImage(question1);
        question1v.setFitWidth(350);
        question1v.setFitHeight(350);
        question1v.setSmooth(true);
        question1v.setCache(true);
        centergrid.add(question1v, 0, 0);

        Scene scene = new Scene(rootpane, 1280, 720);
        scene.getStylesheets().add("file:///E:/Documents/TU/OOPP-B4a/src/main/java/GUI/start_menu.css");
        primaryStage.setScene(scene);

        primaryStage.show();

    }
}
