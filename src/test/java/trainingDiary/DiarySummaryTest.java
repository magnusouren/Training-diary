package trainingDiary;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DiarySummaryTest {

    private Diary diary = new Diary();
    private Map<String, Number> totalSummary;
    private Map<String, Number> runSummary;
    private Map<String, Number> strengthSummary;

    private int sumRunDuration;
    private int sumRunDistance;
    private Collection<Integer> ratingsRun = new ArrayList<>();

    private int sumStrengthDuration;
    private Collection<Integer> ratingsStrength = new ArrayList<>();
    private int sumWeight;

    @BeforeEach
    public void setup() {

        int duration = 60;
        int distance = 10000;

        for (int i = 0; i < 5; i++) {
            diary.addWorkout(
                    new Run(LocalDateTime.of(2022, 3, 31, 12, 00).minusDays(i), duration, distance, '4', "TestRun",
                            150, 200));

            sumRunDuration += duration;
            sumRunDistance += distance;
            ratingsRun.add(4);

        }

        Exercise e = new Exercise("Test", 100, 1, 2, 3, 4);

        for (int i = 0; i < 5; i++) {
            Strength s = new Strength(LocalDateTime.of(2022, 02, 28, 12, 00).minusDays(i), duration, '5',
                    "TestStrength");
            s.addExercise(e);
            diary.addWorkout(s);

            sumStrengthDuration += duration;
            sumWeight += (1 + 2 + 3 + 4) * 100;
            ratingsStrength.add(5);
        }

        for (int i = 0; i < 5; i++) {

        }

        totalSummary = diary.getTotalSummary();
        runSummary = diary.getRunSummary();
        strengthSummary = diary.getStrengthSummary();

    }

    @Test
    public void testTotalSummary() {
        assertEquals(sumRunDuration + sumStrengthDuration, totalSummary.get("totDuration"));
        assertEquals(4.5, totalSummary.get("totAvgRating"));
        assertEquals(ratingsRun.size() + ratingsRun.size(), totalSummary.get("totWorkouts"));
    }

    @Test
    public void testRunSummary() {
        assertEquals(sumRunDuration, runSummary.get("runTotDuration"),
                "Total duration should be equal the sum of run-durations");

        assertEquals((ratingsRun.stream().mapToDouble(s -> s).sum()) / ratingsRun.size(),
                (double) runSummary.get("runAvgRating"), 0.001,
                "Average rating should be equal the average of run ratings");

        assertEquals(sumRunDistance, runSummary.get("runTotDistance"),
                "Total rundistance should be equal the sum of distances");

        assertEquals(ratingsRun.size(), runSummary.get("totRuns"));
    }

    @Test
    public void testStrengthSummary() {
        assertEquals(sumStrengthDuration, strengthSummary.get("strengthTotDuration"),
                "Total duration should be equal the sum of strength-durations");

        assertEquals((ratingsStrength.stream().mapToDouble(s -> s).sum()) / ratingsStrength.size(),
                (double) strengthSummary.get("strengthAvgRating"), 0.001,
                "Average rating should be equal the average of strength-ratings");

        assertEquals(ratingsStrength.size(), strengthSummary.get("totStrengths"),
                "Amount of workouts should be equal amount of ratings");

        assertEquals(sumWeight, strengthSummary.get("kgLifted"),
                "Total kg lifted should be equal to the sum of weight added");
    }

}
