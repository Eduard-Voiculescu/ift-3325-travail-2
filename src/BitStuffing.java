
/**
 * Bit Stuffing est fait dans tous les champs sauf les fanions.
 * Nous avons un bit stuffing pour le sender (BitStuffingReceiver et
 * BitStuffingSender)
 */
public class BitStuffing {

    /**
     * This function is used by the sender to bitstuff it's data.
     * @param data : data is of binary form (zeros and ones)
     * */
    public String bitStuffingSender(String data){
        int ctr = 0;
        StringBuilder bitStuffedData = new StringBuilder();
        for(int i = 0; i < data.length(); i++){
            /* We have 5 consecutive '1'. We append a 0 */
            if (ctr == 5){
                bitStuffedData.append("0");
                ctr = 0; // Reset
            }
            /* Data can only be bits (0, 1) */
            if (data.charAt(i) == '1'){
                bitStuffedData.append(data.charAt(i));
                ctr++;
            } else {
                /* We reset the counter as it breaks a consecutive number of 1's */
                bitStuffedData.append(data.charAt(i));
                ctr = 0;
            }
        }
        return bitStuffedData.toString();
    }

    /**
     * This functions is used by the receiver to decode the bitstuffing data done by the sender.
     * @param data : data is of binary form (zeros and ones)
     * */
    public String bitStuffingReceiver(String data) {
        int ctr = 0;
        StringBuilder bitStuffedData = new StringBuilder();
        for(int i = 0; i < data.length(); i++){
            if (ctr == 5){
                ctr = 0;
                continue;
            }
            /* Data can only be bits (0, 1) */
            if (data.charAt(i) == '1'){
                bitStuffedData.append(data.charAt(i));
                ctr++;
            } else {
                /* We reset the counter as it breaks a consecutive number of 1's */
                bitStuffedData.append(data.charAt(i));
                ctr = 0;
            }
        }
        return bitStuffedData.toString();
    }

}
