package trainingDiary;

import java.time.LocalDate;
import java.util.spi.LocaleServiceProvider;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import trainingDiary.addWorkout.ValidateRun;

public class ValidateRunTest {

    ValidateRun vrun;

    DatePicker date;
    TextField time, duration, distance, maxHr, avgHr;
    ChoiceBox<String> rating;
    TextArea comments;

    @BeforeEach
    public void setup() {
        vrun = new ValidateRun();
    }

    @Test
    public void testDate() {

        vrun.isValid(date, time, duration, distance, rating, maxHr, avgHr, comments);
    }

}
