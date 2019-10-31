import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.text.Normalizer;
import java.text.Normalizer.Form;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Normalization {

    @Test
    void normalization() {
        //  U+00C5 (LATIN CAPITAL LETTER A WITH RING ABOVE)
        String s1 = "\u00C5";
        // U+0041 (LATIN CAPITAL LETTER A), U+030A (COMBINING RING ABOVE)
        String s2 = "\u0041\u030A";
        // U+212B (ANGSTROM SIGN)
        String s3 = "\u212B";

        String n1 = Normalizer.normalize(s1, Form.NFD);
        String n2 = Normalizer.normalize(s2, Form.NFD);
        String n3 = Normalizer.normalize(s3, Form.NFD);

        assertEquals(n1, "\u0041\u030A");
        assertEquals(n1, n2);
        assertEquals(n2, n3);

        assertEquals("\u00C5", Normalizer.normalize("\u0041\u030A", Form.NFC));
        assertEquals("\u00C5", Normalizer.normalize("\u00C5", Form.NFC));
        assertEquals("\u0041\u030A", Normalizer.normalize("\u0041\u030A", Form.NFD));
        assertEquals("\u0041\u030A", Normalizer.normalize("\u00C5", Form.NFD));
    }

}
