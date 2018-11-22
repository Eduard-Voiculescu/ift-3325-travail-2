public class Trame {

    private final String flag = "01111110";
    private String type;
    private String num;
    private String data;
    private String crc;
    private boolean dead;

    /**
     * Constructeur
     */
    public Trame() {
    }

    public String getFlag() {
        return flag;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getCrc() {
        return crc;
    }

    public void setCrc(String crc) {
        this.crc = crc;
    }

    public boolean isDead() {
        return dead;
    }

    public void setDead(boolean dead) {
        this.dead = dead;
    }

    // TODO: constructeur
    // TODO: getters and setters for properties
    // TODO: checksum (crc)
}
