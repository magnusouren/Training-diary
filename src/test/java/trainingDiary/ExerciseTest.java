package trainingDiary;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ExerciseTest {

    Exercise exercise;

    @BeforeEach
    public void setup() {
        exercise = new Exercise();
    }

    @Test
    public void setName() {
        exercise.setName("Testname");
        assertEquals("Testname", exercise.getName());

    }

}
