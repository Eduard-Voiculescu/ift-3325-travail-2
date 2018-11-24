import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.*;

public class Receiver {

    private final static String POLYNOME_GENERATEUR = "10001000000100001";

    /**
     * Roule receveur
     */
    public static void main(String[] args) throws IOException {

//        int port = Integer.parseInt(args[0]);
        int port = 6969;
        ServerSocket serverSocket = new ServerSocket(port);

        try{

            while(true) {

                new TrameProcessorReceiver(serverSocket.accept()).start();

            }

        } finally {
            serverSocket.close();
        }

    }
}
