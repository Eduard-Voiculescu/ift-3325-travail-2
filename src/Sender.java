import java.io.*;
import java.net.*;
import java.sql.SQLOutput;
import java.util.ArrayList;

public class Sender implements Serializable{

    private final static String POLYNOME_GENERATEUR = "10001000000100001";
    private static int timeout = 0; // This value will not exceed 3 (for 3 seconds)
    private static int window = 7; // This is not specified so we set the window size at 7

    /* Create objects to use. */
    private static BitStuffing bitStuffing = new BitStuffing();
    private static CharacterConversion characterConversion = new CharacterConversion();
    private static CheckSum checkSum = new CheckSum();

    /* Sender attributes */
    private String machineName;
    private int portNumber;
    private String fileName;
    private String protocole;

    /* Lists pour les trames. */
    private ArrayList<Trame> trames;

    /* Variables */
    boolean connected = false;
    int numberOfTrameSent = 0;
    int currentPositionTrame = 0;
    int currentPositionWindow = 0;

    /**
     * Constructeur
     */
    private Sender(String machineName, int portNumber, String fileName, String protocole) {
        this.machineName = machineName;
        this.portNumber = portNumber;
        this.fileName = fileName;
        this.protocole = protocole;


        int result = createSocket(machineName, portNumber, fileName, protocole);
//        while (timeout < 3) {
//            int result = createSocket(machineName, portNumber, fileName, protocole);
//
//            /* Here we tried to create socket but there was an error.  */
//            if (result == 0) {
//                timeout += 1;
//            }
//        }

    }

    private int createSocket(String machineName, int portNumber, String fileName, String protocole) {

        /*
        * https://docs.oracle.com/javase/tutorial/essential/exceptions/tryResourceClose.html
        * Here we give our try block resources. This means that once try is ended or and exception is caught
        * the resources will also be freed.
        */
        try (
                Socket socket = new Socket(machineName, portNumber);
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

                ObjectOutputStream os = new ObjectOutputStream(socket.getOutputStream());
                ObjectInputStream is = new ObjectInputStream(socket.getInputStream())

        ) {

            System.out.println("##############################  Sender.java file ##############################");

            System.out.println("SENDER STATE : READY");
            this.delimiter();

            if (timeout > 0){
                Trame firstTrame = new Trame("P", characterConversion.convertDecimalToBinary(0), "", POLYNOME_GENERATEUR, 0);
            }

            /* We create all the Trame objects and put them in an ArrayList<Trames> */
            trames = createTrames(fileName);

            while (numberOfTrameSent < trames.size()){
                System.out.println("Trame confirmed sent -> " + numberOfTrameSent);

                /* Entrer ici pour la première fois. Envoyer la trame de demande de connexion. */
                if (!connected){
                    System.out.println("Attempting to connect ...");
//                    socket.setSoTimeout(3000); // Temporisateur de 3 secondes.
                    Trame connectionTrame = new Trame(characterConversion.charToBinary("C"), characterConversion.convertDecimalToBinary(0), "", POLYNOME_GENERATEUR, 0);
                    System.out.println("Sending connectionTrame to receiver ---> "
                            + bitStuffing.bitStuffingSender(connectionTrame.makeTrameFormat()));
                    os.writeObject(connectionTrame); // Send trame to Receiver
                    // Imprimer la reponse du receveur.
                    Trame answer = (Trame) is.readObject();
                    System.out.println("Trame received by Receiver ... " + answer.makeTrameFormat());

                    /* Verification if appropriate Trame reecived. */
                    if (characterConversion.binaryToChar(answer.getType()).equals("A")){
                        System.out.println("Connection was accepted by Receiver ...");
                        System.out.println("CONNECTION ESTABLISHED ... ");
                        this.delimiter();
                        System.out.println();
                        timeout = 0;
                        connected = true;


                    }
                } else { // we are now connected

                    /* Because our window size is of 7 we have to do another while loop. Complexity-wise could be done in a better. */
                    while (currentPositionWindow <= window && currentPositionTrame <= trames.size()) {
                        this.delimiter();
                        System.out.println("SENDING Trame Information ... ");
                        Trame trameToSend = trames.get(currentPositionTrame);
                        System.out.println("SENDING Trame number ---> " + Integer.toString(trameToSend.getIndexInArrayList()));
                        /*
                        * On transforme toute les données en bits. Data est déjà fait lorsque nous avons créer notre ArrayList<Trame>.
                        * Nous allons egalement bitstuff toute les donnees pour que le tout soit ok.
                        */
                        trameToSend.setType(bitStuffing.bitStuffingSender(trameToSend.getType()));
                        trameToSend.setNum(bitStuffing.bitStuffingSender(characterConversion.convertDecimalToBinary(Integer.parseInt(trameToSend.getNum()))));
                        trameToSend.setData(bitStuffing.bitStuffingSender(trameToSend.getData()));
                        trameToSend.setCrc(bitStuffing.bitStuffingSender(POLYNOME_GENERATEUR)); // TODO : Change this to calculate CRC.
                        System.out.println("Trame to send to Receiver ---> " + trameToSend.makeTrameFormat());
                        System.out.println("Window size left to send Trame ---> " + Integer.toString(window - currentPositionWindow));
                        currentPositionTrame++;
                        currentPositionWindow++;
                        this.delimiter();
                        System.out.println();
                        os.writeObject(trameToSend); // Send Trame to Receiver
                        Trame answer = (Trame) is.readObject();
                    }
                    break;

                }


            }


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
    private ArrayList<Trame> createTrames(String fileName) throws IOException {

        ArrayList<Trame> trames = new ArrayList<>(); // taille n

        /* This list contains all the information from a txt file.
         * The format is as such that the line number is respectfully
         * the ArrayList<String> data.
         */
        ArrayList<String> data = readFile(fileName);

        for(int i = 0; i < data.size(); i++) {

            /* Convert all the data in binary format. */
            data.set(i, characterConversion.charToBinary(data.get(i)));
//            String crc = checkSum.checkSumData();


            Trame trame = new Trame(characterConversion.charToBinary("I"),
                    String.valueOf(i%8),
                    data.get(i),
                    "",
                    i);
            trames.add(trame);
        }

        // Devrait-on calculer CRC ici ou dans Trame?

        return trames;
    }

    /**
     * Read a file line by line and store data in array
     *
     * @param fileName String
     * @return ArrayList of data
     * @throws IOException
     */
    private ArrayList<String> readFile(String fileName) throws IOException {
        ArrayList<String> dataTable = new ArrayList<>();
        try {
            File file = new File(fileName);
            String path = file.getAbsolutePath();
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(path), "UTF-8"));
            String line;
            while ((line = br.readLine()) != null) {
                dataTable.add(line);
            }
            br.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return dataTable;
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

    /* Convert decimal value to string. */
    private String decimalToBinary(int decimal){
        return Integer.toBinaryString(decimal);
    }

    /* The functions below are only used to pretty print the Sender information. */
    private void delimiter(){
        System.out.println("####################################################################################################");
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
//        String protocole = args[3];
//        if(!protocole.equals("0")){
//            System.out.println("The protocole specified is not Go-Back-N.");
//            System.out.println("The program will now exit.");
//            System.exit(0);
//        }

        try {
//            Sender sender = new Sender(Nom_machine, port, filename, protocole);
            Sender sender = new Sender("127.0.0.1", 6969, "Lorem.txt", "0");

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("There was a problem creating a sender object.");
            System.exit(0);
        }

    }
}
