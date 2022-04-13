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

public class AddRun {

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

        setDate(date, validateTime(time), time);
        setDuration(duration);
        setDistance(distance);
        setRating(rating);
        setMaxHr(maxHr);
        setAvgHr(avgHr);

        if (!validationStatus) {
            run = null;
            return false;
        }

        run.setContent(comments.getText());
        run.setAverageSpeed();

        return true;

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
     * Stilsetter Input-field i forhold til gyldigheten på input. Setter validation
     * false om ugyldig verdi.
     * 
     * @param field  Node input-felt
     * @param status boolean gyldig/ugyldig verdi på feltet
     */
    private void styleInput(Node field, boolean status) {

        final PseudoClass errorClass = PseudoClass.getPseudoClass("error");
        final PseudoClass validClass = PseudoClass.getPseudoClass("valid");

        field.pseudoClassStateChanged(validClass, status);
        field.pseudoClassStateChanged(errorClass, !status);
        if (validationStatus == true)
            validationStatus = status;

    }

    /**
     * Validerer duration og stilsetter input-feltet etter gylidhet på input
     * 
     * @param durationVal String med tidverider på formatet (HH:mm)
     */
    private void setDuration(TextField duration) {
        try {
            String durationVal = duration.getText();
            String[] values = durationVal.split(":");

            int hours = Integer.valueOf(values[0]);
            int minutes = Integer.valueOf(values[1]);

            run.setDuration(hours * 60 + minutes);

            styleInput(duration, true);
        } catch (PatternSyntaxException e) {
            styleInput(duration, false);
            message += "Invalid duration, must be on the format 'hh:mm'\n";
        } catch (NumberFormatException e) {
            styleInput(duration, false);
            message += "Invalid duration, must contains numbers on the format hh:mm\n";
        } catch (IllegalArgumentException e) {
            styleInput(duration, false);
            message += "Invalid duration, must be less than 5:00\n";
        }
    }

    /**
     * Setter rating til run, og stilsetter bakgrunn til TextField til grønn. Hvis
     * unntak stilsettes TextField til rød bakgrunn.
     * 
     * @param rating ChoiceBox<String> med tallverdier fra 1-6 + standardverdi
     */
    private void setRating(ChoiceBox<String> rating) {
        try {
            char ratingVal = rating.getValue().charAt(0);
            run.setRating(ratingVal);
            styleInput(rating, true);
        } catch (IllegalArgumentException e) {
            styleInput(rating, false);
            message += "Invalid rating, pick a number\n";
        }
    }

    /**
     * Tar inn dato og tid og lager et LocalDateTime-objekt setter LocalDateTime til
     * run til dette tidspunktet, og stilsetter ChoiceBoxen til å være grønn. Hvis
     * unntak stilsettes ChoiceBoxen til å ha rød bakgrunn
     * 
     * @param date    DatePicker med tilhørende datoverdi
     * @param timeVal LocalTime med tidspunkt
     */
    private void setDate(DatePicker date, LocalTime timeVal, TextField time) {
        try {
            LocalDateTime dateTime = LocalDateTime.of(date.getValue(), timeVal);

            run.setDate(dateTime);
            styleInput(date.getEditor(), true);

        } catch (Exception e) {
            // TODO DateTimeException
            styleInput(date.getEditor(), false);
            styleInput(time, false);
            message += "Invalid date, date couldn't be in the future\n";
        }
    }

    /**
     * Tar inn en textverdi (hh:mm) og gjør om dette til et klokkeslett, stilsetter
     * TextField til å ha grønn bakgrunn. Hvis ugyldig form eller verdi settes
     * TextField til å ha rød bakgrunn.
     * 
     * @param time Textfield med tidspunkt på formen (hh:mm)
     * @return LocalTime klokkeslett basert på inputverdien
     * @return null hvis ugyldig form
     */
    private LocalTime validateTime(TextField time) {
        try {
            String timeVal = time.getText();
            String[] timeValues = timeVal.split(":");

            int hours = Integer.valueOf(timeValues[0]);
            int minutes = Integer.valueOf(timeValues[1]);

            LocalTime localTime = LocalTime.of(hours, minutes);
            styleInput(time, true);
            return localTime;

        } catch (Exception e) {
            styleInput(time, false);
            message += "Invalid timevalue, must be on the format 'hh:mm'\n";
            return null;
        }

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
            message += "Invalid distance, must be greater than 0 and less than 100000\n";
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

        } catch (IllegalArgumentException e) {
            styleInput(avgHr, false);
            message += "Invalid average heartrate, must be between 0 and 225\n";
        }
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

        } catch (IllegalArgumentException e) {
            styleInput(maxHr, false);
            message += "Invalid maximum heartrate, must be between 0 and 225\n";
        }
    }
}
