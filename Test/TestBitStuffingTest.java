import org.junit.Assert;
import org.junit.Test;

public class TestBitStuffingTest {

    @Test
    public void testSender() {
        TestBitStuffing testBitStuffing = new TestBitStuffing();
        Assert.assertEquals(testBitStuffing.testSender(), "01111101001111101111101111100");

        System.out.println("----- JUnit testSender() finished without error. -----");
    }

    @Test
    public void testReceiver() {
        TestBitStuffing testBitStuffing = new TestBitStuffing();
        Assert.assertEquals(testBitStuffing.testReceiver(), "0111111001111111111111110");

        System.out.println("----- JUnit testReceiver() finished without error. -----");
    }

    @Test
    public void testReceiverEmpty(){
        TestBitStuffing testBitStuffing = new TestBitStuffing();
        Assert.assertEquals(testBitStuffing.testReceiverEmpty(), "Empty Data in Frame.");

        System.out.println("----- JUnit testReceiverEmpty() finished without error. -----");

    }
}