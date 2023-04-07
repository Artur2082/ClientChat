package Org.example.ClientChat;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class ClientChat {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost", 8080);
        Scanner scanner = new Scanner(System.in);
        try {
            //DataInputStream is = new DataInputStream(socket.getInputStream());
            DataOutputStream os = new DataOutputStream(socket.getOutputStream());
            Thread thread = new Thread(new ClientThread(socket));
            thread.start();
            while (true) {
                System.out.println("Enter message:");
                String message = scanner.nextLine();
                if ("exit".equals(message)) {
                    break;
                }
                os.writeUTF(message);
                os.flush();
            }

        } finally {
            socket.close();
        }
    }
}

