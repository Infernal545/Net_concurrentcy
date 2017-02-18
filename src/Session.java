import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

/**
 * Created by 14Krishin on 17.02.2017.
 */
public class Session implements Runnable {
Socket socket;
 public static   int ClientNumber_=1;

public Session(Socket socket){
    this.socket=socket;
}

    @Override
    public void run() {
    try {
        InputStream socketInputStream = socket.getInputStream();
        OutputStream socketOutputStream = socket.getOutputStream();

        DataInputStream dataInputStream = new DataInputStream(socketInputStream);
        DataOutputStream dataOutputStream = new DataOutputStream(socketOutputStream);
        ClientNumber_++;
        String line = null;
        while (true) {

            if(dataInputStream.available()>0) {
                // Ожидание сообщения от клиента
                line = dataInputStream.readUTF();
                System.out.println("Client writes " + line +
                "\nAdress " + socket.getInetAddress().getHostAddress() + " port " + socket.getLocalPort());

                // Отсылаем клиенту обратно эту самую строку текста
                dataOutputStream.writeUTF("Server receive text : " + line);
                // Завершаем передачу данных
                dataOutputStream.flush();
                System.out.println();
                if (line.equalsIgnoreCase("close")) {
                    // завершаем соединение
                    socket.close();
                    System.out.println();
                    break;
                }
                if (line.equalsIgnoreCase("exit")) {
                    ClientNumber_--;
                    System.out.println();
                }
            }



        }
        System.exit(0);
    }
    catch (IOException e) {
        e.printStackTrace();
    }
    }
}
