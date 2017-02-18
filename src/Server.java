import jdk.internal.util.xml.impl.ReaderUTF8;
import jdk.nashorn.internal.runtime.regexp.joni.SearchAlgorithm;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by 14Krishin on 10.02.2017.
 */
public class Server {
    public static void main(String[] args) {
        ServerSocket serverSocket = null;
        int maxClientNumber = 5; // максимальное количество клиентов
        try {
            int port = Integer.parseInt(args[0]);
            serverSocket = new ServerSocket(port); // поднимаем сервер

            while (true) {
                Socket socket = serverSocket.accept();//accept - возвращает экземпляр клиента, который подключился к
                if(Session.ClientNumber_>maxClientNumber){
                    OutputStream socketOutputStream = socket.getOutputStream();
                    DataOutputStream dataOutputStream = new DataOutputStream(socketOutputStream);
                    dataOutputStream.writeUTF("Server is not available");
                    dataOutputStream.flush();

                }
                else {
                    OutputStream socketOutputStream = socket.getOutputStream();
                    DataOutputStream dataOutputStream = new DataOutputStream(socketOutputStream);
                    dataOutputStream.writeUTF("Server is available");
                    dataOutputStream.flush();
                    Session session = new Session(socket);
                    Thread thread = new Thread(session);
                    thread.start();
                }
            }



        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
