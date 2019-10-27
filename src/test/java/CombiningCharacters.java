public class CombiningCharacters {
    public static void main(String[] args) {
        var s = new String(new int[]{(int) 'a', 0x0306}, 0, 2);
        System.out.println(s);

        s = new String(new int[]{'e', 0x0304, 0x0304}, 0, 3);
        System.out.println(s);
        System.out.println('\uFDFD');
    }
}
