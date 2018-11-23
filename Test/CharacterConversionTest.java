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
}