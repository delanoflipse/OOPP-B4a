package database;

/**
 * XY-vector Position class, used by the db parser
 */
class Position {
    public int x = 0;
    public int y = 0;

    public Position() {}

    public Position(int x, int y) {
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
        this.x = Integer.parseInt(arr[0].replaceAll("[^\\d]", ""));
        this.y = Integer.parseInt(arr[1].replaceAll("[^\\d]", ""));
    }

}
