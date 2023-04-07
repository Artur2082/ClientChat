package Org.example.Client;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;


public class ServerThread implements Runnable{
    private final Socket socket;
    public ServerThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try (DataInputStream is = new DataInputStream(socket.getInputStream())) {
            while (true) {
                String response = is.readUTF();
                if ("bye".equals(response) ) {
                    break;
                }
                System.out.println("Server: " + response);
            }
        } catch (IOException ignored) {}
        }
    }
