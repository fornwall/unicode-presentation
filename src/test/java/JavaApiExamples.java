import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;

public class JavaApiExamples {

    @Test
    void invalidCodePoint() {
        int codePoint = 1_500_000;
        try {
            char[] chars = Character.toChars(codePoint);
            Assertions.fail("toChars() did not throw");
        } catch (IllegalArgumentException e) {
            // Expected.
        }
    }

    @Test
    void valid() {
        int codePoint = 1_000_000;
        char[] chars = Character.toChars(codePoint);
        Assertions.assertEquals(2, chars.length);

        Assertions.assertEquals(codePoint, Character.codePointAt(chars, 0));
    }

    @Test
    void för_i_iso_8859_1_inläst_som_utf8() {
        byte[] för_i_iso_8859_1 = new byte[]{'f', (byte) 0xF6, 'r' };
        String s = new String(för_i_iso_8859_1, StandardCharsets.UTF_8);
        Assertions.assertEquals("f\uFFFDr", s);
    }

}
