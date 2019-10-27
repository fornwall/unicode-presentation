public class CodePointDescriber {

    public static void describe(int codePoint) {
        System.out.println("Code point: U+" + Integer.toHexString(codePoint) + ":");

        if (!Character.isValidCodePoint(codePoint)) {
            System.out.println("- Invalid codepoint");
            return;
        }

        System.out.println("- Name: " + Character.getName(codePoint));
        System.out.println("- Class: " + getTypeString(codePoint));
    }

    public static String getTypeString(int codePoint) {
        int type = Character.getType(codePoint);
        switch (type) {
            case Character.COMBINING_SPACING_MARK:
                return "COMBINING_SPACING_MARK";
            case Character.CONNECTOR_PUNCTUATION:
                return "CONNECTOR_PUNCTUATION";
            case Character.CONTROL:
                return "CONTROL";
            case Character.CURRENCY_SYMBOL:
                return "CURRENCY_SYMBOL";
            case Character.DASH_PUNCTUATION:
                return "DASH_PUNCTUATION";
            case Character.DECIMAL_DIGIT_NUMBER:
                return "DECIMAL_DIGIT_NUMBER";
            case Character.ENCLOSING_MARK:
                return "ENCLOSING_MARK";
            case Character.END_PUNCTUATION:
                return "END_PUNCTUATION";
            case Character.FINAL_QUOTE_PUNCTUATION:
                return "FINAL_QUOTE_PUNCTUATION";
            case Character.FORMAT:
                return "FORMAT";
            case Character.INITIAL_QUOTE_PUNCTUATION:
                return "INITIAL_QUOTE_PUNCTUATION";
            case Character.LETTER_NUMBER:
                return "LETTER_NUMBER";
            case Character.LINE_SEPARATOR:
                return "LINE_SEPARATOR";
            case Character.LOWERCASE_LETTER:
                return "LOWERCASE_LETTER";
            case Character.MATH_SYMBOL:
                return "MATH_SYMBOL";
            case Character.MODIFIER_LETTER:
                return "MODIFIER_LETTER";
            case Character.MODIFIER_SYMBOL:
                return "MODIFIER_SYMBOL";
            case Character.NON_SPACING_MARK:
                return "NON_SPACING_MARK";
            case Character.OTHER_LETTER:
                return "OTHER_LETTER";
            case Character.OTHER_NUMBER:
                return "OTHER_NUMBER";
            case Character.OTHER_PUNCTUATION:
                return "OTHER_PUNCTUATION";
            case Character.OTHER_SYMBOL:
                return "OTHER_SYMBOL";
            case Character.PARAGRAPH_SEPARATOR:
                return "PARAGRAPH_SEPARATOR";
            case Character.PRIVATE_USE:
                return "PRIVATE_USE";
            case Character.SPACE_SEPARATOR:
                return "SPACE_SEPARATOR";
            case Character.START_PUNCTUATION:
                return "START_PUNCTUATION";
            case Character.SURROGATE:
                return "SURROGATE";
            case Character.TITLECASE_LETTER:
                return "TITLECASE_LETTER";
            case Character.UNASSIGNED:
                return "UNASSIGNED";
            case Character.UPPERCASE_LETTER:
                return "UPPERCASE_LETTER";
            default:
                return "?????(value=" + type + ")";
        }
    }

    public static void main(String[] args) {
        String name = "🙃🙃";
        name.codePoints().forEach(CodePointDescriber::describe);


        describe(0x0306);
        describe(0xE000);
        describe(0xE001);
        describe(0xFEFF);

        "\uD83C\uDDFA\uD83C\uDDF8".codePoints().forEach(CodePointDescriber::describe);
    }
}

