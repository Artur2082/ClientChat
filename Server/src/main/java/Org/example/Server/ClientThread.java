package Org.example.Server;


import java.io.*;
import java.net.Socket;

public class ClientThread implements Runnable {
    private final Socket socket;
    public ClientThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            DataInputStream dataInput = new DataInputStream(socket.getInputStream());
            DataOutputStream dataOut = new DataOutputStream(socket.getOutputStream());
            while (true) {
                String message = dataInput.readUTF();
                if (message == null) {
                    continue;
                }
                System.out.println("Received message : " + " - " + message);
                if ("file_start".equals(message)) {
                    long fileSize = -1;
                    String fileName;
                    File uploadedFile = null;
                    boolean fileUploadFinished = false;
                    while (!fileUploadFinished) {
                        String fileMessage = dataInput.readUTF();
                        switch (fileMessage) {
                            case "file_size":
                                fileSize = dataInput.readLong();
                                System.out.println("file size: " + fileSize);
                                break;
                            case "file_name":
                                fileName = dataInput.readUTF();
                                System.out.println("file name: " + fileName);
                                uploadedFile = new File("." + File.separator + "files" + File.separator + fileName);
                                uploadedFile.getParentFile().mkdirs();
                                break;
                            case "file_finish":
                                fileUploadFinished = true;
                                break;
                            case "file_content":
                                if (uploadedFile == null) {
                                    System.out.println("uploaded file is null");
                                    continue;
                                }
                                FileTransfer fileTransfer = new FileTransfer(socket);
                                fileTransfer.receive(uploadedFile);
                                break;
                        }
                    }
                } else {
                    dataOut.writeUTF("Received");
                    dataOut.flush();
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(" Client went out from chat ");
        } finally {
            try {
                socket.close();
            } catch (IOException ignored) {
            }
        }
    }
}
