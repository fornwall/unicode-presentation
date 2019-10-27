class: center, middle
# Något om Unicode


---
# Det började med bilder...

![Various writing systems](images/alphabets-old.png)

???
Skriftspråk, symboler för att representera skriftliga uttryck, har funnits länge.

---
# ASCII

- ASCII, American Standard Code for Information Interchange, arbetades fram under 60-talet

- Är en sju bitars encoding:
   - 0.red[xxxxxxx]

   - 2^27 = 128 möjliga värden: 0-127


---
# ASCII-tecken
Följande tecken kan uttryckas:

- 0123456789
- ABCDEFGHIJKLMNOPQRSTUVWXYZ
- abcdefghijklmnopqrstuvwxyz
- !"#$%&'()\*+,-./:;<=>?@
- [\\]^\_&#96;{|}~

Och flera "konstiga" tecken som inte är synliga, t.ex. ESC, TAB, WHITESPACE, DEL.

???
- Sju bitars encoding för att åttonde kan vara en paritetsbit.
- Summera bit:arna och kontrollera att de är jämn (eller udda).


---
# Hela ASCII tabellen
![Various writing systems](images/ascii-table.gif)



---
# 7 bitar räcker inte långt

- När åttonde biten blev tillgänglig fick det plats för mer. Många varianter som använde åttonde bit:en skapades

- *ISO 8859-1* (eller *latin1*) den vanligaste i västvärlden
  - ÅÄÖ (och ØÑØÙß..) har värden definierade
  - Stödjer "västeuropeiska" språk (men inte alla, och inte fullt ut)
  
---
# 8 bitar räcker inte heller så långt...

- Räcker fortfarande inte långt internationellt

  - Och behov av tecken tillkommer även i västeuropa - exempelvis €

---
# En universell teckenkodning?

- Det vore önskvärt att kunna uttrycka all världens tecken med en standard

- Unicode, en idé om detta, arbetades fram i slutet på 80-talet

- Joe Becker från Xerox skapade ett första dokument för Unicode


---
# Unicode, ursprunglig draft

"Unicode is intended to address the need for a workable, reliable world text encoding.

Unicode could be roughly described as "wide-body ASCII" that has been stretched to 16 bits to encompass the characters of all the world's **living languages**.

In a properly engineered design, 16 bits per character are more than sufficient for this purpose."


---
# UTF-16

- A=0x0041, B=0x0042, ..., Ö=0x00D6, ..., ∞=0x221E
  - Värdet för A=0x41 är kompatibelt med ASCII, men inte byte-representationen (två bytes)
  
- Värdena kräver två bytes - vilken ordning? *Big-Endian* vs *Little-Endian*


---
# Identitet för bokstäver oavsett teckenkodning
- Låt oss införa en notation för värden av tecken

- "Bokstäver" får en identitet, som A:=0x0041

- Detta är på en mer abstrakt nivå än teckenkodning. A är A oavsett om ASCII eller UTF-16 används som teckenenkodning
  
---
# Kodpunkter
- Vi kallar denna identet för *kodpunkt* (en: *code point*)
  - Mer generellt än bokstav

- Använd U+0041 som syntax istället för 0x0041

- Unicode definierar både egenskaper för kodpunkter, och teckenenkodningar för att representera dessa


---
# Endian problemet: Big-Endian
- Big-Endian (eller *network byte order*): Den högsta byte kommer först.

  - Som decimalsystem (12 = 10 + 2)
  
  - På stora (*Big*) addresser kommer slutet (*End*) på talet
  
  - ∞=0x221E blir representerat som byte-sekvensen `{0x22, 0x1E}`

---
# Endian problemet: Little-Endian
- Little-Endian: Omvänt - på små (*Small*) addresser kommer slutet (*End*) på talet

  - På små (*Little*) addresser kommer slutet (*End*) på talet.
  
  - Används av intelprocessorer
  
  - ∞=0x221E blir representerat som bytet-sekvensen `{0x1E, 0x22}`

---
# UTF-16BE och UTF16LE

- U+.blue[FE].red[FF] är en byte order mark.

  - Placera den i början på fil. Vid inläsning, om de två första byte är {0x.blue[FE], 0x.red[FF]} är det BE, annars {0x.red[FF], 0x.blue[FE]} och därigenom LE

  - Det omkastade värdet U+.red[FF].blue[FE] är definierat att aldrig användas av text, så det inte dyker upp av misstag
  
---
# Default endianess i UTF-16

- Om ingen BOM finns i början på input?

  - Enligt Unicode-standard är UTF-16BE "default"
  
  - I java är UTF-16=UTF-16BE om ingen BE detekteras
  
  - Men UTF-16LE är vanligare i praktiken och t.ex. WHATWG säger att tolka UTF-16 som UTF-16LE
  
---
# Character encoding i java
- En character encoding representeras av klassen [java.nio.charset.Charset](https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/nio/charset/Charset.html)

- För vanliga charsets finns Charset-instanser som fält i [StandardCharsets](https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/nio/charset/StandardCharsets.html)
  - StandardCharsets.ISO_8859_1
  - StandardCharsets.UTF_8
  - StandardCharsets.UTF_16
  - StandardCharsets.UTF_16BE
  - StandardCharsets.UTF16_LE

---
# Icke-standard encoding i java
För icke-standard encodings kan `Charset.forName(String charsetName)` användas:

```java
try {
    Charset myCharset = Charset.forName("utf-16");
    doSomethingWithCharset(myCharset);
} catch (UnsupportedCharsetException e) {
    // Hantera UnsupportedCharsetException, ett checked exception.
}
```

```java
// För standard charsets (som UTF-8 ovan) är det dock onödigt med ovanstående
// konstruktion (från och med java 7), eftersom dessa charset alltid stöds:
doSomethingWithCharset(StandardCharsets.UTF_16);
```
  
---
# Läsa UTF_16BE och UTF-16LE i java

```java
// BOM=U+FEFF, A=U+0041
// OBS: Dåligt som exempel, BOM ska inte användas när byte order anges explicit
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
```

???
- Understryk ordningen av byte array.
- Byte Order Mark erhålls som första bokstav pga av byte order angetts explicit i BE och LE


---
# Detektera byte order med java
```java
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
```

???
- Notera att BOM inte läses till applikationen.


---
# När byte order inte anges?
```java
@Test
void defaultUtf16() throws IOException {
    // A=U+0041
    var out = new ByteArrayOutputStream();
    out.writeBytes(new byte[]{0x00, 0x41});

    var in = new InputStreamReader(new ByteArrayInputStream(out.toByteArray()),
                                   StandardCharsets.UTF_16);
    char c = (char) in.read();
    Assertions.assertEquals('A', c);

    out = new ByteArrayOutputStream();
    out.writeBytes(new byte[]{0x41, 0x00});

    in = new InputStreamReader(new ByteArrayInputStream(out.toByteArray()),
                               StandardCharsets.UTF_16);
    c = (char) in.read();
    // 䄀=U+4100
    Assertions.assertEquals('䄀', c);
}
```

???
- Observera att "A" läses in fel.
- Java default:ar UTF-16 som UTF-16BE.


---
# 16 bits räcker inte

- Räcker inte för historiska skriftspråk

- Räcker inte för alla möjliga emojis och varianter på dessa

- Tillåt fler genom att ibland kombinera speciella 16-bitars värden


---
# Surrogatkodpunkter
  - Om första värdet är en *high surrogate*, i intervallet U+D800 till U+DBFF (vilket ger 1,024 möjliga värder)...
  
  - så kombineras den med en följande *low surrogate*, i intervallet U+DC00 till U+DFFF (återigen 1,024 möjliga värden)
  

---
# Unicodes namnrymd
- Totalt möjliga värden:
  - 2^16 - 2\*1024 + 1024\*1024 = 1,112,064

- Unicode kommer inte definiera utanför den mängden
  
- 836,536 lediga tecken (**Reserverade**, dvs tillgängliga för användning men ännu inte definierade) i senaste Unicode 12.1

---
# Unicodes namnrymd
- Totalt möjliga värden:
  - 2^16 - 2\*1024 + 1024\*1024 = 1,112,064

- Unicode kommer inte definiera utanför den mängden
  
- 836,536 lediga tecken (**Reserverade**, dvs tillgängliga för användning men ännu inte definierade) i senaste Unicode 12.1


---
# Hur ser det ut på bit-nivå?

```
U' = yyyyyyyyyyxxxxxxxxxx  // U - 0x10000
W1 = 110110yyyyyyyyyy      // 0xD800 + yyyyyyyyyy
W2 = 110111xxxxxxxxxx      // 0xDC00 + xxxxxxxxxx
```

---
# Java och UTF-16
- "Java använder UTF-16"

- Java har en primitiv *char*, 16 bitar

- `String.length()` returnerar antal UTF-16 värden, inte antal kodpunkter

- Kodpunkter som kräver två 16 bitars är ofta ovanliga
  - Många system hanterar inte detta korrekt
  
---
# Java-sträng från bytes och till bytes
- `new String(byte[] bytes, Charset charset)`

- `byte[] String.getBytes(Charset charset)`
  
- Vid större datamängder bör strömmar användas för att kontinuerligt tolka/koda allteftersom data blir tillgänligt

---
# Hantering av surrogatkodpunkter i java


```java
// java.lang.Character har flera relevanta hjälpmetoder:
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
    // Do something with code point.
}
```


---
# String.codePoints()

- `String.codePoints()` returnerar en `IntStream`

```java
String s = "...";
s.codePoints().forEach(codePoint -> {
    // Do something with code point.
});
```


---
# Vad händer om vi bryter mitt i?

- "Skriv ut de tre första bokstäverna i namnet?"

- 🙃=U+1F643, "UPSIDE-DOWN FACE", kräver surrogatkodpunkter.

```java
public static void main(String[] args) {
    String name = "🙃🙃";
    String start = name.substring(0, 3);
    System.out.println(start);
}
```

---
# Felhantering i UTF-16
- Skriver ut en 🙃=U+1F643, följt av ?=U+003F.

- Detta är betendet på `PrintStream`-instansen i `System.out` - det finns API:er för att hantera fel på andra sätt.

- Fel här kan vara en hög surrogatkodpunkt som inte följs av låg, eller låg som inte föregås av hög.


---
# UTF-32

- Varför inte bara skriva ut varje kodpunkt som ett 32-bitars värde?

- UTF-32 - enkelt, men används väldigt lite i praktiken.

- Endian-problemet återkommer, så UTF-32BE och UTF-32LE precis som i java.

---
# UTF-24?

- För 1,112,064, max antal kodpunkter, kan egentligen representeras med 21 bitar

- Fanns förslag om UTF-24 men accepterades aldrig


---
# UTF-8

- UTF-16 är 16-bitars värden som vid behov kan kombineras **med ett annat värde** för att ge en kodpunkt med högt värde

- UTF-8 är 8-bitars värden som vid behov kan kombineras **med upp till tre andra värden** för att ge en kodpunkt med högt värde


---
# Varför UTF-8

- Bakåtkompatibilitet med ASCII, i det att ASCII är ett subset av UTF-8, så alla ASCII filer är UTF-8 filer.

- Utrymme i minne och lagring - om största delen av texten är ASCII (vilket i många sammanhang är fallet), dubblerar UTF-16 minnesanvändning (= ger sämre prestanda)


---
# UTF-8, hur ser det ut?
- Bakåtkompatibilitet med ASCII, så upp till sju bitar representeras på samma sätt: 0x0.red[xxxxxxx]

- 110xxxxx + 10xxxxxx

- 1110xxxx + 10xxxxxx + 10xxxxxx

- 11110xxx + 10xxxxxx + 10xxxxxx + 10xxxxxx

---
# Continuation bytes
- 110xxxxx + 10xxxxxx

- 1110xxxx + 10xxxxxx + 10xxxxxx

- 11110xxx + 10xxxxxx + 10xxxxxx + 10xxxxxx

- En inledande byte (110xxxxx/1110xxxx/11110xxx) som följs av *continuation bytes* (10xxxxxx)

---
# Upp till 21 bitar

- 11110xxx + 10xxxxxx + 10xxxxxx + 10xxxxxx är 21 bitar, vilket är tillräckligt för att täcka Unicodes namnrymd på 1,112,064 värden


---
# UTF-8 är självsynkroniserande
- Hoppas det in mitt i en UTF-8 ström kommer inte en felaktigt värde avläsas

- Istället kan continuation bytes (10xxxxxx) skippas (max 3 st) innan strömmen kan börja avläsas igen

---
# UTF-8: Overlong encodings
- 0xxxxxxx för sju bitar
- 110.red[xxxx]x + 10xxxxxx för 8 till 11 bitar

- Om rödmarkerade bitar sätts till 0 så kan ASCII-tecken representeras på två sätt.
  - Och liknande för längre sekvenser.
  - Tillåts inte: mappning kodpunkt <-> serialisering i UTF-8 ett till ett, minimal längd på enkodning måste användas.
  
---
# UTF-8: För stora värden
- 11110xxx + 10xxxxxx + 10xxxxxx + 10xxxxxx

- 21 bits räcker för Unicodes namnrymd på 1,112,064 - men kan också uttrycka större värden.

- Om värdet är för stort är det ogiltigt UTF-8.


---
# Möjliga fel vid deserialisering
- Overlong encoding
- För stora värden
- Surrogatkodpunkter (får inte förekomma som kodpunkter i UTF-8)
- Oväntad continuation byte
- En leading byte när en continuation byte förväntas
- En sträng som avslutas innan alla continuation bytes erhållits

---
# Felhantering
- UTF-8 decoder: Transformerar en ström av bytes till en ström av (teckenenkodade) kodpunkter

- Tidiga UTF-8 decoders hade ofta problem, ingen eller inkonsekvent felhantering

---
# Fel är oacceptabelt
- Unicode definierar att en decoder måste hantera ogiltiga byte-sekvenser som fel

  - Får inte acceptera ogiltigt input
  - Får inte ge ifrån sig ogiltiga kodpunkter (surrogat, för höga värden)
  - "Hantera som fel" kan betyda olika saker

---
# Felhantering: Kasta exception eller avbryt inläsning

- En decoder kan kasta exception eller avbryta inläsning
  - Kan leda till denial of service eller säkerhetshål om oväntat

- Används inte av javas standardbibliotek.

---
# Felhantering: Byt ut fel mot ersättningsbokstav
- Byt ut fel mot ersättningsbokstav

- Ofta ?=U+003F (QUESTION MARK*) eller �=U+FFFD (REPLACEMENT CHARACTER)
  
```java
@Test
void för_i_iso_8859_1_inläst_som_utf8() {
    byte[] för_i_iso_8859_1 = new byte[]{'f', (byte) 0xF6, 'r' };
    String s = new String(för_i_iso_8859_1, StandardCharsets.UTF_8);
    Assertions.assertEquals("f\uFFFDr", s);
}
```

???
- Vi har gått igenom encodings nu - paus innan vi tar oss an Unicode och egenskaper

---
# Unicode som databas
- Unicode kan ses som en databas som definierar egenskaper för kodpunkter

- Namn är en egenskap:
  - U+0041 (A) har namnet "LATIN CAPITAL LETTER A"
  - U+1F4A9 (💩) har namnet "PILE OF POO"
  
---
# Namn av code point i java
- Javas standardbibliotek innehåller delar av Unicodes databas

- `Character.getName(int codePoint)` kan t.ex. användas för att erhålla namnet för en kodpunkt:

```java
@Test
void codePointName() {
    var codePoint = 0x0041; // A
    assertEquals("LATIN CAPITAL LETTER A", Character.getName(codePoint));
    codePoint = 0x1F4A9; // 💩
    assertEquals("PILE OF POO", Character.getName(codePoint));
}
```

---
# Java versioner och Unicode versioner
```
Java version   Release date         Unicode version
------------   ------------         ---------------
Java 13        September 2019       Unicode 12.1
Java 12        March 2019           Unicode 11.0
Java 11        March 2018           Unicode 10.0
Java 10        September 2018       Unicode 8.0
Java 9         September 2017       Unicode 8.0
Java 8         March 2014           Unicode 6.2
Java SE 7      July 28, 2011        Unicode 6.0
Java SE 6      December 11, 2006    Unicode 4.0
J2SE 5.0       September 30, 2004   Unicode 4.0
J2SE 1.4       February 6, 2002     Unicode 3.0
J2SE 1.3       May 8, 2000          Unicode 2.1
J2SE 1.2       December 8, 1998     Unicode 2.1
JDK 1.1        February 19, 1997    Unicode 2.0
JDK 1.1.7      September 12, 1997   Unicode 2.1
JDK 1.1        February 19, 1997    Unicode 2.0
JDK 1.0        January 23, 1996     Unicode 1.1.5
```

---
# General category
Varje kodpunkt har en [General Category](https://en.wikipedia.org/wiki/Unicode_character_property#General_Category)

Kan erhållas med hjälp av `Character.getType(int codePoint)`:

```java
@Test
void generalCategory() {
    assertEquals(Character.CONNECTOR_PUNCTUATION, Character.getType('_'));
    assertEquals(Character.MATH_SYMBOL, Character.getType('='));
    assertEquals(Character.SURROGATE, Character.getType(0xD800));
}
```

---
# Plan i unicode

- Gå igenom hur många, namn
- Visa bild på BMP: https://en.wikipedia.org/wiki/UTF-16#/media/File:Unifont_Full_Map.png


---
# Combining characters
- En combining character modifierar andra bokstäver, som när `U+0306 (COMBINING BREVE)` nedan modifierar `U+0079 (LATIN SMALL LETTER Y)`.

![y och breve](combining.svg)

---
# Combining characters: Emoji modifier
Ett annat exempel på modifiers är de som anger hudfärg på emojis, som `U+1F3FE (EMOJI MODIFIER FITZPATRICK TYPE-5)` nedan:

![emoji och skin tone](skin-modifier.png)

---
# Combining characters: Multiple

Flera modifiers kan förekomma, nedan med `U+0065 (LATIN SMALL LETTER E)` och `U+0304 (COMBINING MACRON)` som exempel:

## ē // U+0065, U+0304

## ē̄ // U+0065, U+0304, U+0304

## ē̄̄ // U+0065, U+0304, U+0304, U+0304

---
# Bokstäver som kan uttryckes både med och utan modifier

---
# Bokstäver kan vara olika breda
Från (BRAAA POST)
https://manishearth.github.io/blog/2017/01/14/stop-ascribing-meaning-to-unicode-code-points/

`U+FDFD (ARABIC LIGATURE BISMILLAH AR-RAHMAN AR-RAHEEM)` är en kodpunkt som ser ut så här:
﷽
..

---
# TODO: Collation? Upper/Lower case?
Inspiration from:
https://manishearth.github.io/blog/2017/01/15/breaking-our-latin-1-assumptions/
TODO: Mention uniview? https://r12a.github.io/uniview/?charlist=%F0%9F%91%A9%E2%80%8D%F0%9F%91%A9%E2%80%8D%F0%9F%91%A7%E2%80%8D%F0%9F%91%A6
