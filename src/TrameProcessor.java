import java.net.Socket;

public class TrameProcessor extends Thread {

    private Socket socket;

    /**
     * Constructeur
     *
     * @param socket Socket
     */
    TrameProcessor(Socket socket) {
        this.socket = socket;
    }

    public void run() {

    }

}
