import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UpperCase {

    @Test
    void upperCase() {
        // I: U+0049 (LATIN CAPITAL LETTER I)
        assertEquals("\u0049", "i".toUpperCase(Locale.ENGLISH));
        // I: U+0049 (LATIN CAPITAL LETTER I)
        assertEquals("\u0130", "i".toUpperCase(Locale.forLanguageTag("tr")));

        assertEquals("SS", "ß".toUpperCase(Locale.ENGLISH));
    }

}
