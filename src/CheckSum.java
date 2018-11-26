import java.sql.SQLOutput;

public class CheckSum {

    /**
     * This function will add the number of zeros corresponding to the degree of the POLYNOME_GENERATEUR.
     * Le boolean onlyZeros est true seulement lorsque le current char à la position à appliquer l'opérateur
     * xor est de 0. Si c'est le cas, nous devons retourner un polynomeGenerateur de la même taille que
     * celui en entrée mais remplit de zéros.
     * Sinon, c'est comme nous avons apris en classe (c'est degrée - 1).
     *
     * @param onlyZeros : true if polynomial generator has to be only zeros, false if not.
     * @param polynomeGenerateur : The polynomial generator.
     * @return : Returns either polynomeGenerateur.length() number of zeros or polynomeGenerateur.length() - 1 number of zeros.
     */
    public StringBuilder addRDegreeZeros(boolean onlyZeros, String polynomeGenerateur){
        StringBuilder stringBuilder = new StringBuilder();
        int degreeOfPolyGen;
        if (onlyZeros) {
            degreeOfPolyGen = polynomeGenerateur.length();
        } else {
            degreeOfPolyGen = polynomeGenerateur.length() - 1;
        }
        for (int i = 0; i < degreeOfPolyGen; i++){
            stringBuilder.append("0");
        }
        return stringBuilder;
    }


    /**
     * This function will calculate the XOR division of a data and its polynomial generator.
     * @param polynomeGenerateur : In our case it is the CRC-16, but also works with different polynomial generators. -> G(x)
     * @param data : The binary data to apply our polynomial generator. -> M(x)
     * @return This function will return the result of the division (the remainder : R(x) in class notes).
     */
    public StringBuilder XORDivision(String data, String polynomeGenerateur){

        if (polynomeGenerateur.length() == 0){
            System.err.println("La taille du polynôme Générateur est de zéro.");
            return new StringBuilder("La taille du polynôme Générateur est de zéro.");
        }

        if (data.length() < polynomeGenerateur.length()){
            return new StringBuilder(data);
        }

        int ctr = 0;
        String positionPolynome = polynomeGenerateur;

        /*
        * current : the current bits that we are comparing
        * next : the next bits that we are going to compare
        * result : the result of the division
        */
        StringBuilder current = new StringBuilder();
        StringBuilder next = new StringBuilder();
        StringBuilder result = new StringBuilder();

        /* Firstly we construct the firstCurrentBits. */
        for (int i = 0; i < polynomeGenerateur.length(); i++){
            current.append(data.charAt(i));
        }

        /* Lorsque le reste à la toute fin est de mm taille que le degrée du polynomeGenerateur. */
        while (ctr <= (data.length() - polynomeGenerateur.length())){

            /* On revient à notre polynomeGenerateur initial. */
            polynomeGenerateur = positionPolynome;

            /*
            * We avoid having to reset a new StringBuilder.
            * https://stackoverflow.com/questions/5192512/how-can-i-clear-or-empty-a-stringbuilder
            */
            next.setLength(0);

            for (int i = 0; i < polynomeGenerateur.length(); i++){

                // We look at the first number
                if (i == 0){
                    /*
                    * On commence par regarder le premier bit du current.
                    * Si c'est un 0, on met tout le polynomeGenerateur à zero puisque nous
                    * allons faire le XOR avec 0..0 et on append un 0 au result.
                    * Si c'est un 1, on doit aussi vérifier que le polynomeGenerateur est également
                    * un 1 et on append un 1 au result puisque nous soustraions 1 * notre polynomeGenerateur.
                    * */
                    if (current.charAt(i) == '0'){
                        result.append("0");
                        // On modifie le polynôme pour la comparaison
                        // Ici on met le tout à 0..0
                        polynomeGenerateur = addRDegreeZeros (true, polynomeGenerateur).toString();
                    } else if (current.charAt(i) == '1' && polynomeGenerateur.charAt(i) == '1'){
                        result.append("1");
                    }
                } else {
                    /* Maintenant nous devons appliquer le XOR entre le current et le polynomeGenerateur. */
                    if (current.charAt(i) == polynomeGenerateur.charAt(i)) {
                        next.append("0");
                    } else {
                        next.append("1");
                    }
                }

            }

            /* Il reste encore des chiffres à ajouter à la séquence. */
            if(data.length() - polynomeGenerateur.length() != ctr)
                next.append((data.charAt(ctr + polynomeGenerateur.length())));

            /* On bouge le current bit à calculer. */
            current.setLength(0);
            for (int i = 0; i < next.length(); i++) {
                current.append(next.charAt(i));
            }

            ctr++;
        }
        return current;

    }

    /**
     * @param data : data to validate the CRC.
     * @param polynomeGenerateur : The polynomial generator.
     * @return : true if the CRC division is all zeros false if atleast 1 one.
     */
    public boolean validateCRC(String data, String polynomeGenerateur){

        String result = checkSumData(data, polynomeGenerateur);
        for (int i = 0; i < result.length(); i++){
            if (result.charAt(i) != '0')
                return false;
        }
        return true;
    }

    /**
     * @param data : The data to do the checkSum operation on.
     * @param polynomeGenerateur : The polynomial generator.
     * @return : The remainder of the division.
     */
    public String checkSumData(String data, String polynomeGenerateur){

        /* First we have to add the zeros at the end of the data. */
        data += addRDegreeZeros(false, polynomeGenerateur).toString();

        StringBuilder result = XORDivision(data, polynomeGenerateur);

        return result.toString();

    }

    public static void main(String[] args) {
        CheckSum cs = new CheckSum();

        String current = cs.XORDivision("101100110000", "10011").toString();
        System.out.println(current);
        System.out.println(current.equals("0100"));

        String a = cs.checkSumData("101100110100", "10011");
        System.out.println(a);

        System.out.println(cs.validateCRC("","1001"));

        System.out.println(cs.XORDivision("100010000001000", "10001000000100001").toString());
    }

}
