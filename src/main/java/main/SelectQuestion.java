package main;

import database.Question;

public class SelectQuestion extends Question{

    private double correctltY;
    private double correctltX;
    private double correctrbY;
    private double correctrbX;
    private double range;
    private String imgname;
    private String question;

    public SelectQuestion(double ltY, double ltX, double rbY, double rbX, String imgname, String question) {
        this.correctltY = ltY;
        this.correctltX = ltX;
        this.correctrbY = rbY;
        this.correctrbX = rbX;
        this.imgname = imgname;
        this.question = question;
    }

    public String getQuestion() {
        return question;
    }

    public String getImgname() {
        return imgname;
    }

    public double getCorrectltX() {
        return correctltX;
    }

    public double getCorrectltY() {
        return correctltY;
    }

    public double getCorrectrbX() {
        return correctrbX;
    }

    public double getCorrectrbY() {
        return correctrbY;
    }

    public boolean isCorrect(double X, double Y) {
        if (Y > correctltY && Y < correctrbY
            && X > correctltX && X < correctrbX) {
            return true;
        }
        return false;
    }
}
