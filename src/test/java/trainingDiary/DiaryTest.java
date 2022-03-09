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
        });
    }

    @Test
    public void testRemoveWorkout() {
        diary.addWorkout(run);
        assertEquals(1, diary.getDiary().size());
        diary.removeWorkout(run);
        assertEquals(0, diary.getDiary().size());
    }
}
