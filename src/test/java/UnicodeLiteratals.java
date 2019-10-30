import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class UnicodeLiteratals {

    @Test
    void literals() {
        Assertions.assertEquals(0x41, '\u0041');
        Assertions.assertEquals('A', '\u0041');
        // U+1F600 (GRINNING FACE) kräver ett surrogatpar:
        Assertions.assertEquals("😀", "\uD83D\uDE00");
    }

}
