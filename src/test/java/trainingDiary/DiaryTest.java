package trainingDiary;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DiaryTest {

    private Diary diary;
    private Run run;
    private Strength strength;

    @BeforeEach
    public void setup() {

        diary = new Diary();
        run = new Run(LocalDateTime.now(), 60, 10, '4', "TestLøp", 150, 200);
        strength = new Strength(LocalDateTime.now(), 90, '4', "TestStyrke");

    }

    @Test
    public void testAddWorkout() {

        diary.addWorkout(run);
        assertEquals(1, diary.getDiary().size());
        assertEquals(run, diary.getDiary().get(0));

        diary.addWorkout(strength);
        assertEquals(2, diary.getDiary().size());
        assertEquals(run, diary.getDiary().get(0));
        assertEquals(strength, diary.getDiary().get(1));

        for (int i = 0; i < 3; i++) {
            run = new Run(LocalDateTime.now(), 60 + i, 10, '4', "TestLøp", 150, 200);
            diary.addWorkout(run);
            assertEquals(3 + i, diary.getDiary().size());
            assertEquals(run, diary.getDiary().get(diary.getDiary().size() - 1));
        }

        assertNotEquals(run, diary.getDiary().get(0));
    }

    @Test
    public void testExceptionAddWorkout() {

        diary.addWorkout(run);
        assertThrows(IllegalArgumentException.class, () -> {
            diary.addWorkout(run);
        }, "One workout can't be added twice");

    }

    @Test
    public void testRemoveWorkout() {

        diary.addWorkout(run);
        assertEquals(1, diary.getDiary().size());
        assertEquals(run, diary.getDiary().get(0));

        diary.removeWorkout(run);
        assertEquals(0, diary.getDiary().size());

        diary.addWorkout(strength);
        assertEquals(1, diary.getDiary().size());
        assertEquals(strength, diary.getDiary().get(0));

        diary.removeWorkout(strength);
        assertEquals(0, diary.getDiary().size());

    }

    @Test
    public void testExceptionRemoveWorkout() {

        assertThrows(IllegalArgumentException.class, () -> {
            diary.removeWorkout(run);
        }, "Workout not in diary can't be removed");

        assertThrows(IllegalArgumentException.class, () -> {
            diary.removeWorkout(strength);
        }, "Workout not in diary can't be removed");

    }

    @Test
    public void testAddExercise() {

        diary.addExercise(strength, "benk", 1, 2, 3, 4);

        assertEquals("benk", strength.getExercises().get(0).getName());
        assertEquals(1, strength.getExercises().get(0).getReps().get(0));
        assertEquals(2, strength.getExercises().get(0).getReps().get(1));
        assertEquals(3, strength.getExercises().get(0).getReps().get(2));
        assertEquals(4, strength.getExercises().get(0).getReps().get(3));

        diary.addExercise(strength, "benk", 1, 2, 3, 4);
        assertNotEquals(strength.getExercises().get(0), strength.getExercises().get(1));

    }

    @Test
    public void testExceptionAddExercise() {

        assertThrows(IllegalArgumentException.class, () -> {
            diary.addExercise(strength, "benk", -1);
        }, "Rep must be greater than 0");

        assertThrows(IllegalArgumentException.class, () -> {
            diary.addExercise(strength, "Benk", 1, 0);
        }, "Rep must be greater than 0");

        assertThrows(IllegalArgumentException.class, () -> {
            diary.addExercise(run, "Benk", 1);
        }, "Run can't get exercise");

        assertThrows(NullPointerException.class, () -> {
            diary.addExercise(strength, "Benk", null);
        }, "Addworkout must get at least 1 Integer 'rep'");

    }
}
