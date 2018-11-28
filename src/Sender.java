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
    int numberOfTrameConfirmed = 0;
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

        while (timeout < 3) {
            int result = createSocket(machineName, portNumber, fileName, protocole);

            /* Here we tried to create socket but there was an error.  */
            if (result == 0) {
                timeout += 1;
            }
        }

    }

    private int createSocket(String machineName, int portNumber, String fileName, String protocole) {

        /*
        * https://docs.oracle.com/javase/tutorial/essential/exceptions/tryResourceClose.html
        * Here we give our try block resources. This means that once try is ended or and exception is caught
        * the resources will also be freed.
        */
        try (
                Socket socket = new Socket(machineName, portNumber);
                ObjectOutputStream os = new ObjectOutputStream(socket.getOutputStream());
                ObjectInputStream is = new ObjectInputStream(socket.getInputStream())

        ) {

            System.out.println("####################################################################################################");
            System.out.println("#                                         Sender.java file                                         #");
            System.out.println("####################################################################################################");

            System.out.println("SENDER STATE : READY");
            System.out.println("Text file to read ::: " + fileName);
            System.out.println();

            this.delimiter();

            if (timeout > 0){
                System.out.println("CONNECTION TIMEOUT ...");
                Trame connectionTimedOut = new Trame("P", characterConversion.convertDecimalToBinary(0), "", POLYNOME_GENERATEUR, 0);
                System.out.println("Trame to send to Receiver ... " + connectionTimedOut.prettyPrint());
                System.out.println("Sending connectionTimedOut to receiver ---> " + connectionTimedOut.makeTrameFormat());
                os.writeObject(connectionTimedOut);
            }

            /* We create all the Trame objects and put them in an ArrayList<Trames> */
            trames = createTrames(fileName);

            while (numberOfTrameConfirmed < trames.size()){

                /* Entrer ici pour la première fois. Envoyer la trame de demande de connexion. */
                if (!connected){
                    System.out.println("Attempting to connect ...");
//                    socket.setSoTimeout(3000); // Temporisateur de 3 secondes. -- Comment this line to be able to debug Receiver.
                    Trame connectionTrame = new Trame(characterConversion.charToBinary("C"), characterConversion.convertDecimalToBinary(255), "", "", 0);
                    connectionTrame.setCrc(checkSum.checkSumData(connectionTrame.getType() + connectionTrame.getNum() + connectionTrame.getData(), POLYNOME_GENERATEUR));
                    System.out.println("Trame to send to Receiver ... " + connectionTrame.prettyPrint());
                    System.out.println("Sending connectionTrame to receiver ---> " + connectionTrame.makeTrameFormat());
                    os.writeObject(connectionTrame); // Send trame to Receiver
                    // Imprimer la reponse du receveur.
                    Trame answer = (Trame) is.readObject();
                    System.out.println("Trame received by Receiver (prettyPrint) ::: " + answer.prettyPrint());
                    System.out.println("Trame received by Receiver ::: " + answer.makeTrameFormat());

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
                    System.out.println("Trame confirmed sent -> " + numberOfTrameConfirmed);
                    /* Because our window size is of 7 we have to do another while loop. Complexity-wise could be done in a better. */
                    while (currentPositionWindow < window && currentPositionTrame < trames.size()) {
                        this.delimiter();
                        System.out.println("Window size left before sending Trame : " + Integer.toString(window - currentPositionWindow));
                        System.out.println("SENDING Trame Information ... ");
                        Trame trameToSend = trames.get(currentPositionTrame);
                        System.out.println("SENDING Trame number ---> " + Integer.toString(trameToSend.getIndexInArrayList()));
                        /*
                        * On transforme toute les données en bits. Data est déjà fait lorsque nous avons créer notre ArrayList<Trame>.
                        * Nous allons egalement bitstuff toute les donnees pour que le tout soit ok.
                        */
//                        trameToSend.setType(bitStuffing.bitStuffingSender(trameToSend.getType()));
                        trameToSend.setNum(characterConversion.convertDecimalToBinary(Integer.parseInt(trameToSend.getNum())));
//                        trameToSend.setData(bitStuffing.bitStuffingSender(trameToSend.getData()));
                        trameToSend.setCrc(checkSum.checkSumData(trameToSend.getType()+trameToSend.getNum()+trameToSend.getData(), POLYNOME_GENERATEUR));

                        System.out.println("Trame to send to Receiver ---> " + trameToSend.prettyPrint());
                        System.out.println("SENDING Trame ::: " + trameToSend.makeTrameFormat());
                        System.out.println("Window size left to send Trame ---> " + Integer.toString(window - currentPositionWindow - 1));

                        currentPositionTrame++;
                        currentPositionWindow++;

                        this.delimiter();
                        System.out.println();

                        os.writeObject(trameToSend); // Send Trame to Receiver
                    }
                    /* We get out of the while loop once the window quota has been reached. */
                    Trame answer = (Trame) is.readObject();
                    this.delimiter();
                    System.out.println("RECEIVED trame from Receiver ...");
                    System.out.println("Trame received (pretty print)" + answer.prettyPrint());
                    System.out.println("Trame received ---> " + answer.makeTrameFormat());
                    timeout = 0; // reset timer

                    /* Have to check the trame that we received.  */
                    int trameReceived = characterConversion.convertBinaryToDecimal(answer.getNum());

                    /* We have received an ACK. */
                    if(characterConversion.binaryToChar(answer.getType()).equals("A")){
                        numberOfTrameConfirmed++;
                        System.out.println("RECEIVED ACK from Receiver ::: Trame number ---> " + answer.getIndexInArrayList());
                        System.out.println("RECEIVED ACK from Receiver (Num associated to window) ::: " + trameReceived + " ::: " + answer.getNum());

                        currentPositionWindow--; // On peut envoyer une trame de plus.
                        this.delimiter();
                        System.out.println();

                        /* We have received a REJ. */
                    } else if (characterConversion.binaryToChar(answer.getType()).equals("R")){
                        // TODO : Implement when we receive a R -> REJECT
                        System.out.println("coucou");
                    }

                }


            }
            /* Here we send the closing connection Trame. */
            this.delimiter();
            System.out.println("ATTEMPTING TO DISCONNECT ...");
            Trame disconnected = new Trame(characterConversion.charToBinary("F"), characterConversion.convertDecimalToBinary(255), "", "", 0);
            disconnected.setCrc(checkSum.checkSumData(disconnected.getType() + disconnected.getNum() + disconnected.getData(), POLYNOME_GENERATEUR));
            System.out.println("Trame to send to Receiver ... " + disconnected.prettyPrint());
            System.out.println("Sending disconnected Trame to receiver ---> " + disconnected.makeTrameFormat());
            this.delimiter();

            os.writeObject(disconnected); // Send trame to Receiver

            System.exit(0);

        } catch (IOException e) {
            System.err.println("Temporisateur time has been exceeded. Buffer Timed Out. Machine : " +
                    machineName + " is not responding.");
            System.err.println("We will send out a PBit.");
            return 0; // An error occurred

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.exit(-1);
        }
        return 1;
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
                    String.valueOf(i % 8),
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
//            Sender sender = new Sender("127.0.0.1", 6969, "Lorem.txt", "0");
//            Sender sender = new Sender("127.0.0.1", 6969, "Witcher3_The_Wtichers.txt", "0");
            Sender sender = new Sender("127.0.0.1", 6969, "Witcher3_The_World_Lore.txt", "0");

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("There was a problem creating a sender object.");
            System.exit(0);
        }

    }
}
