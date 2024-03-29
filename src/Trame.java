
/*
 * Travail fait par EID Alain et VOICULESCU Eduard.
 * Cours --- IFT-3325 : Téléinformatique --- Université de Montréal.
 * Travail remis à Zakaria Abou El Houda.
 */

import java.io.Serializable;

public class Trame implements Serializable {

    /* Objects to use. */
    BitStuffing bitStuffing = new BitStuffing();

    private final String FLAG;
    private String type;
    private String num;
    private String data;
    private String crc;
    private int indexInArrayList;
    private boolean error;

    /**
     * Constructeur
     */
    public Trame(String type, String num, String data, String crc, int indexInArrayList) {
        this.FLAG = "01111110";
        this.type = type;
        this.num = num;
        this.data = data;
        this.crc = crc;
        this.indexInArrayList = indexInArrayList;
        this.error = false;
    }

    /**
     * This function will return the frame format :
     * -------------------------------------------------------------
     * |  FLAG  |   Type   |   Num   |   Data   |   CRC   |  FLAG  |
     * -------------------------------------------------------------
     * De plus, la trame sera également bitstuffed.
     * @return : Returns the entire frame format.
     */
    public String makeTrameFormat(){
        String result = this.FLAG + this.bitStuffSenderTrame() + this.FLAG;

        /* Ici on doit bit stuff type, num, data et crc */
        this.type = bitStuffing.bitStuffingSender(this.type);
        this.num = bitStuffing.bitStuffingSender(this.num);
        this.data = bitStuffing.bitStuffingSender(this.data);
        this.crc = bitStuffing.bitStuffingSender(this.crc);

        return result;
    }

    /**
     * @return
     */
    public String bitStuffSenderTrame(){
        return bitStuffing.bitStuffingSender(this.type + this.num + this.data + this.crc);
    }

    /**
     * This function will pretty print a Trame.
     * The printed format will be the following : >  FLAG  :::   Type   :::   Num   :::   Data   :::   CRC   :::  FLAG  <
     */
    public String prettyPrint(){
        return ">" +
                " FLAG " + this.FLAG + " ::: " +
                " Type " + this.type + " ::: " +
                " Num " + this.num + " ::: " +
                " Data " + this.data + " ::: " +
                " CRC " + this.crc + " ::: " +
                " FLAG " + this.FLAG +
                " <";
    }

    /* ------------------------------------------- Setters ------------------------------------------- */

    /**
     * @param type : I, C, A, R, F or P type of frame.
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @param num : This specifies the number of the frame sent.
     */
    public void setNum(String num) {
        this.num = num;
    }

    /**
     * @param data : Sets the data of the frame.
     */
    public void setData(String data) {
        this.data = data;
    }

    /**
     * @param crc : sets the CRC (pôlynome générateur) of the frame.
     */
    public void setCrc(String crc) {
        this.crc = crc;
    }


    /**
     * @param indexInArrayList : sets the index in array list
     */
    public void setIndexInArrayList(int indexInArrayList) {
        this.indexInArrayList = indexInArrayList;
    }

    /**
     * @param error : sets the error
     */
    public void setError(boolean error) {
        this.error = error;
    }

    /* ------------------------------------------- Getters ------------------------------------------- */

    /**
     * @return : This returns the FLAG value. By default FLAG is always 01111110
     */
    public String getFLAG() {
        return FLAG;
    }

    /**
     * @return : This returns the frame type (either I, C, A, R, F or P)
     */
    public String getType() {
        return type;
    }

    /**
     * @return : gets the number of the trame.
     */
    public String getNum() {
        return num;
    }

    /**
     * @return : returns the data of the frame.
     */
    public String getData() {
        return data;
    }

    /**
     * @return : gets the CRC (pôlynome générateur) of the frame.
     */
    public String getCrc() {
        return crc;
    }

    /**
     * @return : returns the index of the trame in the array list.
     */
    public int getIndexInArrayList() {
        return indexInArrayList;
    }

    /**
     * @return : returns a boolean of the error.
     */
    public boolean isError() {
        return error;
    }
}
