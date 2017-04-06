/**
 * Created by dirnt_000 on 30.03.2017.
 */
public class WorkerThread implements Runnable{
    private Thread thread;
    private ThreadPool threadPool;
    private Runnable currentTask = null;
    private final Object lock = new Object();
    public WorkerThread(ThreadPool threadPool) {
        thread = new Thread(this);
        this.threadPool = threadPool;
        thread.start();
    }
    public void run() {
        synchronized (lock) {
            while (true) {

                    while (currentTask == null)
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    try{
                        currentTask.run(); // таска закончилась или упала
                    }
                    catch (RuntimeException e){
                        e.printStackTrace();
                    }
                    finally {
                        currentTask = null;
                        threadPool.onTaskCompleted(this);
                    }

            }
        }

    }
    public void execute(Runnable task) {
        synchronized (lock) {
            if(currentTask != null){
                new IllegalStateException();
            }
            currentTask = task;
            lock.notifyAll();
        }
    }
}
