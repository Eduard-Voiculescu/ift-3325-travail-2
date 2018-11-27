import java.io.*;
import java.net.*;

public class Receiver extends Thread{

    private static final String POLYNOME_GENERATEUR= "10001000000100001";

    private CheckSum checkSum = new CheckSum();
    private static BitStuffing bitStuffingReceiver = new BitStuffing();
    private static CharacterConversion characterConversion = new CharacterConversion();

    /**
     * Roule receveur
     */
    public static void main(String[] args) throws IOException {

//        int port = Integer.parseInt(args[0]);
        int port = 6969;

        try (
                ServerSocket serverSocket = new ServerSocket(port);
                Socket socket = serverSocket.accept();
                ObjectOutputStream os = new ObjectOutputStream(socket.getOutputStream());
                ObjectInputStream is = new ObjectInputStream(socket.getInputStream());
                PrintWriter out = new PrintWriter(socket.getOutputStream())
        ) {
            System.out.println("RECEIVER STATE : READY");

            while (true) {

                /*
                Je suis pas certain du while true ici
                On lit plusieurs trames dans un meme thread?
                http://cs.lmu.edu/~ray/notes/javanetexamples/
                 */

                Trame trame = (Trame) is.readObject();
                if (characterConversion.binaryToChar(trame.getType()).equals("C")) {
                    System.out.println("Received a connection attempt ...");
                    System.out.println("Attempting to accept connection request ...");
                    Trame connectionAccepted = new Trame(characterConversion.charToBinary("A"), "00000000", "", POLYNOME_GENERATEUR);
                    System.out.println("Sending accepting connection request trame ---> " + connectionAccepted.makeTrameFormat());
                    os.writeObject(connectionAccepted);
                }


            }


        } catch (ClassNotFoundException | EOFException e) {
            if (e instanceof ClassNotFoundException)
                e.printStackTrace();

        } finally {
            System.out.println();
        }

    }
}
