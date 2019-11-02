import com.ibm.icu.text.BreakIterator;
import com.ibm.icu.util.ULocale;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Locale;


public class GraphemeCluster {

    public static int getLength(String emoji) {
        BreakIterator it = BreakIterator.getCharacterInstance();
        it.setText(emoji);
        int emojiCount = 0;
        while (it.next() != BreakIterator.DONE) {
            //System.out.println("GRAPHEME CLUSTER: " + emoji.substring(it.current(), it.last()));
            emojiCount++;
        }
        return emojiCount;
    }

    public static int getLength(String emoji, Locale locale) {
        BreakIterator it = BreakIterator.getCharacterInstance(ULocale.forLocale(
                locale
        ));
        it.setText(emoji);
        int emojiCount = 0;
        while (it.next() != BreakIterator.DONE) {
            emojiCount++;
        }
        return emojiCount;
    }


    @Test
    void emojiLength() {
        // Byt ut
        Assertions.assertEquals(1, getLength("\uD83C\uDDFA\uD83C\uDDF8"));
        Assertions.assertEquals(1, getLength("\uD83D\uDC69\u200D\uD83D\uDC69\u200D\uD83D\uDC67\u200D\uD83D\uDC66"));

        String s = "Dz";
            int lenEn = getLength(s, Locale.forLanguageTag("en"));
            for (Locale locale : Locale.getAvailableLocales()) {
                int len = getLength(s, locale);
                if (lenEn != len) {
                    Assertions.fail("codePoint=" + Integer.toHexString(1));
                }
            }
    }

}
