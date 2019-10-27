import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GeneralCategory {

    @Test
    void generalCategory() {
        assertEquals(Character.CONNECTOR_PUNCTUATION, Character.getType('_'));
        assertEquals(Character.MATH_SYMBOL, Character.getType('='));
        assertEquals(Character.SURROGATE, Character.getType(0xD800));
    }

}
