package main;

public class ImageQuestion extends Question{
    public String image;
    public String text;
    public int[] topLeft = {0,0};
    public int[] bottomRight = {0,0};

    public void setTopLeft(int x, int y) {
        topLeft[0] = x;
        topLeft[1] = x;
    }

    public void setBottomRight(int x, int y) {
        bottomRight[0] = x;
        bottomRight[1] = x;
    }

    @Override
    public boolean isCorrect(int x, int y) {
        return x >= topLeft[0] && x <= bottomRight[0] && y >= topLeft[1] && y <= bottomRight[1];
    }
}
