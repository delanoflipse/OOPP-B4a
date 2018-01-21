package database;

//A GUI element for the question
public class Element extends Answer{

    private String text;

    public Element(String text) {
        super();
        this.text = text;
    }

    public String toString() {
        return text;
    }
}
