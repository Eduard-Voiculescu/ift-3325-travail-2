import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class CheckSumTest {

    /*
     * Ici pour plusieurs des tests, nous avons pris le site du Department of Electrical
     * and computer Engineering of University of New Brunswick. Il y avait quelques autres
     * site qui était probablement valide, mais puisque ce dernier est un site universitaire, il y a plus
     * de chance que le site soit ok. Site web : http://www.ee.unb.ca/cgi-bin/tervo/calc.pl
     * */

    CheckSum checkSum = new CheckSum();

    @Test
    public void addRDegreeZerosTest(){

        /* Test CRC-16. */
        String polynome = "10001000000100001";
        String result = checkSum.addRDegreeZeros(false, polynome).toString();

        Assert.assertEquals(result, "0000000000000000");
        System.out.println("----- JUnit addRDegreeZerosTest() -> CRC-16 finished without error. -----");

        /* Test random */
        polynome = "1110010";
        result = checkSum.addRDegreeZeros(false, polynome).toString();

        Assert.assertEquals(result, "000000");
        System.out.println("----- JUnit addRDegreeZerosTest() -> CRC 1110010  finished without error. -----");

    }

    @Test
    public void XORDivionTest(){

        /* Test empty polynome generateur */
        String polynome = "";
        String result = checkSum.XORDivision("10111010110", polynome).toString();

        Assert.assertEquals("La taille du polynôme Générateur est de zéro.", result);
        System.out.println("----- JUnit XORDivionTest() -> Test empty polynome generateur finished without error. -----");

        /* Test data is all zeros */
        polynome = "10011";
        result = checkSum.XORDivision("0000000000", polynome).toString();

        Assert.assertEquals("0000", result);
        System.out.println("----- JUnit XORDivionTest() -> Test data is all zeros finished without error. -----");


        /* Test diapositive 38 notes de cours couche-liaison */
        polynome = "10011";
        result = checkSum.XORDivision("101100110000", polynome).toString();

        Assert.assertEquals(result, "0100");
        System.out.println("----- JUnit XORDivionTest() -> diapositive 38 notes de cours couche liaison finished without error. -----");

        /* Test diapositive 35 notes de cours couche-liaison */
        polynome = "1011";
        result = checkSum.XORDivision("1001001", polynome).toString();

        Assert.assertEquals(result, "111");
        System.out.println("----- JUnit XORDivionTest() -> diapositive 35 notes de cours couche liaison finished without error. -----");

        /* Test CRC-16 */
        polynome = "10001000000100001";
        result = checkSum.XORDivision("101010101010100010111101010100001010111010101010101010100101", polynome).toString(); // totally random

        Assert.assertNotEquals(result, "0");
        System.out.println("----- JUnit XORDivionTest() -> Test CRC-16 finished without error. -----");
    }

    @Test
    public void checkSumDataTest(){

        /* Test empty polynome generateur */
        String polynome = "";
        String result = checkSum.checkSumData("0000000000", polynome);

        Assert.assertEquals("La taille du polynôme Générateur est de zéro.", result);
        System.out.println("----- JUnit checkSumDataTest() -> Test empty polynome generateur finished without error. -----");

        /* Test data is all zeros */
        polynome = "10011";
        result = checkSum.checkSumData("0000000000", polynome);

        Assert.assertEquals("0000", result);
        System.out.println("----- JUnit checkSumDataTest() -> Test data is all zeros finished without error. -----");

        /* Test diapositive 38 notes de cours couche-liaison */
        polynome = "10011";
        result = checkSum.checkSumData("10110011" + checkSum.XORDivision("101100110000", polynome), polynome);

        Assert.assertEquals(result, "0000");
        System.out.println("----- JUnit checkSumDataTest() -> Test diapositive 38 notes de cours couche-liaison finished without error. -----");

        /* Test diapositive 35 notes de cours couche-liaison */
        polynome = "1011";
        result = checkSum.checkSumData("1001001" + checkSum.XORDivision("1001001", polynome), polynome);

        Assert.assertEquals(result, "100");
        System.out.println("----- JUnit checkSumDataTest() -> diapositive 35 notes de cours couche liaison finished without error. -----");

        /* Test CRC-16 */
        polynome = "10001000000100001";
        result = checkSum.checkSumData("101010101010100010111101010100001010111010101010101010100101", polynome); // totally random

        Assert.assertNotEquals(result, "0");
        System.out.println("----- JUnit checkSumDataTest() -> Test CRC-16 finished without error. -----");

    }

    @Test
    public void validateCRCTest(){

        /* Test diapositive 38 notes de cours couche-liaison */
        String polynome = "10011";
        boolean result = checkSum.validateCRC("10110011" + checkSum.XORDivision("101100110000", polynome), polynome);

        Assert.assertTrue(result);
        System.out.println("----- JUnit validateCRCTest() -> Test diapositive 38 notes de cours couche-liaison finished without error. -----");

        /* Test CRC-16 */
        polynome = "10001000000100001";
        String remainder = checkSum.checkSumData("10010101010100", polynome);
        /*
        * Site web -> http://www.ee.unb.ca/cgi-bin/tervo/calc.pl?num=100101010101000000000000000000&den=10001000000100001&f=d&e=1&m=1
        * 100101010101000000000000000000 / 10001000000100001 = 1110001101100010
        * */
        Assert.assertEquals(remainder, "1110001101100010");
        result = checkSum.validateCRC("100101010101000000000000000000", polynome);

        Assert.assertFalse(result);
        System.out.println("----- JUnit validateCRCTest() -> Test CRC-16 finished without error. -----");

        /* Test2 CRC-16 */
        polynome = "10001000000100001";
        remainder = checkSum.checkSumData("101010101010101010001011110101010101", polynome);
        /*
         * Site web -> http://www.ee.unb.ca/cgi-bin/tervo/calc.pl?num=1010101010101010100010111101010101010000000000000000&den=10001000000100001&f=d&e=1&m=1
         * 1010101010101010100010111101010101010000000000000000 / 10001000000100001 = 1011001001001111
         * */
        Assert.assertEquals(remainder, "1011001001001111");
        result = checkSum.validateCRC("1010101010101010100010111101010101010000000000000000", polynome);

        Assert.assertFalse(result);
        System.out.println("----- JUnit validateCRCTest() -> Test2 CRC-16 finished without error. -----");

        /* Test data vide et polynomeGenerateur vide */
        polynome = "";
        result = checkSum.validateCRC("",polynome);

        Assert.assertFalse(result);
        System.out.println("----- JUnit validateCRCTest() -> Test data vide et polynomeGenerateur vide finished without error. -----");

        /* Test polynomeGenerateur vide */
        polynome = "";
        result = checkSum.validateCRC("1010101010",polynome);

        Assert.assertFalse(result);
        System.out.println("----- JUnit validateCRCTest() -> Test polynomeGenerateur vide finished without error. -----");

        /* Test data plus petit que polynomeGenerateur */
        polynome = "10001000000100001";
        result = checkSum.validateCRC("1000100000010000",polynome);

        Assert.assertFalse(result);
        System.out.println("----- JUnit validateCRCTest() -> Test data plus petit que polynomeGenerateur finished without error. -----");

    }

}