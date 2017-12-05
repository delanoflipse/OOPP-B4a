import javax.swing.JFrame;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class imgQuestion extends Question{

     String path;

    public imgQuestion(String text, int level, String path){

        super(text, level);
        this.path = path;
    }

    public void addAnswer(imgAnswer answer) {
        answers.add(answer);
    }

    public String toString(){

        String res;
        return res;
    }

    public void display(){
        JFrame frame = new JFrame();
        ImageIcon icon = new ImageIcon(path);
        JLabel label = new JLabel(icon);
        frame.add(label);
        frame.setDefaultCloseOperation
                (JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public boolean isCorrect(int index) {
        if (answers.size() > index) {
            return answers.get(index).correct;
        }

        return false;
    }
}
