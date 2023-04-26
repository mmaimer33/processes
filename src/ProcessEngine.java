import java.util.ArrayList;

public class ProcessEngine {

    /**
     * Time interval between each update in milliseconds.
     */
    public static final long TIME_INTERVAL = 200;

    private static ArrayList<Process> processes;

    private static boolean running = false;
    private static boolean finished = false;

    /**
     * Initialise the {@code ProcessEngine} with a given list of {@code Process}es and start execution.
     * @param processList Initial list of processes.
     */
    public static void start (ArrayList<Process> processList) {
        running = true;
        processes = processList;
        for (Process process : processList) {
            process.start();
        }
        run();
    }

    /**
     * Starts running the {@code ProcessEngine}.
     */
    public static void run() {
        while (running) {
            update();
            try {
                Thread.sleep(TIME_INTERVAL);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    /**
     * Update the {@code ProcessEngine} and all {@code Process}es. To be called regularly.
     */
    public static void update() {
        // Update each process
        for (Process process : processes) {
            process.update();
        }

        finished = true;
        for (Process process : processes) {
            if (!process.getFinished()) {
                finished = false;
                break;
            }
        }

        if (finished) {
            end();
        }
    }

    /**
     * End the {@code ProcessEngine}. Will be called automatically when all {@code Process}es are finished,
     * but can be called manually to end the execution prematurely.
     */
    public static void end() {
        running = false;
    }

}
