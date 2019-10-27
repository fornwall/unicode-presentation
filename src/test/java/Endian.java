import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class Endian {

    @Test
    void specifiedOrderWithBom() throws IOException {
        var out = new ByteArrayOutputStream();
        out.writeBytes(new byte[]{(byte) 0xFE, (byte) 0xFF, 0x00, 0x41});
        var in = new InputStreamReader(new ByteArrayInputStream(out.toByteArray()),
                StandardCharsets.UTF_16BE);
        char c = (char) in.read();
        Assertions.assertEquals(0xFEFF, c);
        c = (char) in.read();
        Assertions.assertEquals('A', c);
        out = new ByteArrayOutputStream();
        out.writeBytes(new byte[]{(byte) 0xFF, (byte) 0xFE, 0x41, 0x00});
        in = new InputStreamReader(new ByteArrayInputStream(out.toByteArray()),
                StandardCharsets.UTF_16LE);
        c = (char) in.read();
        Assertions.assertEquals(0xFEFF, c);
        c = (char) in.read();
        Assertions.assertEquals('A', c);
    }

    // BOM=U+FEFF, A=U+0041
    @Test
    void detectByteOrder() throws IOException {
        var out = new ByteArrayOutputStream();
        out.writeBytes(new byte[]{(byte) 0xFE, (byte) 0xFF, 0x00, 0x41});

        var in = new InputStreamReader(new ByteArrayInputStream(out.toByteArray()),
                                       StandardCharsets.UTF_16);
        char c = (char) in.read();
        Assertions.assertEquals('A', c);

        out = new ByteArrayOutputStream();
        out.writeBytes(new byte[]{(byte) 0xFF, (byte) 0xFE, 0x41, 0x00});

        in = new InputStreamReader(new ByteArrayInputStream(out.toByteArray()),
                                   StandardCharsets.UTF_16);
        c = (char) in.read();
        Assertions.assertEquals('A', c);
    }

    @Test
    void defaultUtf16() throws IOException {
        // A=U+0041
        var out = new ByteArrayOutputStream();
        out.writeBytes(new byte[]{0x00, 0x41});

        var in = new InputStreamReader(new ByteArrayInputStream(out.toByteArray()), StandardCharsets.UTF_16);
        char c = (char) in.read();
        Assertions.assertEquals('A', c);

        out = new ByteArrayOutputStream();
        out.writeBytes(new byte[]{0x41, 0x00});

        in = new InputStreamReader(new ByteArrayInputStream(out.toByteArray()), StandardCharsets.UTF_16);
        c = (char) in.read();
        // 䄀=U+4100
        Assertions.assertEquals('䄀', c);
    }



    @Test
    void testEncode() {
        // You get the UTF-16BE with a BOM when encoding to UTF-16:
        var bytes = "A".getBytes(StandardCharsets.UTF_16);
        Assertions.assertArrayEquals(new byte[]{(byte) 0xFE, (byte) 0xFF, 0x00, 0x41}, bytes);

        // Specifying BE or LE gets rid of BOM:
        bytes = "A".getBytes(StandardCharsets.UTF_16BE);
        Assertions.assertArrayEquals(new byte[]{0x00, 0x41}, bytes);
        bytes = "A".getBytes(StandardCharsets.UTF_16LE);
        Assertions.assertArrayEquals(new byte[]{0x41, 0x00}, bytes);
    }
}
