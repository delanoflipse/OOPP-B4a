package database;

/**
 * XY-vector Position class, used by the db parser
 */
public class Position {
    public double x = 0;
    public double y = 0;

    public Position() {}

    public Position(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Create new position from a string value with format "x,y"
     * @param value A string
     */
    public Position(String value) {
        // split, parse, save
        String[] arr = value.trim().split(",");
        this.x = Double.parseDouble(arr[0].replaceAll("[^\\d]", ""));
        this.y = Double.parseDouble(arr[1].replaceAll("[^\\d]", ""));
    }

    public String toString() {
        String out = (int)x + "," + (int)y;
        return out;
    }

}
