import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class KeyValuePairTest {
    @Test
    void valueAsInt() {
        KeyValuePair res = new KeyValuePair("some key", "asdasdasdasd7");
        assertEquals(7,res.valueAsInt());
    }

}