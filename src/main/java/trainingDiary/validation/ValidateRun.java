package trainingDiary.validation;

import java.time.LocalDate;

import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import trainingDiary.Run;
import trainingDiary.IWorkout;

public class ValidateRun extends Commons {

    private Run run = new Run();
    private String message = "";
    boolean validationStatus = true;

    public ValidateRun() {
    }

    public ValidateRun(DatePicker runDate, TextField runTime, TextField runDuration, TextField runDistance,
            ChoiceBox<String> runRating, TextField runMaxHr, TextField runAvgHr, TextArea runComments) {

        try {
            valDate(runDate.getValue(), runTime.getText());
            styleInput(runDate.getEditor(), true);
            styleInput(runTime, true);
        } catch (Exception e) {
            message += e.getLocalizedMessage();
            validationStatus = false;
            styleInput(runDate.getEditor(), false);
            styleInput(runTime, false);
        }

        try {
            valDuration(runDuration.getText());
            styleInput(runDuration, true);
        } catch (Exception e) {
            message += e.getLocalizedMessage();
            validationStatus = false;
            styleInput(runDuration, false);

        }

        try {
            valDistance(runDistance.getText());
            styleInput(runDistance, true);
        } catch (Exception e) {
            message += e.getLocalizedMessage();
            validationStatus = false;
            styleInput(runDistance, false);

        }

        try {
            valRating(runRating.getValue());
            styleInput(runRating, true);
        } catch (Exception e) {
            message += e.getLocalizedMessage();
            validationStatus = false;
            styleInput(runRating, false);
        }

        try {
            valMaxHr(runMaxHr.getText());
            styleInput(runMaxHr, true);
        } catch (Exception e) {
            message += e.getLocalizedMessage();
            validationStatus = false;
            styleInput(runMaxHr, false);
        }

        try {
            valAvgHr(runAvgHr.getText());
            styleInput(runAvgHr, true);
        } catch (Exception e) {
            message += e.getLocalizedMessage();
            validationStatus = false;
            styleInput(runAvgHr, false);
        }

        valComment(runComments.getText());

    }

    public boolean isValid() {
        return validationStatus;
    }

    public String getErrorMessage() {
        return message;
    }

    /**
     * Uses valDate from super to validate date and time and set date if date and
     * time has valid values.
     * 
     * @param date LocalDate with datevalue
     * @param time String with timevalue
     * @return true/false if date-time is valid/invalid
     */
    void valDate(LocalDate date, String time) throws Exception {
        super.valDate(date, time, run);
    }

    /**
     * Uses valDuration from super to validate and set duration if duration is
     * valid.
     * Sets correct errormessage if exception throws during validation.
     * If duration is invalid, validationStatus is set to false
     * 
     * @param duration String with duration-value
     * @return true/false if duration is valid/invalid
     */
    void valDuration(String duration) throws Exception {
        super.valDuration(duration, run);
    }

    /**
     * Uses valRating from super to validate rating and set rating if rating has a
     * valid value.
     * Sets correct errormessage is exception is thrown during validation.
     * If rating is invalid, validationStatus is set to false.
     * 
     * @param rating String with value chosen by user
     * @return true/false if rating is valid/invalid
     */
    void valRating(String rating) throws Exception {
        super.valRating(rating, run);
    }

    /**
     * Validates and sets distance if distance has a valid value.
     * Sets correct errormessage if distance is invalid.
     * If distance is invalid, validationStatus is set to false.
     * 
     * @param distance String with distance-value
     * @return true/false if distance is valid/invalid
     */
    void valDistance(String distance) throws NumberFormatException, IllegalArgumentException {
        try {
            run.setDistance(Integer.valueOf(distance));
            run.setAverageSpeed();
        } catch (NumberFormatException e) {
            throw new NumberFormatException("Invalid distance, distance is not a number\n");
        }
    }

    /**
     * Validates and sets maximum heartrate if maxHr has a valid value.
     * Sets correct errormessage if value is invalid.
     * If maxHR is invalid, validationStatus is set to false.
     * 
     * @param maxHr String with average heartrate-value;
     * @return true/false if maxHR is valid/invalid
     */
    void valMaxHr(String maxHr) throws NumberFormatException, IllegalArgumentException {
        try {
            run.setMaxHeartRate(Integer.valueOf(maxHr));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid maximum heartrate, enter value\n");
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid average heartrate, " + e.getLocalizedMessage() + "\n");
        }
    }

    /**
     * Validates and sets average heartrate if avgHr has a valid value
     * Sets correct errormessage if value is invalid.
     * If avgHr is invalid, validationStatus is set to false.
     * 
     * @param avgHr String with average heartrate-value
     * @return true/false if avgHr is valid/invalid
     */
    void valAvgHr(String avgHr) throws Exception {
        try {
            run.setAvaerageHeartRate(Integer.valueOf(avgHr));
        } catch (NumberFormatException e) {
            throw new NumberFormatException("Invalid average heartrate, enter value\n");
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid average heartrate, " + e.getLocalizedMessage() + "\n");
        }

    }

    /**
     * Uses supers valComment to set comment on exercise
     * 
     * @param comment String with comment on workout
     */
    void valComment(String comment) {
        super.valComment(comment, run);
    }

    /**
     * @return Run with valid fields only
     */
    public IWorkout getRun() {
        return run;
    }

}
