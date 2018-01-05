package database;

/**
 * Image question
 */
public class ClickQuestion extends Question{
    /** Image url*/
    public String image;
    /** question text*/
    public String text;
    /** answer top left coordinates*/
    public Position topLeft = new Position();
    /** answer bottom right coordinates*/
    public Position bottomRight = new Position();

    public void setTopLeft(int x, int y) {
        topLeft.x = x;
        topLeft.y = y;
    }

    public void setTopLeft(Position p) {
        topLeft = p;
    }

    public void setBottomRight(double x, double y) {
        bottomRight.x = x;
        bottomRight.y = y;
    }

    public void setBottomRight(Position p) {
        bottomRight = p;
    }

    public boolean isCorrect(double x, double y) {
        System.out.println("X: "+x);
        System.out.println("Y: "+y);
        System.out.println("CorrectLeftTop: "+topLeft);
        System.out.println("CorrectBottomRight "+bottomRight);
        return x >= topLeft.x
                && x <= bottomRight.x
                && y >= topLeft.y
                && y <= bottomRight.y
                ;
    }
}
