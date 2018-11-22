

public class TestBitStuffing {

    public String testSender(){

        BitStuffing bitStuffing = new BitStuffing();

        String origin = "0111111001111111111111110";
        origin = bitStuffing.bitStuffingSender(origin);
        return origin;

    }

    public String testReceiver(){
        BitStuffing bitStuffing = new BitStuffing();

        String origin = "01111101001111101111101111100";
        origin = bitStuffing.bitStuffingReceiver(origin);
        return origin;
    }

    public String testReceiverEmpty(){
        BitStuffing bitStuffing = new BitStuffing();

        String origin = "";
        origin = bitStuffing.bitStuffingReceiver(origin);
        return origin;
    }

}
