import org.junit.Assert;
import org.junit.Test;

public class CharacterConversionTest {

    private CharacterConversion characterConversion = new CharacterConversion();
    @Test
    public void testBinaryToChar() {
        String result;
        result = characterConversion.binaryToChar("0110111101101011");
        Assert.assertEquals(result, "ok");

        System.out.println("----- JUnit testBinaryToChar() finished without error. -----");
    }

    @Test
    public void testCharToBinary() {
        String result;
        result = characterConversion.charToBinary("coucou");
        Assert.assertEquals(result, "011000110110111101110101011000110110111101110101");

        System.out.println("----- JUnit testCharToBinary() finished without error. -----");
    }

    @Test
    public void testBinaryToCharEqualsCharToBinary(){
        String resultBinaryToChar;
        String resultCharToBinary;
        resultBinaryToChar = characterConversion.binaryToChar("01100101011010100111000001000001010100110100111101001100");
        resultCharToBinary = characterConversion.charToBinary("ejpASOL");

        Assert.assertEquals(resultBinaryToChar, "ejpASOL");
        Assert.assertEquals("01100101011010100111000001000001010100110100111101001100", resultCharToBinary);

        System.out.println("----- JUnit testBinaryToCharEqualsCharToBinary() finished without error. -----");
    }

    @Test
    public void testTrameTypeCharToBinary(){
        String resultBinaryToChar;
        String resultCharToBinary;

        /* Test pour le type I -> Trame d'information. */
        resultBinaryToChar = characterConversion.binaryToChar("01001001");
        resultCharToBinary = characterConversion.charToBinary("I");

        Assert.assertEquals(resultBinaryToChar, "I");
        Assert.assertEquals("01001001", resultCharToBinary);

        System.out.println("----- JUnit testTrameTypeCharToBinary() -> I finished without error. -----");


        /* Test pour le type C -> Demande de connexion. */
        resultBinaryToChar = characterConversion.binaryToChar("01000011");
        resultCharToBinary = characterConversion.charToBinary("C");

        Assert.assertEquals(resultBinaryToChar, "C");
        Assert.assertEquals("01000011", resultCharToBinary);

        System.out.println("----- JUnit testTrameTypeCharToBinary() -> C finished without error. -----");

        /* Test pour le type A -> Accusé de reception. */
        resultBinaryToChar = characterConversion.binaryToChar("01000001");
        resultCharToBinary = characterConversion.charToBinary("A");

        Assert.assertEquals(resultBinaryToChar, "A");
        Assert.assertEquals("01000001", resultCharToBinary);

        System.out.println("----- JUnit testTrameTypeCharToBinary() -> A finished without error. -----");


        /* Test pour le type R -> Rejet de la trame Num et de toutes celles envoyées après. */
        resultBinaryToChar = characterConversion.binaryToChar("01010010");
        resultCharToBinary = characterConversion.charToBinary("R");

        Assert.assertEquals(resultBinaryToChar, "R");
        Assert.assertEquals("01010010", resultCharToBinary);

        System.out.println("----- JUnit testTrameTypeCharToBinary() -> R finished without error. -----");


        /* Test pour le type F -> Fin de la communication. */
        resultBinaryToChar = characterConversion.binaryToChar("01000110");
        resultCharToBinary = characterConversion.charToBinary("F");

        Assert.assertEquals(resultBinaryToChar, "F");
        Assert.assertEquals("01000110", resultCharToBinary);

        System.out.println("----- JUnit testTrameTypeCharToBinary() -> F finished without error. -----");


        /* Test pour le type P -> Trame avec P bit. */
        resultBinaryToChar = characterConversion.binaryToChar("01010000");
        resultCharToBinary = characterConversion.charToBinary("P");

        Assert.assertEquals(resultBinaryToChar, "P");
        Assert.assertEquals("01010000", resultCharToBinary);

        System.out.println("----- JUnit testTrameTypeCharToBinary() -> P finished without error. -----");

    }
}