
/*
 * Travail fait par EID Alain et VOICULESCU Eduard.
 * Cours --- IFT-3325 : Téléinformatique --- Université de Montréal.
 * Travail remis à Zakaria Abou El Houda.
 */

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

    @Test
    public void testConvertDecimalToBinary(){
        String result;

        result = characterConversion.convertDecimalToBinary(0);
        Assert.assertEquals(result,"00000000");
        System.out.println("----- JUnit testConvertDecimalToBinary() :0: conversion is OK -----");

        result = characterConversion.convertDecimalToBinary(1);
        Assert.assertEquals(result,"00000001");
        System.out.println("----- JUnit testConvertDecimalToBinary() :1: conversion is OK -----");

        result = characterConversion.convertDecimalToBinary(2);
        Assert.assertEquals(result,"00000010");
        System.out.println("----- JUnit testConvertDecimalToBinary() :2: conversion is OK -----");

        result = characterConversion.convertDecimalToBinary(3);
        Assert.assertEquals(result,"00000011");
        System.out.println("----- JUnit testConvertDecimalToBinary() :3: conversion is OK -----");

        result = characterConversion.convertDecimalToBinary(4);
        Assert.assertEquals(result,"00000100");
        System.out.println("----- JUnit testConvertDecimalToBinary() :4: conversion is OK -----");

        result = characterConversion.convertDecimalToBinary(5);
        Assert.assertEquals(result,"00000101");
        System.out.println("----- JUnit testConvertDecimalToBinary() :5: conversion is OK -----");

        result = characterConversion.convertDecimalToBinary(6);
        Assert.assertEquals(result,"00000110");
        System.out.println("----- JUnit testConvertDecimalToBinary() :6: conversion is OK -----");

        result = characterConversion.convertDecimalToBinary(7);
        Assert.assertEquals(result,"00000111");
        System.out.println("----- JUnit testConvertDecimalToBinary() :7: conversion is OK -----");

        System.out.println("----- JUnit testConvertDecimalToBinary() finished without error. -----");

    }

    @Test
    public void testConvertBinaryToDecimal(){
        int result;

        result = characterConversion.convertBinaryToDecimal("00000000");
        Assert.assertEquals(result, 0);
        System.out.println("----- JUnit testConvertBinaryToDecimal() :00000000: conversion is OK -----");

        result = characterConversion.convertBinaryToDecimal("00000001");
        Assert.assertEquals(result, 1);
        System.out.println("----- JUnit testConvertBinaryToDecimal() :00000001: conversion is OK -----");

        result = characterConversion.convertBinaryToDecimal("00000010");
        Assert.assertEquals(result, 2);
        System.out.println("----- JUnit testConvertBinaryToDecimal() :00000010: conversion is OK -----");

        result = characterConversion.convertBinaryToDecimal("00000011");
        Assert.assertEquals(result, 3);
        System.out.println("----- JUnit testConvertBinaryToDecimal() :00000011: conversion is OK -----");

        result = characterConversion.convertBinaryToDecimal("00000100");
        Assert.assertEquals(result, 4);
        System.out.println("----- JUnit testConvertBinaryToDecimal() :00000100: conversion is OK -----");

        result = characterConversion.convertBinaryToDecimal("00000101");
        Assert.assertEquals(result, 5);
        System.out.println("----- JUnit testConvertBinaryToDecimal() :00000101: conversion is OK -----");

        result = characterConversion.convertBinaryToDecimal("00000110");
        Assert.assertEquals(result, 6);
        System.out.println("----- JUnit testConvertBinaryToDecimal() :00000110: conversion is OK -----");

        result = characterConversion.convertBinaryToDecimal("00000111");
        Assert.assertEquals(result, 7);
        System.out.println("----- JUnit testConvertBinaryToDecimal() :00000111: conversion is OK -----");

        System.out.println("----- JUnit testConvertBinaryToDecimal() finished without error. -----");

    }

    @Test
    public void testConvertDecimalToBinaryEqualsConvertBinaryToDecimal(){
        boolean result = false;
        String binary;
        int decimal;

        binary = "00000000";
        decimal = 0;
        result = characterConversion.convertDecimalToBinary(characterConversion.convertBinaryToDecimal(binary)).equals(binary);
        Assert.assertTrue(result);
        System.out.println("----- JUnit testConvertDecimalToBinaryEqualsConvertBinaryToDecimal() :00000000:0: conversion is OK -----");

        binary = "00000001";
        decimal = 1;
        result = characterConversion.convertDecimalToBinary(characterConversion.convertBinaryToDecimal(binary)).equals(binary);
        Assert.assertTrue(result);
        System.out.println("----- JUnit testConvertDecimalToBinaryEqualsConvertBinaryToDecimal() :00000001:1: conversion is OK -----");

        binary = "00000010";
        decimal = 2;
        result = characterConversion.convertDecimalToBinary(characterConversion.convertBinaryToDecimal(binary)).equals(binary);
        Assert.assertTrue(result);
        System.out.println("----- JUnit testConvertDecimalToBinaryEqualsConvertBinaryToDecimal() :00000010:2: conversion is OK -----");

        binary = "00000011";
        decimal = 3;
        result = characterConversion.convertDecimalToBinary(characterConversion.convertBinaryToDecimal(binary)).equals(binary);
        Assert.assertTrue(result);
        System.out.println("----- JUnit testConvertDecimalToBinaryEqualsConvertBinaryToDecimal() :00000011:3: conversion is OK -----");

        binary = "00000100";
        decimal = 4;
        result = characterConversion.convertDecimalToBinary(characterConversion.convertBinaryToDecimal(binary)).equals(binary);
        Assert.assertTrue(result);
        System.out.println("----- JUnit testConvertDecimalToBinaryEqualsConvertBinaryToDecimal() :00000100:4: conversion is OK -----");

        binary = "00000101";
        decimal = 5;
        result = characterConversion.convertDecimalToBinary(characterConversion.convertBinaryToDecimal(binary)).equals(binary);
        Assert.assertTrue(result);
        System.out.println("----- JUnit testConvertDecimalToBinaryEqualsConvertBinaryToDecimal() :00000101:5: conversion is OK -----");

        binary = "00000110";
        decimal = 6;
        result = characterConversion.convertDecimalToBinary(characterConversion.convertBinaryToDecimal(binary)).equals(binary);
        Assert.assertTrue(result);
        System.out.println("----- JUnit testConvertDecimalToBinaryEqualsConvertBinaryToDecimal() :00000110:6: conversion is OK -----");

        binary = "00000111";
        decimal = 7;
        result = characterConversion.convertDecimalToBinary(characterConversion.convertBinaryToDecimal(binary)).equals(binary);
        Assert.assertTrue(result);
        System.out.println("----- JUnit testConvertDecimalToBinaryEqualsConvertBinaryToDecimal() :00000111:7: conversion is OK -----");

        System.out.println("----- JUnit testConvertDecimalToBinaryEqualsConvertBinaryToDecimal() finished without error. -----");

    }

}