import org.junit.jupiter.api.Test;

import java.util.function.Consumer;

public class JavaLangCharacter {

    void decode(String s, Consumer<Integer> onCodePoint) {
        var string = "...";
        for (int i = 0; i < string.length(); i++) {
            char c1 = string.charAt(i);
            int codePoint;
            if (Character.isHighSurrogate(c1)) {
                i++;
                char c2 = string.charAt(i + 1);
                if (Character.isLowSurrogate(c2)) {
                    throw new IllegalArgumentException("What?");
                } else{
                    codePoint = Character.toCodePoint(c2, c1);
                }
            } else {
                codePoint = c1;
            }

            // Do something with code point:
            onCodePoint.accept(codePoint);
        }
    }

    void loopStream() {
        String s = "...";
        s.codePoints().forEach(/*int*/ codePoint -> {
            // Do something with code point.
        });
    }

    public static void main(String[] args) {
        String name = "🙃🙃";
        String start = name.substring(0, 3);
        System.out.println(start);
    }

}
