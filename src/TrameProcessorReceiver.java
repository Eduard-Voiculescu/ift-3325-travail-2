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
            System.out.println("coucou");
            ObjectOutputStream os = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream is = new ObjectInputStream(socket.getInputStream());
            try {
                String s = (String) is.readObject();
//                Trame trame = (Trame) is.readObject();
//                System.out.println(trame.makeTrameFormat() + " RECEIVED");
                System.out.println(s);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

        } catch (IOException e) {

        }

    }

}
