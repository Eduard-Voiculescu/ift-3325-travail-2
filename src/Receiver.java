import java.io.*;
import java.net.*;

public class Receiver extends Thread{

    private static final String POLYNOME_GENERATEUR= "10001000000100001";

    private CheckSum checkSum = new CheckSum();
    private static BitStuffing bitStuffingReceiver = new BitStuffing();
    private static CharacterConversion characterConversion = new CharacterConversion();

    boolean endFile = false;
    int trameNumber = 0;

    /* Receiver attributes */
    private int port;

    public Receiver (int port) throws IOException {
        this.port = port;
        this.runReceiver(this.port);
    }

    /* The functions below are only used to pretty print the Sender information. */
    private void delimiter(){
        System.out.println("####################################################################################################");
    }

    public void runReceiver(int port) throws IOException{

        try (
                ServerSocket serverSocket = new ServerSocket(port);
                Socket socket = serverSocket.accept();
                ObjectOutputStream os = new ObjectOutputStream(socket.getOutputStream());
                ObjectInputStream is = new ObjectInputStream(socket.getInputStream());
                PrintWriter out = new PrintWriter(socket.getOutputStream())
        ) {
            System.out.println("##############################  Receiver.java file ##############################");
            System.out.println("RECEIVER STATE : READY");

            while (true) {

                Trame trame = (Trame) is.readObject();
                if (characterConversion.binaryToChar(trame.getType()).equals("C")) {

                    this.delimiter();
                    System.out.println("Received a connection attempt ...");
                    System.out.println("Attempting to accept connection request ...");
                    Trame connectionAccepted = new Trame(characterConversion.charToBinary("A"), characterConversion.convertDecimalToBinary(0), "", POLYNOME_GENERATEUR, 0);
                    System.out.println("Sending accepting connection request trame ---> " + connectionAccepted.makeTrameFormat());
                    os.writeObject(connectionAccepted);
                    this.delimiter();

                } else if (characterConversion.binaryToChar(trame.getType()).equals("I")){

                    this.delimiter();
                    System.out.println("Received a connection attempt ...");
                    System.out.println("Attempting to accept connection request ...");
                    Trame connectionAccepted = new Trame(characterConversion.charToBinary("A"), characterConversion.convertDecimalToBinary(0), "", POLYNOME_GENERATEUR, 0);
                    System.out.println("Sending accepting connection request trame ---> " + connectionAccepted.makeTrameFormat());
                    os.writeObject(connectionAccepted);
                    this.delimiter();
                }

            }

        } catch (ClassNotFoundException | EOFException e) {
            if (e instanceof ClassNotFoundException)
                e.printStackTrace();

        } finally {
            System.out.println();
        }

    }

    /**
     * Roule receveur
     */
    public static void main(String[] args) throws IOException {

//        int port = Integer.parseInt(args[0]);
        try {
            Receiver receiver = new Receiver(Integer.parseInt(args[0]));
        } catch (Exception e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
            System.out.println("Creating RECERVER object failed. Program will now exit.");
            System.exit(0);
        }

    }
}
