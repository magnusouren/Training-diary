package trainingDiary;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class RunTest {

    private Run run;

    @BeforeEach
    public void setup() {
        run = new Run(LocalDateTime.now(), 60, 5000, '3', "Test", 150, 200);
    }

    @Test
    public void testConstructor() {

        assertEquals(5000, run.getDistance());
        assertEquals('3', run.getRating(), "Rating should be '3'");
        assertEquals("Test", run.getContent());
        assertEquals(150, run.getAvaerageHeartRate());
        assertEquals(200, run.getMaxHeartRate());

    }

    @Test
    public void testDate() {

        // Sjekker satt dato opp mot nå, nøyaktig nok i denne sammenhengen
        LocalDateTime date = LocalDateTime.now();
        assertEquals(date.getYear(), run.getDate().getYear(), "Workout should have same year-value as today");
        assertEquals(date.getMonth(), run.getDate().getMonth(), "Workout should have same month-value as today");
        assertEquals(date.getDayOfMonth(), run.getDate().getDayOfMonth(),
                "Workout should have same day-value as today");
        assertEquals(date.getHour(), run.getDate().getHour(), "Workout should have same hour-value as today");
        assertEquals(date.getMinute(), run.getDate().getMinute(), "Workout should have same minute-value as today");

        run = new Run(LocalDateTime.of(2021, 3, 2, 12, 00), 60, 5000, '3', "Test", 150, 200);

        // Sjekker datoer opp mot hverandre
        date = LocalDateTime.of(2021, 3, 2, 12, 00);
        assertEquals(date.getYear(), run.getDate().getYear(), "Workout should have same year-value as date set");
        assertEquals(date.getMonth(), run.getDate().getMonth(), "Workout should have same month-value as date set");
        assertEquals(date.getDayOfMonth(), run.getDate().getDayOfMonth(),
                "Workout should have same day-value as today");
        assertEquals(date.getHour(), run.getDate().getHour(), "Workout should have same hour-value as date set");
        assertEquals(date.getMinute(), run.getDate().getMinute(), "Workout should have same minute-value as date set");

        assertThrows(IllegalArgumentException.class, () -> {
            run = new Run(LocalDateTime.of(2023, 3, 2, 12, 00), 60, 5000, '3', "Test", 150, 200);
        }, "Datoer frem i tid skal ikke kunne settes?");
    }

    @Test
    public void testDuration() {
        assertEquals(60, run.getDuration(), "Duration should be 60 when sat to 60");

    }
}