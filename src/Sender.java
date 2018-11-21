import java.io.IOException;
import java.net.Socket;

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
            System.out.println("Mauvais nombre d'arguments");
            System.exit(0);
        }

        String serverAddress = args[0];
        String filename = args[2];

        int port = Integer.parseInt(args[1]);
        int connDemand = Integer.parseInt(args[3]);

        Socket socket = new Socket(serverAddress, port);
    }
}
