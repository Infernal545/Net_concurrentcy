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
public class Server implements Runnable{

    private volatile int maxClientNumber;
    private volatile int ClientNumber_=0;
    private int port;
    private ServerSocket serverSocket;

    private Object lock = new Object();

    public Server (ServerSocket serverSocket, int port, int maxClientNumber){
        this.serverSocket = serverSocket;
        this.port = port;
        this.maxClientNumber = maxClientNumber;
    }

    public void threadStop()
    {
        synchronized (lock) {
            ClientNumber_--;
            lock.notifyAll();
        }
    }


    public void run() {
        Channel channel = new Channel(maxClientNumber);
        Dispatcher dispatcher = new Dispatcher(channel);
        while (true) {
            try {
                Socket socket = serverSocket.accept();//accept - возвращает экземпляр клиента, который подключился к
                        try {
                           {
                                synchronized (lock) {
                                    while (ClientNumber_ == maxClientNumber) {
                                        lock.wait();
                                    }
                                    OutputStream socketOutputStream = socket.getOutputStream();
                                    DataOutputStream dataOutputStream = new DataOutputStream(socketOutputStream);

                                    try {
                                        dataOutputStream.writeUTF("Server is available");
                                    } catch (IOException e) {
                                        System.err.println("Client connection was reset");
                                    }
                                    dataOutputStream.flush();
                                    Session session = new Session(socket,Server.this);
                                    channel.put(session);
                                    Thread thread = new Thread(dispatcher);
                                    thread.start();
                                    ClientNumber_++;
                                }
                            }
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }


            }
            catch (IOException e) {
                System.err.println("Server is not available");
            }
        }


    }

}