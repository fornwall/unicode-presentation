import com.ibm.icu.text.BreakIterator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


public class GraphemeCluster {
    public static int getLength(String emoji) {
        BreakIterator it = BreakIterator.getCharacterInstance();
        it.setText(emoji);
        int emojiCount = 0;
        while (it.next() != BreakIterator.DONE) {
            System.out.println("GRAPHEME CLUSTER: " + emoji.substring(it.current(), it.last()));
            emojiCount++;
        }
        return emojiCount;
    }

    @Test
    void emojiLength() {
        // Byt ut
        Assertions.assertEquals(1, getLength("\uD83C\uDDFA\uD83C\uDDF8"));
        Assertions.assertEquals(1, getLength("\uD83D\uDC69\u200D\uD83D\uDC69\u200D\uD83D\uDC67\u200D\uD83D\uDC66"));
    }

}
