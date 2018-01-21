package GUI;

import database.Database;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javafx.event.ActionEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;


public class StartMenu extends UIScene implements Initializable {

    @FXML private Text userTitle;
    @FXML private ImageView logoImage1;
    @FXML private ImageView logoImage2;

    @FXML private Button scoreBtn, returnBtn, ttsBtn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // set images
        Image image = new Image("file:src/images/logo.png");
        logoImage1.setImage(image);
        logoImage2.setImage(image);

        //Set the title of the stage
        UI.setTitle("Stichting Lezen en Schrijven - Practice Program");
        UI.setCSS("startmenu.css");

        setButtonImage(scoreBtn, "file:src/images/scores.png");
        setButtonImage(returnBtn, "file:src/images/arrowleft.png");
        setTTSbutton();

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

    @FXML
    protected void handleReturn(ActionEvent event) {
        // go to scene
        UI.goToScene("welcome");
    }

    @FXML
    protected void toggleTTS(MouseEvent event) {
        boolean val = UI.state.user.getBoolPreference("useTTS");
        UI.state.user.setPreference("useTTS", val ? "false" : "true");
        UI.state.user.save();
        setTTSbutton();
    }

    private void setTTSbutton() {
        if (UI.state.user.getBoolPreference("useTTS")) {
            ttsBtn.setText("Disable spoken text");
            setButtonImage(ttsBtn, "file:src/images/speakeron.png");
        } else {
            ttsBtn.setText("Use spoken text");
            setButtonImage(ttsBtn, "file:src/images/speakeroff.png");
        }
    }


    public void toggletts(boolean playtts,String ttsfinal){
        if (playtts) {
            playtts = false;
            //tts.stopSpeaking();

        } else {
            playtts = true;
            //tts.speak(ttsfinal,false,true);
        }};
    private static final String[] SCALES = {"", "thousand", "million", "billion", "trillion", "quadrillion", "quintillion", "sextillion"};
    private static final String[] SUBTWENTY = {"zero", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine", "ten",
            "eleven", "twelve", "thirteen", "fourteen", "fifteen", "sixteen", "seventeen", "eighteen", "nineteen"};
    private static final String[] DECADES = {"zero", "ten", "twenty", "thirty", "forty", "fifty", "sixty", "seventy", "eighty", "ninety"};

    /**
     * Convert any value from 0 to 999 inclusive, to a string.
     * @param value The value to convert.
     * @param and whether to use the word 'and' in the output.
     * @return a String representation of the value.
     */
    private static String tripleAsText(int value, boolean and) {
        if (value < 0 || value >= 1000) {
            throw new IllegalArgumentException("Illegal triple-value " + value);
        }

        if (value < SUBTWENTY.length) {
            return SUBTWENTY[value];
        }

        int subhun = value % 100;
        int hun = value / 100;
        StringBuilder sb = new StringBuilder(50);
        if (hun > 0) {
            sb.append(SUBTWENTY[hun]).append(" hundred");
        }
        if (subhun > 0) {
            if (hun > 0) {
                sb.append(and ? " and " : " ");
            }
            if (subhun < SUBTWENTY.length) {
                sb.append(SUBTWENTY[subhun]);
            } else {
                int tens = subhun / 10;
                int units = subhun % 10;
                if (tens > 0) {
                    sb.append(DECADES[tens]);
                }
                if (units > 0) {
                    sb.append(" ").append(SUBTWENTY[units]);
                }
            }
        }

        return sb.toString();
    }

    /**
     * Convert any long input value to a text representation
     * @param value The value to convert
     * @param useand true if you want to use the word 'and' in the text (eleven thousand and thirteen)
     * @param negname
     * @return
     */
    public static final String asText(long value, boolean useand, String negname) {
        if (value == 0) {
            return SUBTWENTY[0];
        }

        // break the value down in to sets of three digits (thousands).
        int[] thous = new int[SCALES.length];
        boolean neg = value < 0;
        // do not make negative numbers positive, to handle Long.MIN_VALUE
        int scale = 0;
        while (value != 0) {
            // use abs to convert thousand-groups to positive, if needed.
            thous[scale] = Math.abs((int)(value % 1000));
            value /= 1000;
            scale++;
        }

        StringBuilder sb = new StringBuilder(scale * 40);
        if (neg) {
            sb.append(negname).append(" ");
        }
        boolean first = true;
        while (--scale > 0) {
            if (!first) {
                sb.append(", ");
            }
            first = false;
            if (thous[scale] > 0) {
                sb.append(tripleAsText(thous[scale], useand)).append(" ").append(SCALES[scale]);
            }

        }

        if (!first && useand && thous[0] != 0) {
            sb.append(" and ");
        }
        sb.append(tripleAsText(thous[0], useand));

        return sb.toString();
    }


}
