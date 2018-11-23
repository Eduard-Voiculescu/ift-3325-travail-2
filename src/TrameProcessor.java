import java.io.IOException;
import java.net.Socket;

public class TrameProcessor extends Thread {

    private Socket socket;

    TrameProcessor(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
