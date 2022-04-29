package trainingDiary.validation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ValidateExerciseTest {

    ValidateExercise vExercise;

    @BeforeEach
    public void setup() {
        vExercise = new ValidateExercise();
    }

    @Test
    public void valNameTest() {
        vExercise.valName("Testname");
        assertEquals("Testname", vExercise.getExercise().getName());

        assertThrows(IllegalArgumentException.class, () -> {
            vExercise.valName("a");
        }, "Exception should be thrown when setting name with one letter");

        assertThrows(IllegalArgumentException.class, () -> {
            vExercise.valName("abcdæ");
        }, "Exception should be thrown when setting name with æ");

        assertThrows(IllegalArgumentException.class, () -> {
            vExercise.valName("abcdø");
        }, "Exception should be thrown when setting name with ø");

        assertThrows(IllegalArgumentException.class, () -> {
            vExercise.valName("abcdå");
        }, "Exception should be thrown when setting name with å");

        assertThrows(IllegalArgumentException.class, () -> {
            vExercise.valName("0");
        }, "Exception should be thrown when setting name without letters only");

        assertThrows(IllegalArgumentException.class, () -> {
            vExercise.valName("123");
        }, "Exception should be thrown when setting name without letters only");

        assertThrows(IllegalArgumentException.class, () -> {
            vExercise.valName("");
        }, "Exception should be thrown when name is blank");
    }

    @Test
    public void valWeightTest() {
        for (int i = 0; i <= 300; i++) {
            vExercise.valWeight(String.valueOf(i));
            assertEquals(i, vExercise.getExercise().getWeight(),
                    "Weight should be allowed to be set when in interval [0,300]");
        }

        vExercise.valWeight("");
        assertEquals(0, vExercise.getExercise().getWeight(),
                "Weight should be 0 when no weight chosen");

        assertThrows(IllegalArgumentException.class, () -> {
            vExercise.valWeight("-10");
        }, "Exception should be thrown when weight is negative");

        assertThrows(IllegalArgumentException.class, () -> {
            vExercise.valWeight("-10000");
        }, "Exception should be thrown when weight is negative");

        assertThrows(IllegalArgumentException.class, () -> {
            vExercise.valWeight("310");
        }, "Exception should be thrown when weight is greater than 300");

        assertThrows(IllegalArgumentException.class, () -> {
            vExercise.valWeight("10000");
        }, "Exception should be thrown when weight is greater than 300");

        assertThrows(NumberFormatException.class, () -> {
            vExercise.valWeight("ten");
        }, "Exception should be thrown when weight contains letters");

        assertThrows(NumberFormatException.class, () -> {
            vExercise.valWeight("10a");
        }, "Exception should be thrown when weight contains letters");

    }

    @Test
    public void testValrep() {
        for (int i = 10; i <= 1000; i += 10) {
            vExercise = new ValidateExercise();
            vExercise.valRep(String.valueOf(i));
            assertEquals(i, vExercise.getExercise().getReps().get(0),
                    "Rep should be allowed to be set when in interval [1,1000]");
        }

        assertThrows(IllegalArgumentException.class, () -> {
            vExercise.valRep("0");
        }, "Exception should be thrown when rep is 0");

        assertThrows(IllegalArgumentException.class, () -> {
            vExercise.valRep("-10");
        }, "Exception should be thrown when rep is negative");

        assertThrows(IllegalArgumentException.class, () -> {
            vExercise.valRep("-1000");
        }, "Exception should be thrown when rep is negative");

        assertThrows(IllegalArgumentException.class, () -> {
            vExercise.valRep("10000");
        }, "Exception should be thrown when rep is greater than 1001");

        assertThrows(IllegalArgumentException.class, () -> {
            vExercise.valRep("10000");
        }, "Exception should be thrown when rep is greater than 1000");

        assertThrows(NumberFormatException.class, () -> {
            vExercise.valRep("eight");
        }, "Exception should be thrown when rep is greater than 1000");
    }

}
