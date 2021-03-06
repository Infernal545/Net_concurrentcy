/**
 * Created by dirnt_000 on 22.03.2017.
 */
public class Dispatcher implements Runnable {

    private final Channel<Session> channel;
    private final ThreadPool threadPool;

    public Dispatcher(Channel<Session> channel, ThreadPool threadPool) {
        this.channel = channel;
        this.threadPool = threadPool;
    }

    public void run() {
        while (true)
            threadPool.execute(channel.take());
    }
}
