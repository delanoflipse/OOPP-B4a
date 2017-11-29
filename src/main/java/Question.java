import org.javalite.activejdbc.Base;
import org.javalite.activejdbc.Model;

import java.util.List;
import java.util.Scanner;

/**This class represents a multiple choice question
 * Its values are saved in the database using Active JDBC
 */
public class Question extends Model {

    /**Gives a String containing the question and the
     * possible answers
     * @return String question and answers
     */
    public String toString(){
        List<Answer> answers = this.getAll(Answer.class);
        String output = this.get("text") + "\n";

        int i = 0;
        for (Answer answer : answers) {
            char symbol = (char) ('A' + i);
            output += "\t " + symbol + ". " + answer.get("text");
            i++;
        }

        return output;
    }

    /**Checks whether the given answer is the correct one
     * @param char input
     * @return boolean correct answer
     */
    public boolean answer_correct(char input, Scanner insc){
        List<Answer> answers = this.getAll(Answer.class);
        int inputindex = (int) (input - 'A');
        if (inputindex >= answers.size() || inputindex < 0) {
            System.out.println("Please enter one of the possible answers.");
            char inputchar = insc.next().charAt(0);
            return this.answer_correct(inputchar, insc);
        }

        return (int) answers.get(inputindex).get("correct") == 1;

    }


}
