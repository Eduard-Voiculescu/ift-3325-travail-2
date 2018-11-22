import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;

public class Sender {

    private final static String POLYNOME_GENERATEUR = "10001000000100001";
    private static int timeout = 0; // This value will not exceed 3 (for 3 seconds)
    private static int window = 7; // This is not specified so we set the window size at 7

    /* Sender attributes */
    private String machineName;
    private int portNumber;
    private String fileName;
    private int protocole;

    /**
     * Constructeur
     */
    public Sender(String machineName, int portNumber, String fileName, int protocole) {
        this.machineName = machineName;
        this.portNumber = portNumber;
        this.fileName = fileName;
        this.protocole = protocole;

        while (timeout < 3) {
            int result = createSocket(machineName, portNumber, fileName, protocole);

            /* Here we tried to create socket but there was an error.  */
            if(result == 0) {
                timeout += 1;
            }
        }

    }

    private int createSocket(String machineName, int portNumber, String fileName, int protocole) {

        /*
        * https://docs.oracle.com/javase/tutorial/essential/exceptions/tryResourceClose.html
        * Here we give our try block resources. This means that once try is ended or and exception is caught
        * the resources will also be freed.
        */
        try (
                Socket socket = new Socket(machineName, portNumber);
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(
                                new InputStreamReader(socket.getInputStream()))
        ){
            if (timeout > 0){
                // TODO : Create the frame
                // TODO : Bitstuff the data
            }

            return 1; // Everything is OK
        } catch (Exception e){
            return 0; // An error occurred
        }



    }







    /**
     * Roule emetteur
     */
    public static void main(String[] args) throws IOException {

        if (args.length != 4) {
            System.err.println("Mauvais nombre d'arguments.");
            System.exit(0);
        }

        String Nom_machine = args[0];
        int port = Integer.parseInt(args[1]);
        String filename = args[2];
        int protocole = Integer.parseInt(args[3]);

        try {

            Sender sender = new Sender(Nom_machine, port, filename, protocole);

        } catch (Exception e){
            e.printStackTrace();
            System.err.println("There was a problem creating a sender object.");
            System.exit(0);
        }

    }
}
