import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class ErrorTestingTest {

    ErrorTesting errorTesting = new ErrorTesting();
    @Test
    public void testBitShift() {
        String result;
        result = errorTesting.bitShift(8, "01111110010010100000000000000000000000000111110");
        Assert.assertEquals(result, "01111110110010100000000000000000000000000111110");
        System.out.println("----- JUnit testBitShift() -> successfully fliped bit at index 8 -----");

        result = errorTesting.bitShift(12, "01111110010010100000000000000000000000000111110");
        Assert.assertEquals(result, "01111110010000100000000000000000000000000111110");
        System.out.println("----- JUnit testBitShift() -> successfully fliped bit at index 12 -----");

        result = errorTesting.bitShift(16, "01111110010010100000000000000000000000000111110");
        Assert.assertEquals(result, "01111110010010101000000000000000000000000111110");
        System.out.println("----- JUnit testBitShift() -> successfully fliped bit at index 16 -----");


        System.out.println("----- JUnit testBitShift() -> finished without error. -----");
    }
}