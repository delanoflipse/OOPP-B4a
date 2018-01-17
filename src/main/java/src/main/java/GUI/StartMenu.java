package src.main.java.GUI;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class StartMenu extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Stichting Lezen en Schrijven - Practise Program");
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        /**Image logo = new Image("/logo_lezenenschrijven.png");
        ImageView iv = new ImageView();
        iv.setImage(logo);
        iv.setFitWidth(100);
        iv.setPreserveRatio(true);
        iv.setSmooth(true);
        iv.setCache(true);
        grid.add(iv, 0,0);
        grid.add(iv, 3, 0);**/

        Text title = new Text("Practise Program");
        title.setFont(Font.font(26));
        grid.add(title, 1, 0);

        Scene startmenu = new Scene(grid, 1280, 720);
        primaryStage.setScene(startmenu);

        primaryStage.show();

    }
}
