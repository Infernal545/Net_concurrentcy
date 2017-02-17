import java.io.*;
import java.net.Socket;

/**
 * Created by 14Krishin on 17.02.2017.
 */
public class Session implements Runnable {
Socket socket;
int threadNumber;
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

        String line = null;
        while (true) {
            // Ожидание сообщения от клиента
            line = dataInputStream.readUTF();
            System.out.println("Client writes " + line);
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
    }
    catch (IOException e) {
        e.printStackTrace();
    }
    }
}
