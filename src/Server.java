import jdk.internal.util.xml.impl.ReaderUTF8;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by 14Krishin on 10.02.2017.
 */
public class Server {
    public static void main(String[] args) {
        ServerSocket serverSocket = null;
        try {
            int port = Integer.parseInt(args[1]);
            serverSocket = new ServerSocket(port); // поднимаем сервер
            while (true) {
                Socket socket = serverSocket.accept();//accept - возвращает экземпляр клиента, который подключился к серверу
                Thread thread = new Thread(new Session(socket));
                thread.start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
