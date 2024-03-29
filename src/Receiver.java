
/*
 * Travail fait par EID Alain et VOICULESCU Eduard.
 * Cours --- IFT-3325 : Téléinformatique --- Université de Montréal.
 * Travail remis à Zakaria Abou El Houda.
 */

import java.io.*;
import java.net.*;

public class Receiver extends Thread{

    /* Final variable associated to POLYNOME_GENERATEUR. */
    private static final String POLYNOME_GENERATEUR= "10001000000100001";

    /* Objects to use. */
    private CheckSum checkSum = new CheckSum();
    private static BitStuffing bitStuffingReceiver = new BitStuffing();
    private static CharacterConversion characterConversion = new CharacterConversion();
    private static ErrorTesting errorTesting = new ErrorTesting();

    /* Used for testing purposes. */
    boolean once = false;

    /* Receiver attributes */
    private int port;

    /**
     * Constructor of Receiver.
     * @param port : port number that Sender will connect to.
     * @throws IOException
     */
    public Receiver (int port) throws IOException {
        this.port = port;
        this.runReceiver();
    }

    /**
     * The functions below are only used to pretty print the Sender information.
     */
    private void delimiter(){
        System.out.println("####################################################################################################");
    }

    /**
     * Run the Receiver.
     * @throws IOException
     */
    public void runReceiver() throws IOException{

        try (
                ServerSocket serverSocket = new ServerSocket(port);
                Socket socket = serverSocket.accept();
                ObjectOutputStream os = new ObjectOutputStream(socket.getOutputStream());
                ObjectInputStream is = new ObjectInputStream(socket.getInputStream());
        ) {
            boolean endFile = false;
            int trameNumber = 0;
            String trameNumberToReceive;
            boolean trameCRCError = false; // used in the REJ case
            int window = 7;
            boolean wait = false;
            int frameError = -1;
            int frameDestroyed = -1;

            System.out.println("####################################################################################################");
            System.out.println("#                                        Receiver.java file                                        #");
            System.out.println("####################################################################################################");
            System.out.println("RECEIVER STATE : READY");
            System.out.println();

            while (!endFile) {

                Trame trame = (Trame) is.readObject();
                this.delimiter();

                /* Verify the Trame. */
                String type = bitStuffingReceiver.bitStuffingReceiver(trame.getType());
                String num = bitStuffingReceiver.bitStuffingReceiver(trame.getNum());
                String data = bitStuffingReceiver.bitStuffingReceiver(trame.getData());
                String crc = bitStuffingReceiver.bitStuffingReceiver(trame.getCrc());
                crc = type + num + data + crc;

                /*
                * Uncomment this to force a bit flip on the 00000010 trame and it does it once, so its
                * for the trame number 2 (we start counting at zero)
                */
//                if(num.equals("00000010") && !once){
//                    crc = errorTesting.bitShift(12, crc);
//                    once = true;
//                }

                boolean validCrc = checkSum.validateCRC(crc, POLYNOME_GENERATEUR);

                if(frameError != -1 && frameError != characterConversion.convertBinaryToDecimal(num)){
                    /* We have not received the good frame. */
                    continue;
                } else if (frameError == characterConversion.convertBinaryToDecimal(num)){
                    wait = false;
                    frameError = -1;
                }

                System.out.println("RECEIVER received a Trame (prettyPrint) : " + trame.prettyPrint());
                System.out.println("THE TRAME NUMBER IS " + trame.getIndexInArrayList());

                /* Uncomment for TimeOut Test. */
//                errorTesting.timeOut();

                if (validCrc){ // CRC is valid
                    if(!wait) {
                        if (characterConversion.binaryToChar(trame.getType()).equals("C")) {
                            /* I have read a connection request. */
                            System.out.println("Received a connection attempt ...");
                            System.out.println("Attempting to accept connection request ...");
                            Trame connectionAccepted = new Trame(characterConversion.charToBinary("A"), characterConversion.convertDecimalToBinary(0), "", POLYNOME_GENERATEUR, 0);
                            System.out.println("Sending accepting connection request trame (prettyPrint) ---> " + connectionAccepted.prettyPrint());
                            System.out.println("Sending accepting connection request trame ---> " + connectionAccepted.makeTrameFormat());
                            os.writeObject(connectionAccepted);
                            this.delimiter();
                            System.out.println();

                        } else if (characterConversion.binaryToChar(trame.getType()).equals("I")) {

                            /*
                             * Ici il y a deux cas :
                             * - Nous acceptons la trame
                             * - Nous n'avons pas recue la trame.
                             * */
                            trameNumberToReceive = characterConversion.convertDecimalToBinary(trameNumber);

                            /* We have to assure ourselves that the Trame to receive is the appropriate Trame. */
                            if (trameNumberToReceive.equals(num)) {

                                System.out.println("Received an information Trame ...");
                                int number = characterConversion.convertBinaryToDecimal(trame.getNum());
                                System.out.println("Trame number received ::: " + number + " ::: " + trame.getNum());
                                System.out.println("Sending an ACK ... ");
                                Trame connectionAccepted = new Trame(characterConversion.charToBinary("A"), characterConversion.convertDecimalToBinary(number), "", POLYNOME_GENERATEUR, trame.getIndexInArrayList());
                                System.out.println("Sending ACK trame ---> " + connectionAccepted.makeTrameFormat());
                                os.writeObject(connectionAccepted);
                                this.delimiter();
                                System.out.println();
                                trameNumber++; // on attend la prochaine trame

                                /* On est arrivé à la fin de la taille de notre fenêtre recommencer. */
                                if (trameNumber % (window + 1) == 0) {
                                    trameNumber = 0;
                                }

                            } else { // We have not received the appropriate Trame

                                System.out.println("ERROR ::: We have not received the appropriate Trame ::: ");
                                System.out.println("We have not received the Trame number " + trameNumber);

                                if (!once){
                                    frameDestroyed = trameNumber;
                                    once = true;
                                }

//                                Trame REJTrame = new Trame(characterConversion.charToBinary("R"), characterConversion.convertDecimalToBinary(trameNumber), "", POLYNOME_GENERATEUR, trame.getIndexInArrayList());
//                                os.writeObject(REJTrame);

                                this.delimiter();
                                System.out.println();
                            }

                        } else if (characterConversion.binaryToChar(trame.getType()).equals("P")) {

                            this.delimiter();

                            System.out.println("RECEIVER Received a Trame with P bit. ");
                            System.out.println("SENDING immediate ACK ::: ");
                            Trame pBitACKTrame = new Trame(characterConversion.charToBinary("A"), characterConversion.convertDecimalToBinary(trameNumber), "", POLYNOME_GENERATEUR, trame.getIndexInArrayList());
                            System.out.println("SENDING ACK Trame (prettyPrint) in response to P Bit Trame ::: " + pBitACKTrame.prettyPrint());
                            System.out.println("SENDING ACK Trame in response to P Bit Trame ::: " + pBitACKTrame.makeTrameFormat());
                            os.writeObject(pBitACKTrame);

                            this.delimiter();
                            System.out.println();

                        } else if (characterConversion.binaryToChar(trame.getType()).equals("F")) {
                            /* End of file and have to close connection. */
                            System.out.println("SENDER has closed the CONNECTION ... ");
                            System.out.println("CONNECTION CLOSED.");
                            this.delimiter();
                            endFile = true;
                        }
                    } else {

                        /* This frame contains an error. We will have to wait till we get back this frame. */
                        frameError = trame.getIndexInArrayList();

                        /* On attend les trames. */
                        System.out.println("Receiving Trame number ::: " + trame.getIndexInArrayList());
                        if (characterConversion.convertBinaryToDecimal(trame.getNum()) + 2 == window + trameNumber){
                            wait = false;
                            Trame connectionAccepted = new Trame(characterConversion.charToBinary("A"), characterConversion.convertDecimalToBinary(trameNumber), "", POLYNOME_GENERATEUR, trame.getIndexInArrayList());
                            os.writeObject(connectionAccepted);
                        }
                    }
                } else { // la trame n'est valid, le crc n'est pas valid

                    frameError = trame.getIndexInArrayList();

                    System.out.println("ERROR ::: INVALID CRC ::: The Trame we received contains an error ::: ");
                    System.out.println("SENDING ::: REJ Trame ... ");
                    System.out.println("RECEIVER will refuse any Trame until " + trame.getIndexInArrayList() + " is resolved. ");
                    /* By default we have put the Num field equal to the trame that had an error. */
                    Trame errorTrame = new Trame(characterConversion.charToBinary("R"), trame.getNum(), "", "", trame.getIndexInArrayList());
                    errorTrame.setError(true); // il y a une erreur dans cette trame
                    errorTrame.setCrc(checkSum.checkSumData(errorTrame.getType() + errorTrame.getNum() + errorTrame.getData(), POLYNOME_GENERATEUR));
                    os.writeObject(errorTrame);
                    wait = true;

                }

            }

        } catch (ClassNotFoundException | EOFException e) {
            if (e instanceof ClassNotFoundException)
                e.printStackTrace();

        } catch (IOException e) {
            System.err.println("ERROR ::: Buffer has Timed Out.");

        } finally {
            System.out.println();
        }

    }

    /**
     * Roule receveur
     */
    public static void main(String[] args) throws IOException {

        try {
            if (args.length == 0){ // no port was entered
                System.err.println("No argument was entered. Rerun the program and don't forget to only specify the port number.");
                System.exit(1);
            } else if (args.length > 1){
                System.err.println("More than 1 argument was specified. Program will now exit. Rerun the program and don't forget to only specify the port number.");
                System.exit(1);
            } else { // == 1
                Receiver receiver = new Receiver(Integer.parseInt(args[0]));
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
            System.out.println("Creating RECERVER object failed. Program will now exit.");
            System.exit(0);
        }
    }
}
