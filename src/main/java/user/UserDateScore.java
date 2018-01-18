package user;

/**
 * A score instance
 */
public class UserDateScore {
    /** A date */
    public String date;
    /** A score */
    public int score;

    /** Circular reference to the user*/
    public UserData user;
}
