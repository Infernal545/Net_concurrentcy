import jdk.internal.util.xml.impl.ReaderUTF8;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by 14Krishin on 10.02.2017.
 */
public class Server {
    public static void main(String[] args) {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(2000); // поднимаем сервер
            Socket socket = serverSocket.accept();//accept - возвращает экземпляр клиента, который подключился к серверу
            InputStream soccetInputStream = socket.getInputStream(); // байтовый поток
            DataInputStream dataInputStream = new DataInputStream(soccetInputStream);
            String message = dataInputStream.readUTF();
            System.out.println(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
