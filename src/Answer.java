public class Answer {
    String text;
    boolean correct = false;

    public Answer(String text) {
        this(text, false);
    }

    public Answer(String text, boolean correct) {
        this.text = text;
        this.correct = correct;
    }
}
