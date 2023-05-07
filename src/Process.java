/**
 * Represents a {@code Process} that can be run by the {@code ProcessEngine}. Takes a given amount of time with an optional routine to run upon completion.
 */
public class Process {

    /** Process ID. **/
    public final int pid;
    /** Parent Process ID **/
    public final int ppid;

    private final double duration;
    private Routine routine;

    private boolean isRunning;
    private boolean hasStarted;
    private boolean isFinished;

    private long startTime;
    private long elapsedTime;
    private long previousUpdateTime;

    /**
     * Construct a {@code Process} with given details.
     * @param pid Process ID.
     * @param ppid Parent Process ID.
     * @param duration Duration of process, in seconds.
     * @param routine Routine to run upon completion.
     */
    public Process(int pid, int ppid, double duration, Routine routine) {
        ensureValidDuration(duration);
        this.pid = pid;
        this.ppid = ppid;
        this.duration = duration * 1000;
        this.routine = routine;
    }

    /**
     * Construct a {@code Process} with given details.
     * @param pid Process ID.
     * @param ppid Parents Process ID.
     * @param duration Duration of process, in seconds.
     */
    public Process(int pid, int ppid, double duration) {
        ensureValidDuration(duration);
        this.pid = pid;
        this.ppid = ppid;
        this.duration = duration * 1000;
    }

    /**
     * Mark the process ready for execution.
     */
    public void start() {
        if (hasStarted) {
            return;
        }

        hasStarted = true;
        isRunning = true;

        startTime = System.currentTimeMillis();
        previousUpdateTime = startTime;
        elapsedTime = 0;
    }

    /**
     * Update the process based on the duration and whether it is running. To be called regularly by the {@code ProcessEngine}.
     */
    public void update() {
        // If process isn't running, do nothing
        if (!isRunning) {
            return;
        }

        updateTime();

        // Run routine if one exists
        if (routine != null) {
            routine.run();
        }

        // Check if process is complete
        if (elapsedTime >= duration) {
            terminate();
        }
    }

    /**
     * Mark the process as blocked.
     */
    public void block() {
        // If process hasn't started, do nothing
        if (!hasStarted) {
            return;
        }

        updateTime();

        // Block process
        isRunning = false;
    }

    /**
     * Resume a blocked process.
     */
    public void unblock() {
        // If process isn't blocked, do nothing
        if (isRunning) {
            return;
        }

        // Update time to match current
        previousUpdateTime = System.currentTimeMillis();

        // Unblock process
        isRunning = true;
    }

    /**
     * Terminate the process.
     */
    public void terminate() {
        // If already finished, do nothing
        if (isFinished) {
            return;
        }

        isRunning = false;
        isFinished = true;
    }

    public boolean getFinished() {
        return isFinished;
    }

    private void ensureValidDuration(double durationToCheck) throws IllegalArgumentException{
        if (durationToCheck <= 0) {
            throw new IllegalArgumentException("Invalid process duration");
        }
    }

    private void updateTime() {
        long currTime = System.currentTimeMillis();
        elapsedTime += currTime - previousUpdateTime;
        previousUpdateTime = currTime;
    }

}
