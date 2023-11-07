import com.google.gson.Gson;

import java.net.*;
import java.io.*;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

public enum Server {
    INSTANCE;

    private Socket socket;
    private ServerSocket serverSocket;
    private DataInputStream dataInputStream;
    private Gson gson;
    private LocalDateTime serverStartTime;
    public static final String HELP = "HELP";
    public static final String UPTIME = "UPTIME";
    public static final String INFO = "INFO";
    public static final String STOP = "STOP";
    public static final String VERSION = "0.1.0";
    public static final LocalDate timeOfCreate = LocalDate.of(2000, 10, 1);

    public void startServer(int port) {

        try {
            serverSocket = new ServerSocket(port);
            serverStartTime = LocalDateTime.now();
            System.out.println("Server started.");
            System.out.println("I'am waiting for client.....");
            socket = serverSocket.accept();
            System.out.println("Client accepted.");
            dataInputStream = new DataInputStream(new BufferedInputStream(socket.getInputStream()));

            getRequestFromServer();

        } catch (IOException i) {
            System.out.println("Exception" + i);
        }
    }

    public void getRequestFromServer() {

        String line = "";
        while (Objects.nonNull(line)) {
            try {
                line = dataInputStream.readUTF();

                switch (line) {
                    case STOP:
                        stop();
                        System.out.println("Connection was terminated");
                        return;
                    case UPTIME:
                        upTime();
                        continue;
                    case INFO:
                        info();
                        continue;
                    case HELP:
                        getHelp();
                        continue;
                    default:
                        throw new IllegalArgumentException("If you need help, write - HELP");
                }
            } catch (IOException i) {
                System.out.println("Exception" + i);
            }
        }
    }

    public void stop() throws IOException {
        dataInputStream.close();
        serverSocket.close();
        socket.close();
    }

    public void getHelp() {

        String response =
                "available command:" +
                        " \"uptime\" - returns the server's uptime\n" +
                        "\"info\" - returns the server's version number and creation date\n" +
                        "\"help\" - returns a list of available commands\n" +
                        "\"stop\" - simultaneously stops the server and the client";
        converterToJson(response);
    }

    public void upTime() {
        LocalDateTime now = LocalDateTime.now();
        Duration duration = Duration.between(serverStartTime, now);
        long seconds = duration.getSeconds();
        System.out.println("Server life times[s]:");
        converterToJson(seconds);
    }

    public void info() {
        System.out.println("Version app:");
        converterToJson(VERSION);
        System.out.println("Create date:");
        converterToJson(timeOfCreate.toString());
    }

    public void converterToJson(Object object) {
        gson = new Gson();
        String response = gson.toJson(object);
        System.out.println(response);
    }

    public static void main(String[] args) {
        Server server = INSTANCE;
        server.startServer(5000);
    }
}
