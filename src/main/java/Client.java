import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

@Slf4j
public enum Client {
    INSTANCE;

    private Socket socket;
    private DataInputStream input;
    private DataOutputStream out;

    public void startClient(String address, int port) {

        try {
            socket = new Socket(address, port);
           log.info("Connected");
            input = new DataInputStream(System.in);
            out = new DataOutputStream(socket.getOutputStream());
        } catch (UnknownHostException unknownHostException) {
            log.error("Exception" + unknownHostException);
            return;

        } catch (IOException e) {
            log.error("Exception:" + e);
            return;
        }
        String line = "";

        while (!line.equals("STOP")) {
            try {
                line = input.readLine();
                out.writeUTF(line);
            } catch (IOException e) {
               log.error("Exception:" + e);
            }
        }

        try {
            input.close();
            out.close();
            socket.close();
        } catch (IOException e) {
            log.error("Exception:" + e);
        }
    }
    public static void main(String[] args) {
        Client client = INSTANCE;
        client.startClient("127.0.0.1", 5000);
    }
}