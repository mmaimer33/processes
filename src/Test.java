public class Test {

    public static void runRoutine(Routine routine) {
        routine.run();
    }

    public static void main(String[] args) {
        runRoutine(() -> System.out.println("Hallo!"));
    }
}
