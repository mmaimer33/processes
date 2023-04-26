import java.util.ArrayList;

public class ProcessEngine {

    private static ArrayList<Process> processes;

    private static boolean running = false;
    private static boolean finished = false;

    public static void initialise(ArrayList<Process> processList) throws InterruptedException {
        running = true;
        processes = processList;
        for (Process process : processList) {
            process.start();
        }
        run();
    }

    public static void run() throws InterruptedException {
        while (running) {
            update();
            Thread.sleep(200);
        }
    }

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

    public static void end() {
        running = false;
    }

}
