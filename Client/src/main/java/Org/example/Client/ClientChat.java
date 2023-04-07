package Org.example.Client;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class ClientChat {
    public static void main(String[] args) throws IOException {
        try (Socket socket = new Socket("localhost", 8080)) {
            Scanner scanner = new Scanner(System.in);
            DataOutputStream os = new DataOutputStream(socket.getOutputStream());
            new Thread(new ServerThread(socket)).start();
            while (true) {
                System.out.println("Enter message:");
                String message = scanner.nextLine();
                if ("exit".equals(message)) {
                    break;
                }
                if (message.startsWith("file:")) {
                    String fileName = "C:\\Users\\Артур\\OneDrive\\Робочий стіл\\FileFromClient.txt";
                    File file = new File(fileName);
                    if (!file.exists() || !file.isFile()) {
                        System.out.println("Choose file ");
                        continue;
                    }
                    os.writeUTF("file_start");
                    os.writeUTF("file_name");
                    os.writeUTF(file.getName());
                    os.writeUTF("file_size");
                    os.writeLong(file.length());
                    os.writeUTF("file_content");
                    try (FileInputStream fis = FileUtils.openInputStream(file)) {
                        IOUtils.copy(fis, os);
                    } finally {
                        System.out.println("Uploaded");
                    }
                    os.writeUTF("file_finish");
                } else {
                    os.writeUTF(message);
                }
                os.flush();
            }
        }
    }
}

