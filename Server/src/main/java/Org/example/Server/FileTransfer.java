package Org.example.Server;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public class FileReceiver {
    private Socket socket;
    int bufferSize;

    public FileReceiver(Socket client) {
        socket = client;
        bufferSize = 0;
    }
    public void receive (String fileName){
        try{
            InputStream is = socket.getInputStream();
            bufferSize = socket.getReceiveBufferSize();
            System.out.println("Buffer size = " + bufferSize);
            FileOutputStream fos = new FileOutputStream(fileName);
            BufferedOutputStream bos = new BufferedOutputStream(fos);
            byte[] bytes = new byte[bufferSize];
            int count;
            while ((count = is.read(bytes)) >= 0){
                bos.write(bytes, 0, count);
            }
            bos.close();
            is.close();
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
