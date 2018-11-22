import java.io.IOException;
import java.net.*;

public class Sender {

    /**
     * Constructeur
     */
    public Sender() {
    }

    /**
     * Roule emetteur
     */
    public static void main(String[] args) throws IOException {

        if (args.length != 4) {
            System.err.println("Mauvais nombre d'arguments.");
            System.exit(0);
        }

        String serverAddress = args[0];
        String filename = args[2];

        int port = Integer.parseInt(args[1]);
        int connDemand = Integer.parseInt(args[3]);

        try {
            Socket socket = new Socket(serverAddress, port);

        } catch (SocketException sockEx){
            sockEx.printStackTrace();
            System.err.println("There was a problem opening a socket.");
            System.exit(0);
        }

    }
}
