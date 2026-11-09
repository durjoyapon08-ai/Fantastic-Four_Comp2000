import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class StatisticsBenchmark {

    private static volatile Map<String, Long> result;

    public static void main(String[] args) {
        List<String> statuses = new ArrayList<>();
        statuses.addAll(Collections.nCopies(70, "Healthy"));
        statuses.addAll(Collections.nCopies(15, "Infected"));
        statuses.addAll(Collections.nCopies(10, "Recovered"));
        statuses.addAll(Collections.nCopies(5, "Dead"));
        List<String> snapshot = List.copyOf(statuses);

        Map<String, Long> expected = Map.of(
                "Healthy", 70L, "Infected", 15L,
                "Recovered", 10L, "Dead", 5L
        );

        if (!StatusStatistics.count(snapshot, false).equals(expected)
                || !StatusStatistics.count(snapshot, true).equals(expected)) {
            throw new IllegalStateException("Status counts did not match.");
        }

        // Warm up both modes before measuring.
        measure(snapshot, false, 1000);
        measure(snapshot, true, 1000);

        double[] sequential = new double[7];
        double[] parallel = new double[7];

        for (int round = 0; round < 7; round++) {
            // Alternate which mode runs first.
            if (round % 2 == 0) {
                sequential[round] = measure(snapshot, false, 2000);
                parallel[round] = measure(snapshot, true, 2000);
            } else {
                parallel[round] = measure(snapshot, true, 2000);
                sequential[round] = measure(snapshot, false, 2000);
            }
        }

        Arrays.sort(sequential);
        Arrays.sort(parallel);

        System.out.println("Both modes produced the expected counts for 100 people.");
        System.out.printf("Sequential median: %.2f us/count%n", sequential[3]);
        System.out.printf("Parallel median: %.2f us/count%n", parallel[3]);
    }

    private static double measure(
            List<String> snapshot, boolean parallel, int repetitions) {
        long start = System.nanoTime();

        for (int i = 0; i < repetitions; i++) {
            result = StatusStatistics.count(snapshot, parallel);
        }

        return (System.nanoTime() - start) / (1000.0 * repetitions);
    }
}