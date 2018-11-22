public class Trame {

    private final String FLAG;
    private String type;
    private String num;
    private String data;
    private String crc;
//    private boolean dead;

    /**
     * Constructeur
     */
    public Trame(String type, String num, String data, String crc) {
        this.FLAG = "01111110";
        this.type = type;
        this.num = num;
        this.data = data;
        this.crc = crc;
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

    // TODO: checksum (crc)
}
