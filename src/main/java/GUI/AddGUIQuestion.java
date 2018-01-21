package GUI;

import database.DropDownHead;
import database.Element;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class AddGUIQuestion {

    private static GridPane centergrid;
    private static TextField qfield;
    private static ArrayList<RadioButton> levelbuttons = new ArrayList<>();
    private static TextField menuField;
    private static TextField elementField;
    private static ChoiceBox<DropDownHead> addElementChoice = new ChoiceBox<>();
    private static CheckBox correctBox;

    public static void AddQuestion(GridPane grid) {
        //Prepare the grid
        centergrid = grid;
        centergrid.getChildren().clear();
        centergrid.setAlignment(Pos.CENTER_LEFT);

        //Display intro texts
        Text intro = new Text("Fill in the question and level");
        Text intro2 = new Text("Click 'new menu' to add a drop-down menu");
        Text intro3 = new Text("Click add element to add the element to the selected drop-down menu");
        centergrid.add(intro, 1, 0, 10, 1);
        centergrid.add(intro2, 1, 1, 10, 1);
        centergrid.add(intro3, 1, 2, 10, 1);

        //Add question field
        Label qlabel = new Label("Question:");
        qfield = new TextField();
        centergrid.add(qlabel, 0, 3);
        centergrid.add(qfield, 1, 3, 2, 1);

        //Add level buttons
        Label levellabel = new Label("Label:");
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
        centergrid.add(levellabel, 0, 4);
        centergrid.add(level1button, 1, 4);
        centergrid.add(level2button, 1, 5);
        centergrid.add(level3button, 1, 6);

        //Row 9 will be the menus
        Label currentMenusLabel = new Label("Your Menus:");
        centergrid.add(currentMenusLabel, 0, 9);

        //Add rw for adding a menu
        Label menulabel = new Label("Menu:");
        menuField = new TextField();
        Button addMenubtn = new Button("Add menu");
        addMenubtn.setOnAction(e -> addMenu());
        centergrid.add(menulabel, 0, 10);
        centergrid.add(menuField, 1, 10, 2, 1);
        centergrid.add(addMenubtn, 3, 10);

        //Same for adding element
        Label elementlabel = new Label("Element:");
        elementField = new TextField();
        Button addElementbtn = new Button("Add element");
        addElementbtn.setOnAction(e -> addElement());
        correctBox = new CheckBox("Correct?");
        centergrid.add(elementlabel, 0, 11);
        centergrid.add(addElementChoice, 1, 11);
        centergrid.add(elementField, 2, 11, 2, 1);
        centergrid.add(correctBox, 4, 11);
        centergrid.add(addElementbtn, 5, 11);

        //Add save button
        Button savebtn = new Button("Save Question");
        savebtn.setOnAction(e -> Save());
        centergrid.add(savebtn, 1, 12);

        //Add cencel button
        Button cancelbtn = new Button("Cancel");
        cancelbtn.setOnAction(e -> {
            addElementChoice.getItems().clear();
            qfield.setText("");
            //Admin.display();
        });
        centergrid.add(cancelbtn, 2, 12);
    }

    private static void addMenu() {
        //Add the menu with the given name
        DropDownHead menu = new DropDownHead(menuField.getText());
        menuField.setText("");
        //MAke sure you can select it at element
        addElementChoice.getItems().add(menu);
        addElementChoice.setValue(menu);
        showMenus();
    }

    private static void addElement() {
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

    private static void showMenus() {
        //i to now which column we should put the menu in
        int i=1;
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
            centergrid.add(choiceBox, i, 9);
            i++;
        }
    }

    private static void Save() {
        try {
            //Get the level from the radiobuttons
            int level = -1;
            int i=1;
            for (RadioButton button : levelbuttons) {
                if (button.isSelected()) {
                    level = i;
                }
                i++;
            }
            //If there was no level selected, stop
            if (level == -1) {
                return;
            }

            //Get the Question from the field
            String question = qfield.getText();
            //if it's empty again quit
            if (question == "") {
                return;
            }

            //We add new data at the end of the file
            PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter("db.data", true)));
            writer.println("\n");

            //Write type, level and question
            writer.println("#Added by the Add GUI-Question GUI");
            writer.println("type: gui");
            writer.println("level: "+1);
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
            qfield.setText("");
            //Admin.display();

        }
        catch (IOException e) {
            System.out.println("Something went wrong:");
            System.out.println(e.getMessage());
        }
    }

}