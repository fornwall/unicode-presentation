import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class NamnAvCodePointIJava {

    @Test
    void codePointName() {
        var codePoint = 0x0041; // A
        assertEquals("LATIN CAPITAL LETTER A", Character.getName(codePoint));
        codePoint = 0x1F4A9; // 💩
        assertEquals("PILE OF POO", Character.getName(codePoint));
    }

}
