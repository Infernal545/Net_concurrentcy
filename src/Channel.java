import java.util.LinkedList;

/**
 * Created by dirnt_000 on 22.03.2017.
 */
public class  Channel <T> {

    private final LinkedList<T> linkedList = new LinkedList<>();
    private final static Object lock = new Object();
    private final int maxClientNumber;

    public Channel(int maxClientNumber) {
        this.maxClientNumber = maxClientNumber;
    }

    void put(Runnable x) {
        synchronized (lock) {
            while (maxClientNumber <= linkedList.size()) {
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            linkedList.addLast((T) x);
            lock.notifyAll();
        }

    }

   T take() {
        synchronized (lock) {
            while (linkedList.isEmpty()) {
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            lock.notifyAll();
            return linkedList.removeFirst();
        }
    }
}
