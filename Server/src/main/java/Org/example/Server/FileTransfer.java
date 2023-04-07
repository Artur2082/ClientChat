package Org.example.Server;

import org.apache.commons.io.FileUtils;

import java.io.*;
import java.net.Socket;

public class FileTransfer {
    private Socket socket;
    int bufferSize;

    public FileTransfer(Socket client) {
        socket = client;
        bufferSize = 0;
    }
    public void receive (File uploadedFile) throws IOException {
        try (FileOutputStream fos = FileUtils.openOutputStream(uploadedFile)) {
            InputStream is = socket.getInputStream();
            BufferedOutputStream bos = new BufferedOutputStream(fos);
            bufferSize = socket.getReceiveBufferSize();
            byte[] bytes = new byte[bufferSize];
            int file = is.read(bytes, 0, bytes.length);
            bos.write(bytes, 0, file);
            bos.close();
        } finally {
            System.out.println("Uploaded");
        }
    }
}
