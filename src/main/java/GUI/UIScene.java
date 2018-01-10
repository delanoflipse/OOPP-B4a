package GUI;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

public abstract class UIScene {

    public abstract void render(UI gui, UIContext context);

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
}
