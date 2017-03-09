import com.sun.org.apache.xpath.internal.SourceTree;
import jdk.internal.util.xml.impl.ReaderUTF8;
import jdk.nashorn.internal.runtime.regexp.joni.SearchAlgorithm;

import java.io.*;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by 14Krishin on 10.02.2017.
 */
public class Server {

    private static int ClientNumber_=0;
    public static void threadStop()
    {
        ClientNumber_--;
    }

    public static void main(String[] args) {
        ServerSocket serverSocket = null;
        int port; // номер порта

        try {
           port = Integer.parseInt(args[0]);
        }

        catch (NumberFormatException e) {
            System.err.println("Server: Wrong port format. Should be integer");
            return;
        }

        int maxClientNumber; // максимальное количество клиентов

        try {
            maxClientNumber = Integer.parseInt(args[1]);
        }

        catch (NumberFormatException e) {
                System.err.println("Server: Wrong maximum client format. Should be integer");
                return;
        }

        try {
            serverSocket = new ServerSocket(port); // поднимаем сервер
        }

        catch (BindException e){
            System.err.println("Port already in use");
            return;
        }

        catch (IOException e) {
            System.err.println("Server: The error of creating a new serverSocket");
            return;
        }
        while (true) {
            try {
                Socket socket = serverSocket.accept();//accept - возвращает экземпляр клиента, который подключился к

                if (ClientNumber_ < maxClientNumber) {

                    ClientNumber_++;
                    OutputStream socketOutputStream = socket.getOutputStream();
                    DataOutputStream dataOutputStream = new DataOutputStream(socketOutputStream);
                    dataOutputStream.writeUTF("Server is available");
                    dataOutputStream.flush();
                    Session session = new Session(socket);
                    Thread thread = new Thread(session);
                    thread.start();

                } else {
                    OutputStream socketOutputStream = socket.getOutputStream();
                    DataOutputStream dataOutputStream = new DataOutputStream(socketOutputStream);
                    dataOutputStream.writeUTF("Server is not available");
                    dataOutputStream.flush();
                }
            }
            catch (IOException e) {
                System.err.println("Server is not available");
            }
        }






    }

}