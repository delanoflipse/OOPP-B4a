import org.junit.jupiter.api.Test;

import main.*;

import static org.junit.jupiter.api.Assertions.*;

class KeyValuePairTest {
    @Test
    void valueAsInt() {
        KeyValuePair res = new KeyValuePair("some key", "asdasdasd7");
        assertEquals(7,res.valueAsInt());
    }

}