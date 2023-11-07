import java.io.*;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public enum Client {
    INSTANCE;

    private Socket socket;
    private DataInputStream input;
    private DataOutputStream out;

    public void startClient(String address, int port) {

        try {
            socket = new Socket(address, port);
            System.out.println("Connected");
            input = new DataInputStream(System.in);
            out = new DataOutputStream(socket.getOutputStream());
        } catch (UnknownHostException unknownHostException) {
            System.out.println("Exception" + unknownHostException);
            return;

        } catch (IOException e) {
            System.out.println("Exception:" + e);
            return;
        }
        String line = "";

        while (!line.equals("STOP")) {
            try {
                line = input.readLine();
                out.writeUTF(line);
            } catch (IOException e) {
                System.out.println("Exception:" + e);
            }
        }

        try {
            input.close();
            out.close();
            socket.close();
        } catch (IOException e) {
            System.out.println("Exception:" + e);
        }
    }
    public static void main(String[] args) {
        Client client = INSTANCE;
        client.startClient("127.0.0.1", 5000);
    }
}