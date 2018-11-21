import java.io.IOException;
import java.net.ServerSocket;

public class Receiver {

    /**
     * Roule receveur
     */
    public static void main(String[] args) throws IOException {

        int port = Integer.parseInt(args[0]);

        ServerSocket serverSocket = new ServerSocket(port);

    }
}
