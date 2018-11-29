
/*
 * Travail fait par EID Alain et VOICULESCU Eduard.
 * Cours --- IFT-3325 : Téléinformatique --- Université de Montréal.
 * Travail remis à Zakaria Abou El Houda.
 */

public class ErrorTesting {

    /*
    * 1) Lose of Trame
    * 2) Errors of transmission
    * 3) Loses of ACK
    * 4) faire une test de timeout
    */

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


    /**
     * This function will destroy specified trame.
     * @param numTrameToDestroy : number associated to trame to destroy.
     * @param trame : destroy specified trame.
     * @return : the number of the trame specified.
     */
    public int destroyTrame(int numTrameToDestroy, Trame trame){
        if(trame.getIndexInArrayList() == numTrameToDestroy){
            trame.setError(true);
            return numTrameToDestroy;
        }
        return -1;
    }
}
