package GUI;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import tts.TextToSpeech;

public class StartMenu extends Application {

    private static ImageView logov;
    private static ImageView logov2;
    private static GridPane centergrid;
    private static GridPane titlegrid;
    private static Scene scene;
    private static Text title;
    private static Button MCbutton;
    private static Button Selectimgbutton;
    private static Button GUIbutton;
    public static String ttsfinal = "";
    public static TextToSpeech tts = new TextToSpeech();
    public static boolean playtts = true;
    @Override
    public void start(Stage primaryStage) {
        ttsfinal = "";
        playtts = true;
        //Set up the different panes
        StackPane rootpane = new StackPane();
        titlegrid = new GridPane();
        centergrid = new GridPane();
        rootpane.getChildren().addAll(titlegrid, centergrid);

        //Set the title of the stage
        primaryStage.setTitle("Stichting Lezen en Schrijven - Practice Program");
        //tts title
        tts.speak( "Reading and writing association .  Practice Program",false,playtts);//play only line of tts
        //Some settings for the grid at the top
        titlegrid.setHgap(30);
        titlegrid.setVgap(10);
        titlegrid.setPadding(new Insets(25, 25, 25, 25));

        //Load logos for the title and add the first logo
        //Second logo is added in display() because only the start menu has two logos
        Image logo = new Image("file:src/images/logo.png");
        logov = new ImageView();
        logov.setImage(logo);
        logov.setFitWidth(100);
        logov.setPreserveRatio(true);
        logov.setSmooth(true);
        logov.setCache(true);
        titlegrid.add(logov, 0, 0);
        logov2 = new ImageView();
        logov2.setImage(logo);
        logov2.setFitWidth(100);
        logov2.setPreserveRatio(true);
        logov2.setSmooth(true);
        logov2.setCache(true);

        //Make the title text and add it
        title = new Text();
        title.setId("Title");
        titlegrid.add(title, 1, 0);

        //some setup for the grid in the center
        centergrid.setHgap(30);
        centergrid.setVgap(10);
        centergrid.setPadding(new Insets(25, 25, 25, 25));

        //Make the button for MCQuestion
        MCbutton = new Button("Multiple Choiche Questions");
        MCbutton.setMinSize(300, 300);
        MCbutton.setId("MCbutton");
        MCbutton.setOnMouseEntered(e -> tts.speak("Multiple Choice Questions",false,playtts)); //ttsonmousenter multiple choicequestions
        //Make the button for the ImageQuestions
        Selectimgbutton = new Button("Select part of Image Questions");
        Selectimgbutton.setMinSize(300, 300);
        Selectimgbutton.setId("SelectIMGButton");
        Selectimgbutton.setOnMouseEntered(e -> tts.speak("Select Part of image questions",false,playtts));
        //Make button for the GUIQuestions
        GUIbutton = new Button("GUI Elements Question");
        GUIbutton.setMinSize(300, 300);
        GUIbutton.setId("GUIbutton");
        GUIbutton.setOnMouseEntered(e -> tts.speak("G U I Elements Question",false,playtts)); //tts gui question on mouse enter
        //Gridlines for debugging are off
        titlegrid.setGridLinesVisible(false);
        centergrid.setGridLinesVisible(false);

        //Make scene to display the panes in
        scene = new Scene(rootpane, 1280, 720);
      
        //Set actions for the buttons
        MCbutton.setOnAction(e -> {
            //Change the title
            titlegrid.setAlignment(Pos.TOP_LEFT);
            title.setText("Multiple Choiche Questions");
            //Remove the second logo
            titlegrid.getChildren().remove(logov2);
            //Ask the questions
            MCQuestion.askQuestions(centergrid);
            //Set other CSS file
            scene.getStylesheets().clear();
            scene.getStylesheets().add("file:src/stylesheets/MCquestions.css");
        });

        Selectimgbutton.setOnAction(e -> {
            //Change the title
            titlegrid.setAlignment(Pos.TOP_LEFT);
            title.setText("Select Image Questions");
            //Remove the second logo
            titlegrid.getChildren().remove(logov2);
            //Set other CSS file
            scene.getStylesheets().clear();
            //We dont have CSS for this question yet
            //scene.getStylesheets().add("file:src/stylesheets/");

            //Ask the questions
            GUI.SelectQuestion.askQuestions(centergrid);
        });

        GUIbutton.setOnAction(e -> {
            //Change the title
            titlegrid.setAlignment(Pos.TOP_LEFT);
            title.setText("GUI Elements Questions");
            //Remove the second logo
            titlegrid.getChildren().remove(logov2);
            //Set other CSS file
            scene.getStylesheets().clear();
            //We dont have CSS for this question yet
            //scene.getStylesheets().add("file:src/stylesheets/");

            //Ask the questions
            GUI.GUIQuestion.askQuestions(centergrid);
        });

        //Set the scene and size of the stage
        scene.getStylesheets().add("file:src/stylesheets/start_menu.css");
        primaryStage.setScene(scene);
        primaryStage.setHeight(720);
        primaryStage.setWidth(1280);

        //Display the menu
        display();

        //Show it all
        primaryStage.show();

    }

    public static void display(){
        //Set alignment titlegrid to center
        titlegrid.setAlignment(Pos.TOP_CENTER);

        //Change the title text
        title.setText("Practice Program");

        //Add the second logo
        titlegrid.add(logov2, 2, 0);

        //Set centergrid alignment
        centergrid.setAlignment(Pos.CENTER);

        //Set the CSS
        scene.getStylesheets().clear();
        scene.getStylesheets().add("file:src/stylesheets/start_menu.css");

        //Clear the centergrid and add the buttons
        centergrid.getChildren().clear();
        centergrid.add(MCbutton, 0, 0);
        centergrid.add(Selectimgbutton, 1, 0);
        centergrid.add(GUIbutton, 2, 0);
    }
    public void toggletts(boolean playtts,String ttsfinal){
        if (playtts) {
            playtts = false;
            tts.stopSpeaking();

        } else {
            playtts = true;
            tts.speak(ttsfinal,false,true);
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
