public class Process {

    /** Process ID. **/
    public final int pid;
    /** Parent Process ID **/
    public final int ppid;

    private final double duration;
    private Routine routine;

    private boolean running;
    private boolean started;
    private boolean finished;

    private long startTime;
    private long elapsedTime;
    private long previousUpdateTime;

    public Process(int pid, int ppid, double duration, Routine routine) {
        ensureValidDuration(duration);
        this.pid = pid;
        this.ppid = pid;
        this.duration = duration * 1000;
        this.routine = routine;
    }

    public Process(int pid, int ppid, double duration) {
        ensureValidDuration(duration);
        this.pid = pid;
        this.ppid = ppid;
        this.duration = duration;
    }

    public void start() {
        if (started) {
            return;
        }

        started = true;
        running = true;

        startTime = System.currentTimeMillis();
    }

    public void update() {
        // If process isn't running, do nothing
        if (!running) {
            return;
        }

        // Check if process is complete
        if (System.currentTimeMillis() - startTime >= duration) {
            terminate();
        }
    }

    public void block() {
        // If process hasn't started, do nothing
        if (!started) {
            return;
        }

        running = false;
    }

    public void resume() {

    }

    public void terminate() {
        // If already finished, do nothing
        if (finished) {
            return;
        }

        // Run the routine if one exists
        if (routine != null) {
            routine.run();
        }

        running = false;
        finished = true;
    }

    public boolean getFinished() {
        return finished;
    }

    private void ensureValidDuration(double durationToCheck) throws IllegalArgumentException{
        if (durationToCheck <= 0) {
            throw new IllegalArgumentException("Invalid process duration");
        }
    }

}
