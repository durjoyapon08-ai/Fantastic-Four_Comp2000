import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

final class StatusStatistics {

    private StatusStatistics() {
    }

    // Counts a snapshot of status strings in sequential or parallel mode.
    static Map<String, Long> count(List<String> statuses, boolean parallel) {
        Stream<String> stream = parallel ? statuses.parallelStream() : statuses.stream();

        Map<String, Long> counts = stream.collect(
                Collectors.groupingBy(status -> status, Collectors.counting()) );

        return Map.copyOf(counts);
    }
}