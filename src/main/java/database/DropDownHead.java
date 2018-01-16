package database;

import java.util.ArrayList;

public class DropDownHead extends Element {

    public ArrayList<Element> subelements;

    public DropDownHead (String text) {
        super(text);
        subelements = new ArrayList<>();
    }

    public ArrayList<Element> getElements() {
        return subelements;
    }

    public String toString() {
        return super.toString();
    }
}
