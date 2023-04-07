import Org.example.Server.ServerChat;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerChatTest {

    @Test
    public void isNotNull() throws IOException {
        ServerSocket serverSocket = new ServerSocket(8080);
        Socket socket = new Socket("localhost", 8080);
        serverSocket.accept();
        File file = ServerChat.saveConnection("Artur",socket);
        Assertions.assertNotNull(file);
        socket.close();
    }
}
