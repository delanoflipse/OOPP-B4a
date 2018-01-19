package GUI;

import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;


public class AddSelectQuestion extends UIScene implements Initializable {

    @FXML
    private ImageView logoImage1, logoImage2;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // set image
        Image image = new Image("file:src/images/logo.png");
        logoImage1.setImage(image);
        logoImage2.setImage(image);
    }

    private static GridPane centergrid;
    private static Image image;
    private static ImageView imagev;
    private static Button cancel;
    private static Rectangle selected;
    //Coordinates of the rectangle
    private static double ltX;
    private static double ltY;
    private static double rbX;
    private static double rbY;
    private static Button savebut;
    private static TextField questionField;
    private static ArrayList<RadioButton> levelbuttons = new ArrayList<>();
    private static File file;
    private static double imgratio;

    public static void AddQuestion (Stage stage, GridPane grid) {
        centergrid = grid;
        //Empty the centergrid
        centergrid.getChildren().clear();
        //Set alignment
        centergrid.setAlignment(Pos.TOP_LEFT);

        //Make and configure the filechooser
        final FileChooser imageChooser = new FileChooser();
        imageChooser.setTitle("Choose Image");
        imageChooser.setInitialDirectory(
                new File(System.getProperty("user.home"))
        );
        imageChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                new FileChooser.ExtensionFilter("PNG", "*.png")
        );

        //Add intro text
        Text intro = new Text("Here you can add a question where the user can select a certain point at the image as answer\nFirst, please select an image from your computer");

        //make cancel button
        cancel = new Button("Cancel");
//        cancel.setOnAction(e -> Admin.display());
        centergrid.add(cancel, 3, 22);

        //Make button for checking whether the image is correct
        Button gotoconfigure = new Button("Yes, continue");
        gotoconfigure.setOnAction(e -> ConfigureQuestion());

        //Make Button for opening an image
        Button openImage = new Button("Choose Image...");
        openImage.setOnAction(e -> {
            file = imageChooser.showOpenDialog(stage);
            if (file != null) {
                //Load image and display it
               image = new Image("file:"+file.getAbsolutePath());
               imagev = new ImageView(image);
               imagev.setFitHeight(centergrid.getHeight()*0.5);
               imagev.setPreserveRatio(true);
               imagev.setSmooth(true);
               imagev.setCache(true);
               centergrid.add(imagev, 2, 21, 2, 1);
               //remove this button and add new one
               centergrid.getChildren().remove(openImage);
               centergrid.add(gotoconfigure, 2, 22);
            }
        });

        //Add Text and Button to grid
        centergrid.add(intro, 2, 20, 2, 1);
        centergrid.add(openImage, 2, 22);
    }

    private static void ConfigureQuestion() {
        //clear centergrid
        centergrid.getChildren().clear();

        //Make introtext
        Text intro = new Text("Fill in all the boxes and select a part of the image.");
        centergrid.add(intro, 2, 20, 2, 1);

        //Make questionField and label
        questionField = new TextField();
        questionField.setMaxWidth(500);
        Label questionLabel = new Label("Question:");
        centergrid.add(questionField, 2, 21, 2, 1);
        centergrid.add(questionLabel, 1, 21);

        //Add the cancel button
        centergrid.add(cancel, 3, 27);

        //Add buttons for the level of the question
        Label levellabel = new Label("Level:");
        RadioButton level1button = new RadioButton("1");
        RadioButton level2button = new RadioButton("2");
        RadioButton level3button = new RadioButton("3");
        ToggleGroup levelgroup = new ToggleGroup();
        level1button.setToggleGroup(levelgroup);
        level2button.setToggleGroup(levelgroup);
        level3button.setToggleGroup(levelgroup);
        levelbuttons.add(level1button);
        levelbuttons.add(level2button);
        levelbuttons.add(level3button);
        centergrid.add(levellabel, 1, 22);
        centergrid.add(level1button, 2, 22);
        centergrid.add(level2button, 2, 23);
        centergrid.add(level3button, 2, 24);

        //Image is changed by this ratio (will be used later)
        imgratio = image.getHeight()/imagev.getFitHeight();
        //Make a clickable pane as big as the image
        Pane imagepane = new Pane();
        imagepane.setPrefHeight(imagev.getFitHeight());
        imagepane.setPrefWidth(image.getWidth()*imgratio);
        //Show the pane
        centergrid.add(imagepane, 2, 26, 2, 1);
        //Add the image to the pane
        imagepane.getChildren().add(imagev);
        //Set position to top left
        imagev.relocate(0,0);

        //Get some text above and in front of the image
        Label imageLabel = new Label("Chosen Image:");
        Text imageText = new Text("Please select the part of the image that is the correct answer.");
        centergrid.add(imageLabel, 1, 26);
        centergrid.add(imageText, 2, 25, 2, 1);

        //Make rectangle to show which part is selected
        selected = new Rectangle();
        //Add the rectangle to the imagepane
        imagepane.getChildren().add(selected);

        //Set what should be done when someone clicks the image and moves the mouse
        imagev.setOnMousePressed(e -> beginDrag(e));

        imagev.setOnMouseDragged(e -> setCoordinates(e.getX(), e.getY()));

        imagev.setOnMouseReleased(e -> endDrag(e));
        //If the rectangle is clicked the drag should start over again as well
        selected.setOnMousePressed(e -> beginDrag(e));

        //At the beginning the rectangle is not visible
        selected.setOpacity(0.5);
        selected.setHeight(0);
        selected.setWidth(0);

        //Make a button that will save the question
        savebut = new Button("Save Question");
        savebut.setOnAction(e -> {
            if (questionField.getText()!=null) {
                save();
            }
        });
        //Starts disabled
        savebut.setDisable(true);

        centergrid.add(savebut, 2, 27);
    }

    private static void setBeginCoordinates(double X, double Y) {
        //Read and set the coordinates at the beginning
        ltX = X;
        ltY = Y;
    }

    private static void setCoordinates(double X, double Y) {
        /**System.out.println("X: "+X);
        System.out.println("Y: "+Y);**/
        //If the mouse is in the image
        if (X >= 0.0 && X <= imagev.getFitHeight()/image.getHeight()*image.getWidth()) {
            //Set the rbX to the current value
            rbX = X;
        }
        //Otherwise set is to the boundary
        else {
            if (X <= 0) {
                rbX = 0.0;
                X = 0;
            }
            if (X > imagev.getFitHeight()/image.getHeight()*image.getWidth()) {
                rbX = imagev.getFitHeight()/image.getHeight()*image.getWidth();
            }
        }
        //Set the width (and position) of the rectangle
        if (rbX - ltX < 0) {
            selected.setX(X);
            selected.setWidth(ltX - rbX);
        } else {
            selected.setWidth(rbX - ltX);
        }
        //Same things for the Y value
        if (Y >= 0 && Y <= imagev.getFitHeight()) {
            rbY = Y;
        } else {
            if (Y <= 0) {
                rbY = 0.0;
                Y = 0;
            }
            if (Y > imagev.getFitHeight()) {
                rbY = imagev.getFitHeight();
            }
        }

        if (rbY - ltY < 0) {
            selected.setY(Y);
            selected.setHeight(ltY - rbY);
        } else {
            selected.setHeight(rbY - ltY);
        }
    }

    private static void beginDrag(MouseEvent e) {
        //At the start of a drag set rectangle size to 0 and set coordinates
        //Also set the color back
        selected.setX(e.getX());
        selected.setY(e.getY());
        selected.setWidth(0);
        selected.setHeight(0);
        setBeginCoordinates(e.getX(), e.getY());
        savebut.setDisable(true);
        selected.setFill(Color.SKYBLUE);
    }

    private static void endDrag(MouseEvent e) {
        //If there is a rectangle
        if (selected.getWidth() >= 5 && selected.getHeight() >= 5) {
            //Enable the submit button and make the color a bit darker
            savebut.setDisable(false);
            selected.setFill(Color.DEEPSKYBLUE);
            /**System.out.println("X: "+e.getX());
             System.out.println("Y: "+e.getY());**/
        }
    }

    private static void save() {
        //Read question
        String question = questionField.getText();

        //Read level
        int level = 0;
        int i=1;
        for (RadioButton button : levelbuttons) {
            if (button.isSelected()) {
                level = i;
            }
            i++;
        }

        //Read filename of image (the read file)
        String filename = file.getName();
        //Save file in the program folder
        //Get extension
        String extension = "";
        i = filename.lastIndexOf('.');
        if (i > 0) {
            extension = filename.substring(i+1);
        }
        filename = filename.substring(0,i);
        //Get the correct path
        String path = "src/images/"+filename+"."+extension;
        //Check whether there exists a file with that name
        i=0;
        File f = new File(path);
        int filenamelength = filename.length();
        //If so change the name
        while (f.exists() && !f.isDirectory()) {
            filename = filename.substring(0,filenamelength) + i;
            path = "src/images/"+filename+"."+extension;
            f = new File(path);
            i++;
        }
        //Make the new file for the image
        File outputFile = new File(path);
        BufferedImage bImage = SwingFXUtils.fromFXImage(image, null);
        try {
            //Write the image to the new file
            ImageIO.write(bImage, extension , outputFile);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        //Read the coordinates of the left top and right bottom of the selected part
        //And convert them to the right size for the image
        double correctltX = imgratio*selected.getX();
        double correctltY = imgratio*selected.getY();
        double correctrbX = imgratio*(selected.getX()+selected.getWidth());
        double correctrbY = imgratio*(selected.getY()+selected.getHeight());

        try {
            //We add new data at the end of the file
            PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter("db.data", true)));
            writer.print("\n");
            //Write all the data
            writer.println("#Question Added by the Add Clickable Image UI");
            writer.println("type: clickable");
            writer.println("level:" + level);
            writer.println("image:" + filename + "." + extension);
            writer.println("question: " + question);
            writer.println("topleft: "+((Double) correctltX).intValue()+","+((Double) correctltY).intValue());
            writer.println("bottomright: "+((Double) correctrbX).intValue()+","+((Double) correctrbY).intValue());
            //Close the writer
            writer.close();
            //Go back to the start menu
//            Admin.display();
        }
        catch(IOException e) {System.out.println(e.getMessage());}
    }

}
