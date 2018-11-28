
public class ErrorTesting {

    /*
    * TODO : Have to create test cases for Errors possible to occur in Trames being sent.
    * 1) Lose of Trame
    * 2) Errors of transmission
    * 3) Loses of ACK
    * 4) faire une test de timeout
    *
    * */
    // Faire une fonction qui bit shift un bit

    /**
     * Force a time out. In this case, force a 10 seconds timeout.
     */
    public void timeOut(){
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    /**
     * @param positionToShift : position of the bit to shift.
     * @return : return the string that has been bit shifted
     */
    public String bitShift(int positionToShift, String binarySequence){
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < binarySequence.length(); i++){
            if (i == positionToShift){
                if (binarySequence.charAt(i) == '0')
                    stringBuilder.append('1');
                else
                    stringBuilder.append('0');
                continue;
            }
            stringBuilder.append(binarySequence.charAt(i));
        }
        return stringBuilder.toString();
    }
}
