
public class CharacterConversion {


    /**
     * This function takes in a binary as a String type and converts it to characters.
     *
     * @param binary : Sequence of zeros and ones.
     * @return : returns the String value of the Binary.
     */
    public String binaryToChar(String binary) {
        int begin;
        int end;
        int charValue;
        char character;
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < binary.length() / 8; i++) {
            begin = i * 8; // This will be 0, 8, 16 ...
            end = (i + 1) * 8; // This will be 8, 16, 32 ...
            charValue = Integer.parseInt(binary.substring(begin, end), 2); // This gives us the char value of a character
            character = (char) charValue;
            stringBuilder.append(character);
        }
        return stringBuilder.toString();
    }

    /**
     * This function takes in a String of characters and converts it to binaries.
     *
     * @param character : String character to convert to binary.
     * @return : Binary equivalent of the String character.
     */
    public String charToBinary(String character) {
        StringBuilder result = new StringBuilder();
        StringBuilder resultTemp = new StringBuilder();
        char[] characters = character.toCharArray();
        for (int i = 0; i < characters.length; i++) {
            resultTemp.append(String.valueOf(Integer.toBinaryString(characters[i])));
            resultTemp.insert(0, "0");
            result.append(resultTemp);
            resultTemp.setLength(0);
        }
        return result.toString();
    }

    /* Code adapted from https://stackoverflow.com/questions/18348745/decimal-to-binary-8-bits-only-using-append */
    public String convertDecimalToBinary(int no){ // 0 ... 7

        StringBuilder stringBuilder = new StringBuilder();
        StringBuilder stringBuilder1 = new StringBuilder();
        String padding;
        int restant;

        while (no != 0){
            restant = no % 2;
            stringBuilder.append(restant);
            no = no / 2;
        }
        padding = stringBuilder.reverse().toString();
        int length = padding.length();
        if(length < 8){
            while (8 - length>0){
                stringBuilder1.append("0");
                length++;
            }
        }
        return stringBuilder1.toString() + padding;
    }

    /* Inspiration : https://www.thecrazyprogrammer.com/2015/11/program-to-convert-binary-to-decimal-in-java.html */
    public int convertBinaryToDecimal(String binary){
        return Integer.parseInt(binary, 2);
    }

}
