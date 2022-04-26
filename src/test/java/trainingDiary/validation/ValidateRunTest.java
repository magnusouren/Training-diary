package trainingDiary.validation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import trainingDiary.Run;

public class ValidateRunTest {

    ValidateRun vrun;

    @BeforeEach
    public void setup() {
        vrun = new ValidateRun();
    }

    @Test
    public void testDistance() {

        for (int i = 1; i <= 100000; i++) {
            vrun.valDistance(String.valueOf(i));
            assertEquals(i, ((Run) vrun.getRun()).getDistance());
        }

    }

    @Test
    void testDistanceExceptions() {

        assertThrows(IllegalArgumentException.class, () -> {
            vrun.valDistance("0");
        }, "0 should not be allowed to set as distance");

        assertThrows(IllegalArgumentException.class, () -> {
            vrun.valDistance("-100");
        }, "-100 should not be allowed to set as distance");

        assertThrows(NumberFormatException.class, () -> {
            vrun.valDistance(null);
        }, "null should not be allowed to set as distance");

        assertThrows(NumberFormatException.class, () -> {
            vrun.valDistance("");
        }, "empty field should not be allowed to set as distance");

        assertThrows(NumberFormatException.class, () -> {
            vrun.valDistance("tusen");
        }, "Not numeric distance should ");

        assertThrows(NumberFormatException.class, () -> {
            vrun.valDistance("10km");
        }, "null should not be allowed to set as distance");

        assertThrows(IllegalArgumentException.class, () -> {
            vrun.valDistance("100001");
        }, "100001 should not be allowed to set as distance");

        assertThrows(IllegalArgumentException.class, () -> {
            vrun.valDistance("1000000");
        }, "1 000 000 should not be allowed to set as distance");

    }

    @Test
    public void testHr() {
        for (int i = 40; i <= 225; i++) {
            vrun.valMaxHr(String.valueOf(i));
            assertEquals(i, ((Run) vrun.getRun()).getMaxHeartRate(),
                    "Max heartrate should be allowed to be sat for values greater than 39 and less than 226");
        }
    }

    @Test
    public void testHrException() {
        assertThrows(NumberFormatException.class, () -> {
            vrun.valAvgHr("");
        }, "Heartrate shouldn't be allowed to be blank");

        assertThrows(IllegalArgumentException.class, () -> {
            vrun.valAvgHr("230");
        }, "Heartrate shouldn't be allowed to be greater than 225");

        assertThrows(IllegalArgumentException.class, () -> {
            vrun.valAvgHr("39");
        }, "Heartrate shouldn't be allowed to be less than 40");

        assertThrows(IllegalArgumentException.class, () -> {
            vrun.valAvgHr("-200");
        }, "Heartrate shouldn't be allowed to be less than 0");

        assertThrows(NumberFormatException.class, () -> {
            vrun.valAvgHr("hundred");
        }, "Heartrate shouldn't be allowed to be a string");
    }

    @Test
    public void testAvgHr() throws Exception {
        vrun.valMaxHr("180");
        assertEquals(180, ((Run) vrun.getRun()).getMaxHeartRate(), "Max Heartrate should be 108");

        for (int i = 40; i <= 180; i += 10) {
            vrun.valAvgHr(String.valueOf(i));
            assertEquals(i, ((Run) vrun.getRun()).getAvaerageHeartRate(),
                    "Max heartrate should be allowed to be sat for values less or equal than max HR");
        }

        assertThrows(IllegalArgumentException.class, () -> {
            vrun.valAvgHr("181");
        }, "Average heartrate shouldn't be allowed to be greater than maximum hr");

    }
}
