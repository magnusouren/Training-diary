package trainingDiary;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RunTest {

    private Run run;

    @BeforeEach
    public void setup() {
        run = new Run();
    }

    @Test
    public void testSetDate() {
        LocalDateTime now = LocalDateTime.now();
        run.setDate(now);
        assertEquals(now, run.getDate(), "Dates should be equal when date is sat");

        LocalDateTime date = LocalDateTime.of(2022, 01, 01, 12, 00);
        run.setDate(date);
        assertEquals(date, run.getDate(), "Dates should be equals when sat");

        assertThrows(IllegalArgumentException.class, () -> {
            run.setDate(LocalDateTime.now().plusDays(1));
        }, "Date should not be allowed to be set to the future");

        assertThrows(IllegalArgumentException.class, () -> {
            run.setDate(LocalDateTime.now().plusMinutes(1));
        }, "Time should not be allowed to be set to the future");
    }

    @Test
    public void testSetDuration() {
        for (int i = 30; i < 300; i += 30) {
            run.setDuration(i);
            assertEquals(i, run.getDuration(), "Duration should be " + i + " when sat to " + i);
        }
        assertThrows(IllegalArgumentException.class, () -> {
            run.setDuration(0);
        }, "Date should not be allowed to be set to 0");

        assertThrows(IllegalArgumentException.class, () -> {
            run.setDuration(-10);
        }, "Date should not be allowed to be set to 0");

        assertThrows(IllegalArgumentException.class, () -> {
            run.setDuration(1000);
        }, "Date should not be allowed to be set to 1000");

    }

    @Test
    public void testSetDistance() {

        for (int i = 1000; i < 100000; i += 500) {
            run.setDistance(i);
            assertEquals(i, run.getDistance(), "Distance should be" + i + " when sat to" + i);
        }

        assertThrows(IllegalArgumentException.class, () -> {
            run.setDistance(0);
        }, "Distance should not be allowed to be set to 0");

        assertThrows(IllegalArgumentException.class, () -> {
            run.setDistance(-10);
        }, "Distance should not be allowed to be set to -10");

        assertThrows(IllegalArgumentException.class, () -> {
            run.setDistance(200000);
        }, "Distance should not be allowed to be set to 200000");
    }

    @Test
    public void testSetRating() {
        char[] values = { '1', '2', '3', '4', '5', '6' };

        for (int i = 0; i < values.length; i++) {
            run.setRating(values[i]);
            assertEquals(values[i], run.getRating(), "Ratin should be " + values[i] + " when sat");
        }

        assertThrows(IllegalArgumentException.class, () -> {
            run.setRating('0');
        }, "Rating should not be allowed to be set to 0");

        assertThrows(IllegalArgumentException.class, () -> {
            run.setRating('7');
        }, "Rating should not be allowed to be set to 7");

        assertThrows(IllegalArgumentException.class, () -> {
            run.setRating('A');
        }, "Rating should not be allowed to be set to A");

    }

    @Test
    public void testMaxHr() {
        for (int i = 10; i < 225; i += 10) {
            run.setMaxHeartRate(i);
            assertEquals(i, run.getMaxHeartRate(), "Maximum heartRate Should be " + i + " when sat");
        }

        assertThrows(IllegalArgumentException.class, () -> {
            run.setMaxHeartRate(0);
        }, "Max hr should not be allowed to be set to 0");

        assertThrows(IllegalArgumentException.class, () -> {
            run.setMaxHeartRate(226);
        }, "Max hr should not be allowed to be set to 226");

        assertThrows(IllegalArgumentException.class, () -> {
            run.setMaxHeartRate(1000);
        }, "Max hr should not be allowed to be set to 1000");

        assertThrows(IllegalArgumentException.class, () -> {
            run.setMaxHeartRate(-10);
        }, "Max hr should not be allowed to be negative");
    }

    @Test
    public void testAvgHr() {
        run.setMaxHeartRate(225);

        for (int i = 10; i < 225; i += 10) {
            run.setAvaerageHeartRate(i);
            assertEquals(i, run.getAvaerageHeartRate(), "Average heartRate Should be " + i + " when sat");
        }

        run.setMaxHeartRate(150);

        for (int i = 10; i < 150; i += 10) {
            run.setAvaerageHeartRate(i);
            assertEquals(i, run.getAvaerageHeartRate(), "Average heartRate Should be " + i + " when sat");
        }

        assertThrows(IllegalArgumentException.class, () -> {
            for (int i = 155; i < 225; i += 10) {
                run.setAvaerageHeartRate(i);
            }
        }, "Avg hr should not be allowed to be set greater than max hr");

    }

    @Test
    public void testAverageSpeed() {
        run.setDuration(60);

        for (int i = 1000; i < 20000; i += 1000) {
            run.setDistance(i);
            run.setAverageSpeed();
            assertEquals(i / 1000, run.getAverageSpeed(), "Average speed should be " + i / 1000 + " when sat");
        }

        run.setDistance(10000);

        for (int i = 30; i < 300; i += 30) {
            run.setDuration(i);
            run.setAverageSpeed();
            assertEquals((600 / (double) i), run.getAverageSpeed(), 0.00001,
                    "Average speed should be " + 600 / ((double) i) + " when sat");
        }

    }

}