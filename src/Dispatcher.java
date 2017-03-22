/**
 * Created by dirnt_000 on 22.03.2017.
 */
public class Dispatcher implements Runnable {

    public static Channel<Runnable> channel;

    public Dispatcher(Channel channel) {
        this.channel = channel;
    }

    public void run() {
        while (true) {
            Runnable session = channel.take();
            Thread thread = new Thread(session);
            thread.start();
        }
    }
}
