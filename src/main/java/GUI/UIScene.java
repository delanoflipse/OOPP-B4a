package GUI;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import tts.ttshelper;

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

    protected void handleTTSbutton(Button btn) {
        setTTSbutton(btn);
        btn.setOnMouseClicked((MouseEvent e) -> {
            boolean val = UI.state.user.getBoolPreference("useTTS");
            UI.state.user.setPreference("useTTS", val ? "false" : "true");
            UI.state.user.save();
            ttshelper.toggle(!val);
            setTTSbutton(btn);
        });
    }

    private void setTTSbutton(Button btn) {
        if (UI.state.user.getBoolPreference("useTTS")) {
            btn.setText("Disable spoken text");
            setButtonImage(btn, "file:src/images/speakeron.png");
        } else {
            btn.setText("Use spoken text");
            setButtonImage(btn, "file:src/images/speakeroff.png");
        }
    }
}
