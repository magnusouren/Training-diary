package trainingDiary.addWorkout;

import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.regex.PatternSyntaxException;

import javafx.css.PseudoClass;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import trainingDiary.Run;
import trainingDiary.IWorkout;

public class AddRun extends Commons {

    private boolean validationStatus;
    private String message;

    private Run run = new Run();

    public boolean validate(DatePicker date, TextField time, TextField duration, TextField distance,
            ChoiceBox<String> rating,
            TextField maxHr,
            TextField avgHr,
            TextArea comments) {

        validationStatus = true;
        message = "";

        setDate(date, time);
        setDuration(duration);
        setDistance(distance);
        setRating(rating);
        setMaxHr(maxHr);
        setAvgHr(avgHr);
        run.setContent(comments.getText());

        if (validationStatus)
            run.setAverageSpeed();

        return validationStatus;

    }

    /**
     * Returnerer Workout som er initilaisert med gyldige verdier
     * 
     * @return Workout
     */
    public IWorkout getRun() {
        IWorkout res = run;
        return res;
    }

    public String getMessage() {
        return this.message;
    }

    /**
     * Validerer duration og stilsetter input-feltet etter gylidhet på input
     * 
     * @param durationVal String med tidverider på formatet (HH:mm)
     */
    private void setDuration(TextField duration) {
        try {
            super.setDuration(duration, run);
            return;

        } catch (PatternSyntaxException e) {
            message += "Invalid duration, must be on the format 'hh:mm'\n";
        } catch (ArrayIndexOutOfBoundsException e) {
            message += "Invalid duration, must be on the format 'hh:mm'\n";
        } catch (NumberFormatException e) {
            message += "Invalid duration, must contains numbers on the format hh:mm\n";
        } catch (DateTimeException e) {
            message += "Invalid duration, invalid values for hours and/or minutes\n";
        } catch (IllegalArgumentException e) {
            message += e.getLocalizedMessage() + "\n";
        }
        validationStatus = false;

    }

    /**
     * Setter rating til run, og stilsetter bakgrunn til TextField til grønn. Hvis
     * unntak stilsettes TextField til rød bakgrunn.
     * 
     * @param rating ChoiceBox<String> med tallverdier fra 1-6 + standardverdi
     */
    private void setRating(ChoiceBox<String> rating) {
        try {
            super.setRating(rating, run);
            return;
        } catch (IllegalArgumentException e) {
            message += e.getLocalizedMessage() + "\n";
        }
        validationStatus = false;
    }

    /**
     * Tar inn dato og tid og lager et LocalDateTime-objekt setter LocalDateTime til
     * run til dette tidspunktet, og stilsetter ChoiceBoxen til å være grønn. Hvis
     * unntak stilsettes ChoiceBoxen til å ha rød bakgrunn
     * 
     * @param date    DatePicker med tilhørende datoverdi
     * @param timeVal LocalTime med tidspunkt
     */
    private void setDate(DatePicker date, TextField time) {
        try {
            super.setDate(date, time, run);
            return;
        } catch (IllegalArgumentException e) {
            message += e.getLocalizedMessage() + "\n";
        } catch (DateTimeException e) {
            message += e.getLocalizedMessage();
        }
        validationStatus = false;

    }

    /**
     * Tar inn en tekstverdi med distansen på økten, gjør om dette til tallverdi og
     * setter distansen til run. Stilsetter TextField i forhold til gyldighet
     * 
     * @param distance TextField med distanse på turen
     */
    private void setDistance(TextField distance) {
        try {
            String distanceVal = distance.getText();
            run.setDistance(Integer.valueOf(distanceVal));
            styleInput(distance, true);

        } catch (IllegalArgumentException e) {
            styleInput(distance, false);
            message += "Invalid distance, must a number be greater than 0 and less than 100000\n";
            validationStatus = false;
        }

    }

    /**
     * Tar inn tekstverdi for gjennomsnittspuls, og setter denne verdien til run.
     * Stilsetter TextField i forhold til gyldighet
     * 
     * @param avgHr TextField med verdi for gjennomsnittspuls
     */
    private void setAvgHr(TextField avgHr) {
        try {
            String avgHrVal = avgHr.getText();
            run.setAvaerageHeartRate(Integer.valueOf(avgHrVal));
            styleInput(avgHr, true);
            return;
        } catch (NumberFormatException e) {
            message += "Invalid average heartrate, enter value\n";
        } catch (IllegalArgumentException e) {
            message += "Invalid average heartrate, " + e.getLocalizedMessage() + "\n";
        }
        validationStatus = false;
        styleInput(avgHr, false);

    }

    /**
     * Tar inn tekstverdi for makspuls, og setter denne verdien til run.
     * Stilsetter TextField i forhold til gyldighet
     * 
     * @param maxHr TextField med verdi for makspuls
     */
    private void setMaxHr(TextField maxHr) {
        try {
            String maxHrVal = maxHr.getText();
            run.setMaxHeartRate(Integer.valueOf(maxHrVal));
            styleInput(maxHr, true);
            return;
        } catch (NumberFormatException e) {
            message += "Invalid maximum heartrate, enter value\n";
        } catch (IllegalArgumentException e) {
            message += "Invalid average heartrate, " + e.getLocalizedMessage() + "\n";
        }
        validationStatus = false;
        styleInput(maxHr, false);

    }
}
