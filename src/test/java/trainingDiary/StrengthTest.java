package trainingDiary;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class StrengthTest {
    private Strength strength;

    @BeforeEach
    public void setup() {
        strength = new Strength();
    }

    @Test
    public void testSetDate() {
        LocalDateTime now = LocalDateTime.now();
        strength.setDate(now);
        assertEquals(now, strength.getDate(), "Dates should be equal when date is sat");

        LocalDateTime date = LocalDateTime.of(2022, 01, 01, 12, 00);
        strength.setDate(date);
        assertEquals(date, strength.getDate(), "Dates should be equals when sat");

        assertThrows(IllegalArgumentException.class, () -> {
            strength.setDate(LocalDateTime.now().plusDays(1));
        }, "Date should not be allowed to be set to the future");

        assertThrows(IllegalArgumentException.class, () -> {
            strength.setDate(LocalDateTime.now().plusMinutes(1));
        }, "Time should not be allowed to be set to the future");
    }

    @Test
    public void testSetDuration() {
        for (int i = 30; i < 300; i += 30) {
            strength.setDuration(i);
            assertEquals(i, strength.getDuration(), "Duration should be " + i + " when sat to " + i);
        }
        assertThrows(IllegalArgumentException.class, () -> {
            strength.setDuration(0);
        }, "Date should not be allowed to be set to 0");

        assertThrows(IllegalArgumentException.class, () -> {
            strength.setDuration(-10);
        }, "Date should not be allowed to be set to 0");

        assertThrows(IllegalArgumentException.class, () -> {
            strength.setDuration(1000);
        }, "Date should not be allowed to be set to 1000");

    }

    @Test
    public void testSetRating() {
        char[] values = { '1', '2', '3', '4', '5', '6' };

        for (int i = 0; i < values.length; i++) {
            strength.setRating(values[i]);
            assertEquals(values[i], strength.getRating(), "Ratin should be " + values[i] + " when sat");
        }

        assertThrows(IllegalArgumentException.class, () -> {
            strength.setRating('0');
        }, "Rating should not be allowed to be set to 0");

        assertThrows(IllegalArgumentException.class, () -> {
            strength.setRating('7');
        }, "Rating should not be allowed to be set to 7");

        assertThrows(IllegalArgumentException.class, () -> {
            strength.setRating('A');
        }, "Rating should not be allowed to be set to A");

    }

    @Test
    public void testSetExercise() {
        assertEquals(0, strength.getExercises().size(), "Exercises should be empty when initialized");

        assertThrows(NullPointerException.class, () -> {
            strength.addExercise(null);
        }, "Should not be allowed to add null as exercise");

        Exercise ex1 = new Exercise("Benchpress", 90, 8, 8, 8);
        strength.addExercise(ex1);
        assertEquals(1, strength.getExercises().size(), "Exercises should contain exercise when exercise is added");
        assertEquals(ex1, strength.getExercises().get(0), "Exercise added should be equal to exercise from Exercises");

        Exercise ex2 = new Exercise("Squats", 90, 8, 8, 8);
        strength.addExercise(ex2);
        assertEquals(2, strength.getExercises().size(), "Exercises should contain exercise when exercise is added");
        assertEquals(ex2, strength.getExercises().get(1), "Exercise added should beequal to exercise from Exercises");
        assertNotEquals(ex1, strength.getExercises().get(1), "Exercise1 should be equal to exercise2 from Exercises");

        assertThrows(IllegalArgumentException.class, () -> {
            strength.addExercise(ex1);
            ;
        }, "Should not be allowed to add excisting exercise");
    }
}
