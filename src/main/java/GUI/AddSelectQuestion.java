package GUI;

import database.ClickQuestion;
import database.Database;
import database.Position;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;


public class AddSelectQuestion extends UIScene implements Initializable {

    @FXML private ImageView logoImage1, imagev;
    @FXML private Rectangle selected;
    @FXML private TextField questionField, levelField;
    @FXML private Image image;
    @FXML private Button saveButton;
    @FXML private Label imgLabel;
    @FXML private Pane imgPane;

    private File file;
    private double beginX, beginY;
    private double endX, endY;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // set image
        Image image = new Image("file:src/images/logo.png");
        logoImage1.setImage(image);


    }

    @FXML
    protected void handleExit(ActionEvent e) {
        UI.goToScene("admin");
    }

    @FXML
    protected void handleSelectImage(ActionEvent e) {
        final FileChooser imageChooser = new FileChooser();

        imageChooser.setTitle("Please choose an image");

        imageChooser.setInitialDirectory(
                new File(System.getProperty("user.home"))
        );

        imageChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("All Images", "*.jpg", "*.jpeg", "*.JPG", "*.JPEG", "*.png", "*.PNG"),
                new FileChooser.ExtensionFilter("jpg", "*.jpg", "*.jpeg", "*.JPG", "*.JPEG"),
                new FileChooser.ExtensionFilter("png", "*.png", "*.PNG")
        );

        file = imageChooser.showOpenDialog(UI.stage);

        if (file != null) {
            //Load image and display it
            imgPane.setPrefHeight(500);
            image = new Image("file:" + file.getAbsolutePath());
            imagev.setFitHeight(500);
            imagev.setPreserveRatio(true);
            imagev.setImage(image);

            imgLabel.setVisible(true);

            selected.setX(0);
            selected.setY(0);
            selected.setWidth(0);
            selected.setHeight(0);
            selected.setVisible(true);
        }
    }

    @FXML
    protected void handleSave(ActionEvent e) {
        String question = questionField.getText();
        String leveltxt = levelField.getText();

        if (question.length() == 0 || leveltxt.length() == 0) {
            return;
        }
        int level;

        try {
            level = Integer.parseInt(leveltxt);
        } catch (NumberFormatException error) {
            return;
        }

        if (level < 1 || level > 3) {
            return;
        }

        double imgratio = image.getHeight()/imagev.getFitHeight();
        String filename = file.getName();
        //Save file in the program folder
        //Get extension
        String extension = "";
        int i = filename.lastIndexOf('.');
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
        } catch (IOException err) {
            System.out.println(err.getMessage());
        }

        double correctltX = imgratio * selected.getX();
        double correctltY = imgratio * selected.getY();
        double correctrbX = imgratio * (selected.getX() + selected.getWidth());
        double correctrbY = imgratio * (selected.getY() + selected.getHeight());

        ClickQuestion q = new ClickQuestion();
        q.topLeft = new Position(correctltX, correctltY);
        q.bottomRight = new Position(correctrbX, correctrbY);
        q.text = question;
        q.level = level;
        q.image = filename + "." + extension;

        Database.questions.add(q);

        try {
            PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter("db.data", true)));
            writer.println();
            //Write all the data
            writer.println("#Question Added by the Add Clickable Image UI");
            writer.println("type: clickable");
            writer.println("level:" + q.level);
            writer.println("image:" + q.image);
            writer.println("question: " + q.text);
            writer.println("topleft: " + q.topLeft.toString());
            writer.println("bottomright: " + q.bottomRight.toString());
            //Close the writer
            writer.close();
        } catch (IOException err) {
            System.out.println(err.getCause());
            System.out.println(err.getMessage());
        }

        UI.goToScene("admin");
    }

    @FXML
    private void beginDrag(MouseEvent e) {
        //At the start of a drag set rectangle size to 0 and set coordinates
        //Also set the color back
        selected.setX(e.getX());
        selected.setY(e.getY());
        selected.setWidth(0);
        selected.setHeight(0);
        setBeginCoordinates(e.getX(), e.getY());
        saveButton.setDisable(true);
    }

    private void setBeginCoordinates(double x, double y) {
        //Read and set the coordinates at the beginning
        beginX = x;
        beginY = y;
    }

    @FXML
    private void setCoordinates(MouseEvent e) {
        double x = e.getX(), y = e.getY();

        double newx = x, newy = x;

        //Set mouse in image
        if (x <= 0.0) x = 0.0;
        if (x >= image.getWidth()) x = image.getWidth();
        if (y <= 0.0) y = 0.0;
        if (y >= image.getHeight()) y = image.getHeight();

        endX = x;
        endY = y;

        if (endX < beginX) {
            selected.setX(endX);
            selected.setWidth(beginX - endX);
        } else {
            selected.setX(beginX);
            selected.setWidth(endX - beginX);
        }

        if (endY < beginY) {
            selected.setY(endY);
            selected.setHeight(beginY - endY);
        } else {
            selected.setY(beginY);
            selected.setHeight(endY - beginY);
        }


        if (selected.getWidth() >= 5 && selected.getHeight() >= 5) {
            selected.setFill(Color.DEEPSKYBLUE);
        } else {
            selected.setFill(Color.SKYBLUE);
        }
    }

    private double abs(double d) {
        return d < 0.0 ? -d : d;
    }

    @FXML
    private void endDrag(MouseEvent e) {
        saveButton.setDisable(false);
    }
/*

    private static void endDrag(MouseEvent e) {
        //If there is a rectangle
        if (selected.getWidth() >= 5 && selected.getHeight() >= 5) {
            //Enable the submit button and make the color a bit darker
            savebut.setDisable(false);
            selected.setFill(Color.DEEPSKYBLUE);
            System.out.println("X: "+e.getX());
             System.out.println("Y: "+e.getY());
        }{
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
*/
}
