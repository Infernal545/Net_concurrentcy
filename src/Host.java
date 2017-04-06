import java.io.*;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by 14Krishin on 10.02.2017.
 */
public class Host implements Runnable{
    private static final int channelSize = 100;
    private int maxClientNumber;
    private int ClientNumber_=0;
    private int port;


    private Object lock = new Object();

    public Host(int port, int maxClientNumber){

        this.port = port;
        this.maxClientNumber = maxClientNumber;
    }

    public void threadStop()
    {
        synchronized (lock) {
            ClientNumber_--;
        }
    }
    public void threadStart()
    {
        synchronized (lock) {
            ClientNumber_++;
        }
    }

    public void run() {
        try {
           ServerSocket serverSocket = new ServerSocket(port); // поднимаем сервер

        ThreadPool threadPool = new ThreadPool(maxClientNumber);
        Channel<Session> channel = new Channel(channelSize);
        Dispatcher dispatcher = new Dispatcher(channel ,threadPool);
        new Thread(dispatcher).start();
        while (true) {
            try {
                Socket socket = serverSocket.accept();//accept - возвращает экземпляр клиента, который подключился к
                Session session = new Session(socket, Host.this);
                threadPool.execute(session);
            }
            catch (IOException e) {
                System.err.println("Host is not available");
            }
        }

        }

        catch (BindException e){
            System.err.println("Port already in use");
            return;
        }
        catch (IOException e) {
            System.err.println("Host: The error of creating a new serverSocket");
            return;
        }
    }

}