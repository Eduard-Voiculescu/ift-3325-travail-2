import java.io.*;
import java.net.Socket;

public class TrameProcessor extends Thread {

    private Socket socket;

    /**
     * Constructeur
     *
     * @param socket Socket
     */
    TrameProcessor(Socket socket) {
        this.socket = socket;
    }

    public void run() {

        try {

            ObjectInputStream is = new ObjectInputStream(socket.getInputStream());
            ObjectOutputStream os = new ObjectOutputStream(socket.getOutputStream());

            try {
                Trame trame = (Trame) is.readObject();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

        } catch (IOException e) {

        }

    }

}
