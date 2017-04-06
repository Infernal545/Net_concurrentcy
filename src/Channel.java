import java.util.LinkedList;

/**
 * Created by dirnt_000 on 22.03.2017.
 */
public class  Channel <T> {

    private final LinkedList<T> queue = new LinkedList<>();
    private final static Object lock = new Object();
    private final int maxClientNumber;

    public Channel(int maxClientNumber) {
        this.maxClientNumber = maxClientNumber;
    }

    void put(Runnable x) {
        synchronized (lock) {
            while (maxClientNumber == queue.size()) {
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            queue.addLast((T) x);
            lock.notifyAll();
        }

    }

   T take() {
        synchronized (lock) {
            while (queue.isEmpty()) {
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            lock.notifyAll();
            return queue.removeFirst();
        }
    }
    int size() {
        synchronized (lock) {
            return queue.size();
        }
    }
}
