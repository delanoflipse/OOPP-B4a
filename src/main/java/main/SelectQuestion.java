package main;

public class SelectQuestion extends Question{

    private double correctltY;
    private double correctltX;
    private double correctrbY;
    private double correctrbX;
    private double range;
    private String imgname;
    private String question;

    public SelectQuestion(double ltY, double ltX, double rbY, double rbX, double range, String imgname, String question) {
        this.correctltY = ltY;
        this.correctltX = ltX;
        this.correctrbY = rbY;
        this.correctrbX = rbX;
        this.range = range;
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

    public boolean isCorrect(double ltY, double ltX, double rbY, double rbX) {
        if (ltY < correctltY + range && ltY > correctltY - range
            && ltX < correctltX + range && ltX > correctltX - range
            && rbY < correctrbY + range && rbY > correctrbY - range
            && rbX < correctrbX + range && rbX > correctrbX - range) {
            return true;
        }
        return false;
    }
}
