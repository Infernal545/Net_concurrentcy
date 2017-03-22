import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;

/**
 * Created by dirnt_000 on 21.03.2017.
 */
public class ServerHost {
    public static void main(String[] args) {
        int port;
        int maxClientNumber;
        try {
            port = Integer.parseInt(args[0]);
        }
        catch (NumberFormatException e) {
            System.err.println("Server: Wrong port format. Should be integer");
            return;
        }
        try {
            maxClientNumber = Integer.parseInt(args[1]);
        }
        catch (NumberFormatException e) {
            System.err.println("Server: Wrong maximum client format. Should be integer");
            return;
        }
        ServerSocket serverSocket = null;


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
        Thread server = new Thread(new Server(serverSocket, port, maxClientNumber));
        server.start();
    }

}






