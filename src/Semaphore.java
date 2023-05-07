import java.util.ArrayList;

public class Semaphore {

    private int value;
    private ArrayList<Process> waitingProcesses;

    /**
     * Construct a {@code Semaphore} with a given value.
     * @param value Initial value of the {@code Semaphore}.
     */
    public Semaphore(int value) {
        this.value = value;
        waitingProcesses = new ArrayList<>();
    }

    /**
     * Wait on the {@code Semaphore}. If the value of the semaphore is less than 1, the process that calls this is blocked.
     * Decrements the semaphore (after resuming).
     * @param process Process that is waiting on the {@code Semaphore} (Must pass itself).
     */
    public void sem_wait(Process process) {
        if (value < 1) {
            waitingProcesses.add(process);
            process.block();
        }
    }

    /**
     * Signal the {@code Semaphore}. If there are processes waiting on the {@code Semaphore}, the first process in the queue is unblocked.
     * Increments the semaphore.
     */
    public void sem_post() {
        if (waitingProcesses.size() > 0) {
            Process process = waitingProcesses.get(0);
            waitingProcesses.remove(0);
            process.unblock();
        }
    }
}
