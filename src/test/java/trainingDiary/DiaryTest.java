package trainingDiary;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DiaryTest {

    private Diary diary;
    private Run run;
    private Strength strength;

    @BeforeEach
    public void setup() {

        diary = new Diary();
        run = new Run(LocalDateTime.now().minusDays(1), 60, 10, '4', "TestRun", 150, 200);
        strength = new Strength(LocalDateTime.now(), 90, '4', "TestStrength");

    }

    @Test
    public void testAddWorkout() {

        assertEquals(0, diary.getDiary().size(), "Diary should be empty when initialized");

        diary.addWorkout(run);
        assertEquals(1, diary.getDiary().size(), "Size of diary should increase when workout is added");
        assertEquals(run, diary.getDiary().get(0), "Workout from the diary should be equal to the workout added");

        diary.addWorkout(strength);
        assertEquals(2, diary.getDiary().size(), "Size of diary should increase when workout is added");
        assertEquals(run, diary.getDiary().get(0), "Workout from the diary should be equal to the workout added");
        assertEquals(strength, diary.getDiary().get(1), "Workout from the diary should be equal to the workout added");

    }

    @Test
    public void testExceptionsAddWorkout() {

        diary.addWorkout(run);

        assertThrows(IllegalArgumentException.class, () -> {
            diary.addWorkout(run);
        }, "Workout cannot be added if workout already exists in diary");

        strength.setDate(run.getDate());

        assertThrows(IllegalArgumentException.class, () -> {
            diary.addWorkout(strength);
        }, "Two workouts cannot be added at the same day");

        assertThrows(NullPointerException.class, () -> {
            diary.addWorkout(null);
        }, "Null should not be allowed to be added to diary");

    }

    @Test
    public void testGetWorkoutsPerMonth() {

        List<LocalDateTime> dates = new ArrayList<>();
        dates.add(LocalDateTime.of(2022, 01, 01, 12, 00));
        dates.add(LocalDateTime.of(2022, 01, 02, 12, 00));
        dates.add(LocalDateTime.of(2022, 01, 03, 12, 00));
        dates.add(LocalDateTime.of(2022, 01, 04, 12, 00));
        dates.add(LocalDateTime.of(2022, 01, 05, 12, 00));
        dates.add(LocalDateTime.of(2022, 02, 01, 12, 00));
        dates.add(LocalDateTime.of(2022, 02, 02, 12, 00));
        dates.add(LocalDateTime.of(2022, 02, 03, 12, 00));
        dates.add(LocalDateTime.of(2022, 02, 04, 12, 00));

        Iterator<LocalDateTime> ItDates = dates.iterator();

        while (ItDates.hasNext()) {
            diary.addWorkout(new Strength(ItDates.next(), 60, '5', "test"));
        }

        assertEquals(0, diary.getWorkoutsPerMonth(03, 2022).size(), "Month shouldn't contain workouts");

        for (IWorkout workout : diary.getWorkoutsPerMonth(01, 2022)) {
            assertEquals(01, workout.getDate().getMonthValue(), "Month value should be equal");
        }

        for (IWorkout workout : diary.getWorkoutsPerMonth(02, 2022)) {
            assertEquals(02, workout.getDate().getMonthValue(), "Month value should be equal");
        }
    }

}
