package GUI;

import database.DropDownHead;
import database.Element;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AddGUIQuestion extends UIScene implements Initializable {

    @FXML private ImageView logoImage1;
    @FXML private TextField questionField;
    @FXML private TextField levelField;
    @FXML private TextField menuField;
    @FXML private TextField elementField;
    @FXML private ChoiceBox<DropDownHead> addElementChoice;
    @FXML private CheckBox correctBox;
    @FXML private HBox menus;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // set image
        Image image = new Image("file:src/images/logo.png");
        logoImage1.setImage(image);

    }

    @FXML
    private void handleAddMenu() {
        //Add the menu with the given name
        DropDownHead menu = new DropDownHead(menuField.getText());
        menuField.setText("");
        //MAke sure you can select it at element
        addElementChoice.getItems().add(menu);
        addElementChoice.setValue(menu);
        showMenus();
    }

    @FXML
    private void handleAddElement() {
        //Check whether a dropdownhead is selected
        if (addElementChoice.getValue() == null) {
            return;
        }
        //create new element
        Element element = new Element(elementField.getText());
        elementField.setText("");
        //Set correct to true if needed
        if (correctBox.isSelected()) {
            element.correct = true;
            correctBox.setSelected(false);
        }
        //Add element to the selected dropdownhead
        addElementChoice.getValue().getElements().add(element);
        //Show the menus with this new element
        showMenus();
    }

    @FXML
    private void handleSave() {
        try {

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

            //We add new data at the end of the file
            PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter("db.data", true)));
            writer.println("\n");

            //Write type, level and question
            writer.println("#Added by the Add GUI-Question GUI");
            writer.println("type: gui");
            writer.println("level: "+ level);
            writer.println("question: "+question);

            //Write every DropDownHead
            for (DropDownHead menu : addElementChoice.getItems()) {
                writer.println("menu: "+menu.toString());
                //Write every element of this menu
                for (Element element : menu.getElements()) {
                    //Write correct in front of it if needed
                    if (element.correct) {
                        writer.print("correct");
                    }
                    writer.println("element: "+element.toString());
                }
            }

            //Close the writer
            writer.close();

            //Go back to the startmenu
            addElementChoice.getItems().clear();
            questionField.setText("");
            //Admin.display();

            UI.goToScene("admin");

        }
        catch (IOException e) {
            System.out.println("Something went wrong:");
            System.out.println(e.getMessage());
        }
    }

    @FXML
    private void handleExit() {
        UI.goToScene("admin");
    }

    private void showMenus() {
        menus.getChildren().clear();
        menus.getChildren().add(new Label("Your Menus:"));
        for (DropDownHead menu : addElementChoice.getItems()) {
            //Make a choicebox
            ChoiceBox<Element> choiceBox = new ChoiceBox<>();
            //Add the name
            choiceBox.setValue(menu);
            choiceBox.getItems().add(menu);
            //Add the elements
            for (Element element : menu.getElements()) {
                choiceBox.getItems().add(element);
            }
            menus.getChildren().add(choiceBox);
        }
    }
}