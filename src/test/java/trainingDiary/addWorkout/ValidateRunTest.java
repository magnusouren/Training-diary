package trainingDiary.addWorkout;

import java.time.LocalDate;

import javafx.application.Application;
import javafx.application.Platform;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import trainingDiary.Run;
import trainingDiary.trainingDiaryApp;
import trainingDiary.addWorkout.ValidateRun;

public class ValidateRunTest {

    ValidateRun vrun;
    Run run;

    DatePicker date;
    TextField time, duration, distance, maxHr, avgHr;
    ChoiceBox<String> rating;
    TextArea comments;

    @BeforeEach
    public void setup() {

        Application.launch();
        vrun = new ValidateRun();
        run = new Run();

        date = new DatePicker();
        time = new TextField();
        duration = new TextField();
        distance = new TextField();
        maxHr = new TextField();
        avgHr = new TextField();
        rating = new ChoiceBox<String>();
        comments = new TextArea();
    }

    @Test
    public void testDate() {
        LocalDate dateValue = LocalDate.of(2022, 01, 01);
        date.setValue(dateValue);
        time.setText("12:00");

        vrun.valDate(date, time, run);

    }

}
