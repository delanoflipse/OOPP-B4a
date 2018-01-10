import database.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class TextQuestionTest {
    @Test
    void addAnswer() {
        TextQuestion res = new TextQuestion();
        TextQuestion res2 = new TextQuestion();
        TextAnswer answer = new TextAnswer();
        answer.text = "Just a test.";
        res.addAnswer(answer);
        res2.addAnswer(answer);
        assertEquals(res.answers, res2.answers);
    }

    @Test
    void isCorrect_false() {
        TextQuestion res = new TextQuestion();
        TextAnswer answer = new TextAnswer();
        answer.text = "Just a test.";
        res.addAnswer(answer);
        assertFalse(res.isCorrect(4));
    }

    @Test
    void isCorrect_true(){
        TextQuestion res = new TextQuestion();
        TextAnswer answer = new TextAnswer();
        answer.text = "Just a test.";
        answer.correct = true;
        res.addAnswer(answer);
        assertTrue(res.isCorrect(0));
    }

}