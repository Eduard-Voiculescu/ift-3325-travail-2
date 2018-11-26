import java.io.*;
import java.net.Socket;

public class TrameProcessorReceiver extends Thread {

    private Socket socket;
    private String polynomeGen = "10001000000100001";

    private CheckSum checkSum = new CheckSum();

    /**
     * Constructeur
     *
     * @param socket Socket
     */
    TrameProcessorReceiver(Socket socket) {
        this.socket = socket;
    }

    public void run() {

        try {

            ObjectOutputStream os = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream is = new ObjectInputStream(socket.getInputStream());
            PrintWriter out = new PrintWriter(socket.getOutputStream());

            try {

                while (true) {

                    /*
                    Je suis pas certain du while true ici
                    On lit plusieurs trames dans un meme thread?
                    http://cs.lmu.edu/~ray/notes/javanetexamples/
                     */

                    Trame trame = (Trame) is.readObject();
                    System.out.println(trame.makeTrameFormat());

                    if (isEndOfTransmission(trame.getType())) {

                        System.out.println("Receveur : Fin de la transmission");
                        break;

                    } else {

                        if (hasError(trame)) {
                            // TODO: send REJ
                        } else {
                            // TODO: send ACK
                        }

                    }

                }


            } catch (ClassNotFoundException | EOFException e) {
                if (e instanceof ClassNotFoundException)
                    e.printStackTrace();
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {

            try {
                socket.close();
            } catch (IOException e) {
                System.out.println("Receiver : Echec de fermeture du socket");
            }
        }
    }

    /**
     * Checks if transmission is over by looking for type "f"
     *
     * @param type String
     * @return boolean
     */
    private boolean isEndOfTransmission(String type) {
        return new CharacterConversion().binaryToChar(type).equals("f");
    }

    /**
     * Checks if trame contains an error
     *
     * @param trame Trame
     * @return boolean
     */
    private boolean hasError(Trame trame) {
        return checkSum.validateCRC(polynomeGen, trame.getCrc());
    }
}
