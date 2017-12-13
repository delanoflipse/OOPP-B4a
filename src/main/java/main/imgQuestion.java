package main;

import javax.swing.JFrame;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class imgQuestion extends Question{

    public String path;

    public imgQuestion( String path){


        this.path = path;
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


        return false;
    }
}
