import java.io.*;
import java.net.Socket;

public class TrameProcessorReceiver extends Thread {

    private Socket socket;

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
            try {
                Trame trame = (Trame) is.readObject();
                System.out.println(trame.makeTrameFormat() + " RECEIVED");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

        } catch (IOException e) {

        }

    }

}
