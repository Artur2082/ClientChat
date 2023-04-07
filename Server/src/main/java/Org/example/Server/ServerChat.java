package Org.example.Server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalTime;
import java.util.Random;

public class ServerChat {

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(8080)) {
            while (true) {
                Socket socket = serverSocket.accept();
                Random random = new Random();
                DataOutputStream dataOut = new DataOutputStream(socket.getOutputStream());
                String clientName = "Client-" + random.nextInt(10);
                dataOut.writeUTF(clientName + " Connected");
                System.out.println(clientName + " connected ");
                saveConnection(clientName, socket);
                new Thread(new ClientThread(socket)).start();
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public static File saveConnection(String clientName, Socket socket) throws IOException {
        File connections = new File("Connections");
        FileOutputStream fos = new FileOutputStream("Connections", true);
        String write = clientName + " - Time - " + LocalTime.now() + " - " + socket + '\n';
        byte[] bytes = write.getBytes();
        fos.write(bytes);
        fos.close();
        return connections;
    }
}
