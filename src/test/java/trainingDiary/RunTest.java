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
        run = new Run(LocalDateTime.now().minusSeconds(1), 60, 5000, '3', "Test", 150, 200);
    }

    @Test
    public void testConstructor() {
        LocalDateTime now = LocalDateTime.now().minusSeconds(1);

        assertEquals(now.getYear(), run.getDate().getYear(), "Workout should have same year-value as today");
        assertEquals(now.getMonth(), run.getDate().getMonth(), "Workout should have same month-value as today");
        assertEquals(now.getDayOfMonth(), run.getDate().getDayOfMonth(), "Workout should have same day-value as today");
        assertEquals(now.getHour(), run.getDate().getHour(), "Workout should have same hour-value as today");
        assertEquals(now.getMinute(), run.getDate().getMinute(), "Workout should have same minute-value as today");

        assertEquals(60, run.getDuration(), "Duration should be 60 when sat to 60");
        assertEquals(5000, run.getDistance());
        assertEquals('3', run.getRating(), "Rating should be '3'");
        assertEquals("Test", run.getContent());
        assertEquals(150, run.getAvaerageHeartRate());
        assertEquals(200, run.getMaxHeartRate());

    }

}