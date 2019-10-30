import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PrivateUse {

    @Test
    void generalCategory() {
        assertEquals(Character.PRIVATE_USE, Character.getType(''));
    }

}
