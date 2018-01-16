package GUI;

import database.DropDownHead;
import database.Element;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

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
        centergrid = grid;
        centergrid.getChildren().clear();

        centergrid.setAlignment(Pos.CENTER_LEFT);

        Text intro = new Text("Fill in the question and level");
        Text intro2 = new Text("Click 'new menu' to add a drop-down menu");
        Text intro3 = new Text("Click add element to add the element to the selected drop-down menu");
        centergrid.add(intro, 1, 0, 10, 1);
        centergrid.add(intro2, 1, 1, 10, 1);
        centergrid.add(intro3, 1, 2, 10, 1);

        Label qlabel = new Label("Question:");
        qfield = new TextField();
        centergrid.add(qlabel, 0, 3);
        centergrid.add(qfield, 1, 3, 2, 1);

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

        Label menulabel = new Label("Menu:");
        menuField = new TextField();
        Button addMenubtn = new Button("Add menu");
        addMenubtn.setOnAction(e -> addMenu());
        centergrid.add(menulabel, 0, 10);
        centergrid.add(menuField, 1, 10, 2, 1);
        centergrid.add(addMenubtn, 3, 10);

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

        Button savebtn = new Button("Save Question");
        savebtn.setOnAction(e -> Save());

        centergrid.add(savebtn, 1, 12);
    }

    private static void addMenu() {
        DropDownHead menu = new DropDownHead(menuField.getText());
        menuField.setText("");
        addElementChoice.getItems().add(menu);
        addElementChoice.setValue(menu);
        showMenus();
    }

    private static void addElement() {
        Element element = new Element(elementField.getText());
        elementField.setText("");
        if (correctBox.isSelected()) {
            element.correct = true;
            correctBox.setSelected(false);
        }
        addElementChoice.getValue().getElements().add(element);
        showMenus();
    }

    private static void showMenus() {
        int i=1;
        for (DropDownHead menu : addElementChoice.getItems()) {
            ChoiceBox<Element> choiceBox = new ChoiceBox<>();
            choiceBox.setValue(menu);
            choiceBox.getItems().add(menu);
            for (Element element : menu.getElements()) {
                choiceBox.getItems().add(element);
            }
            centergrid.add(choiceBox, i, 9);
            i++;
        }
    }

    private static void Save() {

    }

}