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
                Socket socket = serverSocket.accept();
//                new TrameProcessorReceiver(serverSocket.accept()).start();
                try {
                    System.out.println("coucou");
                    ObjectOutputStream os = new ObjectOutputStream(socket.getOutputStream());
                    ObjectInputStream is = new ObjectInputStream(socket.getInputStream());
                    try {
                        String s = (String) is.readObject();
//                Trame trame = (Trame) is.readObject();
//                System.out.println(trame.makeTrameFormat() + " RECEIVED");
                        System.out.println(s);
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }

                } catch (IOException e) {

                }



            }

        } finally {
            serverSocket.close();
        }

    }
}
