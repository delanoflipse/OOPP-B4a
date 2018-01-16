package database;

import java.util.ArrayList;

public class GUIQuestion extends Question{

    public GUIQuestion(String text, int level) {
        this.answers = new ArrayList<>();
        this.text = text;
        this.level = level;
    }

}
