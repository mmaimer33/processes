import java.util.ArrayList;

public class ProcessEngine {

    /**
     * Time interval between each update in milliseconds.
     */
    public final double TIME_INTERVAL = 200;

    private static ArrayList<Process> processes;

    private static boolean running = false;
    private static boolean finished = false;

    /**
     * Initialise the {@code ProcessEngine} with a given list of {@code Process}es.
     * @param processList Initial list of processes.
     * @throws InterruptedException If execution is interrupted.
     */
    public static void initialise(ArrayList<Process> processList) throws InterruptedException {
        running = true;
        processes = processList;
        for (Process process : processList) {
            process.start();
        }
        run();
    }

    /**
     * Starts running the {@code ProcessEngine}.
     * @throws InterruptedException If execution is interrupted.
     */
    public static void run() throws InterruptedException {
        while (running) {
            update();
            Thread.sleep(200);
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
