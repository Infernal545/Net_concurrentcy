import java.io.*;
import java.net.Socket;

/**
 * Created by 14Krishin on 10.02.2017.
 */
public class Client {
    public static void main(String[] args) {
        try {

            String host = args[0];
            int port = Integer.parseInt(args[1]);
            Socket socket = new Socket(host,port); // подключение к серверу

            InputStream socketInputStream   = socket.getInputStream();
            OutputStream socketOutputStream = socket.getOutputStream();

            DataInputStream dataInputStream = new DataInputStream (socketInputStream );
            DataOutputStream dataOutputStream = new DataOutputStream(socketOutputStream);

            // Создаем поток для чтения с клавиатуры.
            InputStreamReader isr = new InputStreamReader(System.in);
            BufferedReader keyboard = new BufferedReader(isr);
            String line = null;
            String serverData = dataInputStream.readUTF();
            if (serverData.equals("Server is available")) {
                System.out.println("Write something");
                System.out.println();
                while (true) {
                    // Пользователь должен ввести строку и нажать Enter
                    line = keyboard.readLine();
                    dataOutputStream.writeUTF(line);     // Отсылаем строку серверу
                    dataOutputStream.flush();            // Завершаем поток
                    if (line.equalsIgnoreCase("exit")) {
                        socket.close();
                        break;
                    } else {
                        // сообщение от сервера
                        System.out.println(
                                "The server sent me this line :\n\t" + dataInputStream.readUTF());
                    }
                }
            }
            else {
                System.out.println("The server sent me this line :\n\t" + serverData);
                socket.close();
            }
            dataInputStream.close();
            dataOutputStream.close();
            isr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
