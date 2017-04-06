import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;

/**
 * Created by dirnt_000 on 21.03.2017.
 */
public class Server {
    public static void main(String[] args) {
        int port;
        int maxClientNumber;
        try {
            port = Integer.parseInt(args[0]);
        }
        catch (NumberFormatException e) {
            System.err.println("Host: Wrong port format. Should be integer");
            return;
        }
        try {
            maxClientNumber = Integer.parseInt(args[1]);
        }
        catch (NumberFormatException e) {
            System.err.println("Host: Wrong maximum client format. Should be integer");
            return;
        }




        Thread server = new Thread(new Host(port, maxClientNumber));
        server.start();
    }

}






