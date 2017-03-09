import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by 14Krishin on 17.02.2017.
 */
public class Session implements Runnable {
    private Socket socket;
    String name;

    public Session(Socket socket){
        this.socket=socket;
        this.name="Adress " + socket.getInetAddress().getHostAddress() + " port " + socket.getLocalPort();
    }

    @Override
    public void run() {
        try {
            InputStream socketInputStream = socket.getInputStream();
            OutputStream socketOutputStream = socket.getOutputStream();

            DataInputStream dataInputStream = new DataInputStream(socketInputStream);
            DataOutputStream dataOutputStream = new DataOutputStream(socketOutputStream);

            String line = "";
            while (!line.equals("exit")) {
                    // Ожидание сообщения от клиента
                    line = dataInputStream.readUTF();
                    System.out.println("Client writes " + line +
                            "\n" + name);

                    // Отсылаем клиенту обратно эту самую строку текста
                    dataOutputStream.writeUTF("Server receive text : " + line);
                    // Завершаем передачу данных
                    dataOutputStream.flush();
                socket.close();
            }

        }
        catch (IOException e) {
            System.err.println("Connection "+ name + " was reset");
        }
        finally {
            Server.threadStop();
        }
    }
}
