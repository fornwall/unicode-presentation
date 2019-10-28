public class Main {

    public static void describeCodePoints(String s) {
        s.codePoints().forEach(codePoint -> {
            var name = Character.getName(codePoint);
            var id = Character.toString(codePoint) + " U+" + String.format("%04X", codePoint);
            System.out.println(id + " " + name);
        });
        System.out.println();
    }

    public static void describeForms(String s) {
        System.out.println("### Input string: " + s);
        describeCodePoints(s);

        var map = new TreeMap<String, String>();
        for (var form : Normalizer.Form.values()) {
            var normalized = Normalizer.normalize(s, form);
            map.merge(normalized, form.name(), (oldValue, value) -> oldValue + " & " + value);
        }
        for (var entry : map.entrySet()) {
            System.out.println("# " + entry.getValue());
            describeCodePoints(entry.getKey());
        }
        System.out.println();
    }

    public static void main(String[] args) {
        describeForms("Å");

        describeForms("Å");

        describeForms("A\u030A");

        //text with the acute accent symbol
        describeForms("touch" + "\u00e9");

        //text with ligature
        describeForms("a" + "\ufb03" + "ance");

        //text with the cedilla
        describeForms("fa" + "\u00e7" + "ade");

        //text with half-width katakana
        describeForms("\uff81\uff6e\uff7a\uff9a\uff70\uff84");

        var title = "title";
        describeCodePoints(title.toUpperCase());
        describeCodePoints(title.toUpperCase(Locale.forLanguageTag("tr")));

        describeForms(title.toUpperCase(Locale.forLanguageTag("tr")));

        describeForms("ﬀ");

        // Good resource
        // http://www.unicode.org/faq/private_use.html

        System.out.println("CASE: ");
        describeCodePoints("ß");
        describeCodePoints("ß".toUpperCase());
    }

    void upper() {
        Assertions.assertEquals("ß".toUpperCase(), "SS");
    }

}

