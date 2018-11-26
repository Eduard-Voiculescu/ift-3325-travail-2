import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class Sender implements Serializable{

    private final static String POLYNOME_GENERATEUR = "10001000000100001";
    private static int timeout = 0; // This value will not exceed 3 (for 3 seconds)
    private static int window = 7; // This is not specified so we set the window size at 7

    /* Create objects to use. */
    private static BitStuffing bitStuffing = new BitStuffing();
    private static CharacterConversion characterConversion = new CharacterConversion();

    /* Sender attributes */
    private String machineName;
    private int portNumber;
    private String fileName;
    private int protocole;

    /**
     * Constructeur
     */
    private Sender(String machineName, int portNumber, String fileName, int protocole) {
        this.machineName = machineName;
        this.portNumber = portNumber;
        this.fileName = fileName;
        this.protocole = protocole;

        while (timeout < 3) {
            int result = createSocket(machineName, portNumber, fileName, protocole);

            /* Here we tried to create socket but there was an error.  */
            if (result == 0) {
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

                ObjectOutputStream os = new ObjectOutputStream(socket.getOutputStream());
                ObjectInputStream is = new ObjectInputStream(socket.getInputStream());

        ) {
            Trame trame = new Trame("P", "00000000", "", POLYNOME_GENERATEUR);
            // createTrames();
            trame.setType(characterConversion.charToBinary(trame.getType()));
            // readline des donnes et les mettre en bit
            // jai calculer le CRC
            // blablabla
            // Trame = une grosse sequence de bit
            os.writeObject(trame);
//            os.flush();
//            if (timeout == 0) {
//                // TODO : Create the frame
//                // TODO : Bitstuff the data
//                Trame trameToSend = new Trame("P", "00000000", "", POLYNOME_GENERATEUR);
//                System.out.println(trameToSend.makeTrameFormat());
//                String trameBitToSend = bitStuffing.bitStuffingSender(trameToSend.makeTrameFormat());
//
//                /*
//                * Num -> 8 bits -> 255 -> 11111111 reserver pour RR
//                *                         00000000 reserver pour REJ
//                *                         et le reste cest pour les trames
//                * Creer la trame -> |  FLAG  |   Type   |   Num   |   Data   |   CRC   |  FLAG  |
//                *                   |  1  |   1   |   1   |   0..n   |   2   |  1  | -> 6 octets min (48 bits)
//                *    |  01111110  |    I, C, A, R, F or P   |   Num   |   Coucou toto   |   CRC   |  01111110  |
//                *
//                * Switch:
//                *   CASE  I, C, A, R, F or P
//                *
//                *
//                *
//                * */
//
//
//                // SENDER --> TrameProcessorSender --> RECEIVER
//                // RECEIVER--> TrameProcessorReceiver --> SENDER
//                // SENDER check le num pour que cest ok
//                //  SI ACK ok on fait rien
//                //  SINON RENVOYER LA TRAME
//            }

            return 0; // Everything is OK
        } catch (Exception e) {
            return 1; // An error occurred
        }
    }

    /**
     * Create trames list from data
     *
     * @return ArrayList
     */
    private ArrayList<Trame> createTrames() throws FileNotFoundException {

        ArrayList<Trame> trames = new ArrayList<>(); // taille n

        ArrayList<String> data = readFile(fileName);


        for(int i = 0; i < data.size(); i++) {
            String type = "i";
            String num = "";
            String d = data.get(i);
            String crc = "";

            Trame trame = new Trame(type, num, d, crc);
            trames.add(trame);
        }

        // TODO: create trames from data and store in trames list
        // TODO: bitstuff the data in trames list

        convertToBin(trames);
        // Devrait-on calculer CRC ici ou dans Trame?

        return trames;
    }

    /**
     * Read a file line by line and store data in array
     *
     * @param fileName String
     * @return ArrayList of data
     * @throws FileNotFoundException
     */
    private ArrayList<String> readFile(String fileName) throws FileNotFoundException {
        ArrayList<String> data = new ArrayList<>();

        CharacterConversion converter = new CharacterConversion();

        BufferedReader in = new BufferedReader(new FileReader(fileName));
        String line;

        try {
            while ((line = in.readLine()) != null) {
                data.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return data;
    }

    /**
     * Convert trames to binary
     *
     * @param trames ArrayList
     * @return ArrayList
     */
    private ArrayList<String> convertToBin(ArrayList<Trame> trames) {

        ArrayList<String> binaryTrames = new ArrayList<>();

        CharacterConversion converter = new CharacterConversion();

        // TODO: convert trame from list to binary format

        return binaryTrames;
    }

    /**
     * Roule emetteur
     */
    public static void main(String[] args) throws IOException {

//        if (args.length != 4) {
//            System.err.println("Mauvais nombre d'arguments.");
//            System.exit(0);
//        }
//
//        String Nom_machine = args[0];
//        int port = Integer.parseInt(args[1]);
//        String filename = args[2];
//        int protocole = Integer.parseInt(args[3]);

        try {
//            Sender sender = new Sender(Nom_machine, port, filename, protocole);
            Sender sender = new Sender("127.0.0.1", 6969, "test.txt", 0);

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("There was a problem creating a sender object.");
            System.exit(0);
        }

    }
}
