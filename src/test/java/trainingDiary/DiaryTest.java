package trainingDiary;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DiaryTest {

    private Diary diary;
    private Run run;
    private Strength strength;
    private Exercise exercise;

    @BeforeEach
    public void setup() {

        diary = new Diary();
        run = new Run(LocalDateTime.now().minusDays(1), 60, 10, '4', "TestRun", 150, 200);
        strength = new Strength(LocalDateTime.now(), 90, '4', "TestStrength");
        exercise = new Exercise("TestExercise", 100, 8, 7, 6, 5);

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

        strength.setDate(LocalDateTime.now().minusDays(1));

        assertThrows(IllegalArgumentException.class, () -> {
            diary.addWorkout(strength);
        }, "Two workouts cannot be added at the same day");

        assertThrows(NullPointerException.class, () -> {
            diary.addWorkout(null);
        }, "Null should not be allowed to be added to diary");

    }

}
