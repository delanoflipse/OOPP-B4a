import database.*;

import java.util.ArrayList;

import database.*;

import static org.junit.jupiter.api.Assertions.*;

class DatabaseTest {

    @org.junit.jupiter.api.Test
    void getQuestionsForLevel() {
        Database tst = new Database();
        ArrayList res = new ArrayList<Question>();
        ArrayList res2 = new ArrayList<Question>();
        Question q1 = new TextQuestion();
        Question q2 = new TextQuestion();
        q1.level = 2;
        q2.level = 1;
        res.add(q1);
        res.add(q2);
        res2.add(q1);
        tst.questions = res;
        assertEquals(res2, Database.getQuestionsForLevel(2, "TextQuestion"));
        }

    }

