import java.util.LinkedList;

/**
 * Created by dirnt_000 on 30.03.2017.
 */
public class ThreadPool {
    private final LinkedList<WorkerThread> allWorkers;
    private final Channel<WorkerThread> freeWorkers;
    private final int maxSize;
    private final static Object lock = new Object();
    public ThreadPool(int maxSize) {
        this.maxSize = maxSize;
        this.freeWorkers = new Channel<>(maxSize);
        this.allWorkers = new LinkedList<>();
        WorkerThread workerThread = new WorkerThread(this);
        allWorkers.add(workerThread);
        freeWorkers.put(workerThread);
    }
    public void execute(Runnable task) {
        synchronized (lock) {
            if (freeWorkers.size() == 0 && allWorkers.size() < maxSize) {
                WorkerThread workerThread = new WorkerThread(this);
                allWorkers.addLast(workerThread);
                freeWorkers.put(workerThread);
            }
        }
        freeWorkers.take().execute(task);
    }
    public void onTaskCompleted(WorkerThread workerThread) {
            freeWorkers.put(workerThread);
    }
}
