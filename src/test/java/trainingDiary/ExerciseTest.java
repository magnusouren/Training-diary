package trainingDiary;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ExerciseTest {

    Exercise exercise;

    @BeforeEach
    public void setup() {
        exercise = new Exercise();
    }

    @Test
    public void testAddRep() {
        for (int i = 1; i < 4; i++) {
            exercise.addRep(i);
            assertEquals(exercise.getReps().size(), i);
            assertEquals(i, exercise.getReps().get(i - 1));
        }

    }

}
