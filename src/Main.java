import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        ArrayList<Process> processes = new ArrayList<>();
        processes.add(new Process(1, 0, 3, () -> System.out.println("Done")));

        ProcessEngine.start(processes);
    }
}