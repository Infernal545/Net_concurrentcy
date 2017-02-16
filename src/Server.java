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
            int port = Integer.parseInt(args[0]);
            serverSocket = new ServerSocket(port); // поднимаем сервер
            Socket socket = serverSocket.accept();//accept - возвращает экземпляр клиента, который подключился к серверу
            InputStream  socketInputStream   = socket.getInputStream();
            OutputStream socketOutputStream = socket.getOutputStream();

            DataInputStream  dataInputStream = new DataInputStream (socketInputStream  );
            DataOutputStream dataOutputStream = new DataOutputStream(socketOutputStream);

            String line = null;
            while(true) {
                // Ожидание сообщения от клиента
                line = dataInputStream.readUTF();
                System.out.println("Client writes "+ line );
                // Отсылаем клиенту обратно эту самую строку текста
                dataOutputStream.writeUTF("Server receive text : " + line);
                // Завершаем передачу данных
                dataOutputStream.flush();
                System.out.println();
                if (line.equalsIgnoreCase("exit")) {
                    // завершаем соединение
                    socket.close();
                    System.out.println();
                    break;
                }
            }
            System.exit(0);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
