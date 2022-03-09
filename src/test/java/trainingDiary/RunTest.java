package trainingDiary;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class RunTest {

    private Run run;

    @BeforeEach
    public void setup() {
        Date today = new Date();
        run = new Run(today, 60, 5000, '3', "Test", 150, 200);
    }

}