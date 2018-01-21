package GUI;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

public abstract class UIScene {

    public void setup() {}

    public ImageView createImageView(String path) {
        Image img = new Image("file:src/images/" + path);
        ImageView iv = new ImageView();
        iv.setImage(img);
        iv.setFitWidth(100);
        iv.setPreserveRatio(true);
        iv.setSmooth(true);
        iv.setCache(true);

        return iv;
    }

    public Text createText() {
        return new Text();
    }

    protected void setButtonImage(Button btn, String img) {
        Image icon = new Image(img);
        ImageView imageView = new ImageView(icon);
        imageView.setFitWidth(24);
        imageView.setX(20);
        imageView.setFitHeight(24);
        imageView.setPreserveRatio(true);
        imageView.getStyleClass().add("graphic");
        btn.setGraphic(imageView);
    }
}
