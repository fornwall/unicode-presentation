import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Utf16 {

    @Test
    void length() {

        assertEquals(1, "a".length());

        assertEquals(0x0061, 'a');

        assertEquals(1, Character.charCount(0x0061));
    }

    @Test
    void codePointName() {

        assertEquals('a', Character.codePointOf("LATIN SMALL LETTER A"));

        assertEquals("LATIN SMALL LETTER A", Character.getName('a'));

    }

}
