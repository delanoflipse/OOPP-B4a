import java.util.ArrayList;


public class TextQuestion extends Question {
    public ArrayList<TextAnswer> answers;

    public TextQuestion() {
        this.answers = new ArrayList<TextAnswer>();
    }

    public void addAnswer(TextAnswer answer) {
        answers.add(answer);
    }

    public void display() {
        int base = (int) 'A';
        int i = 0;
        for (TextAnswer ans : answers) {
            System.out.print((char) (base + i));
            System.out.print(") ");
            System.out.println(ans.text);
            i++;
        }
    }


    public boolean isCorrect(int index) {
        if (answers.size() > index) {
            return answers.get(index).correct;
        }

        return false;
    }
}
