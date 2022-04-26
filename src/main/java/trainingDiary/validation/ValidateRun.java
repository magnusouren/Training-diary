package trainingDiary.validation;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.regex.PatternSyntaxException;

import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import trainingDiary.Run;
import trainingDiary.IWorkout;

public class ValidateRun extends Commons {

    private Run run = new Run();
    private String errorMessage = "";
    private boolean validationStatus = true;

    public ValidateRun() {
    }

    /**
     * Constructor, runs validation on all inputfields.
     * If inputvalue is valid, field is sat, and inputfield gets green as color.
     * 
     * If inputvalue is invalid, exception is catched, message from exception is
     * added to errormessage, inputfield gets red as backgroundcolor, and
     * validationstatus is sat to false.
     * 
     * @param runDate     DatePicker with datevalue
     * @param runTime     TextField with timevalue on the format hh:mm
     * @param runDuration TextField with duration-value on the format hh:mm
     * @param runDistance TextField with distance value as number
     * @param runRating   ChoiceBox<String> rating in the interval [1,6]
     * @param runMaxHr    TextField with heartrate-value for maximum heartrate
     * @param runAvgHr    TextField with heartrate-value for average heartrate
     * @param runComments TextArea with comments to the workout.
     */
    public ValidateRun(DatePicker runDate, TextField runTime, TextField runDuration, TextField runDistance,
            ChoiceBox<String> runRating, TextField runMaxHr, TextField runAvgHr, TextArea runComments) {

        try {
            valDate(runDate.getValue(), runTime.getText());
            styleInput(runDate.getEditor(), true);
            styleInput(runTime, true);
        } catch (IllegalArgumentException | ArrayIndexOutOfBoundsException | DateTimeException
                | NullPointerException e) {
            setInvalid(e.getLocalizedMessage(), runTime);
            styleInput(runDate.getEditor(), false);
        }

        try {
            valDuration(runDuration.getText());
            styleInput(runDuration, true);
        } catch (PatternSyntaxException | ArrayIndexOutOfBoundsException | NumberFormatException
                | DateTimeException e) {
            setInvalid(e.getLocalizedMessage(), runDuration);
        }

        try {
            valDistance(runDistance.getText());
            styleInput(runDistance, true);
        } catch (IllegalArgumentException e) {
            setInvalid(e.getLocalizedMessage(), runDistance);
        }

        try {
            valRating(runRating.getValue());
            styleInput(runRating, true);
        } catch (IllegalArgumentException e) {
            setInvalid(e.getLocalizedMessage(), runRating);
        }

        try {
            valMaxHr(runMaxHr.getText());
            styleInput(runMaxHr, true);
        } catch (IllegalArgumentException e) {
            setInvalid(e.getLocalizedMessage(), runMaxHr);
        }

        try {
            valAvgHr(runAvgHr.getText());
            styleInput(runAvgHr, true);
        } catch (IllegalArgumentException e) {
            setInvalid(e.getLocalizedMessage(), runAvgHr);
        }

        valComment(runComments.getText());

    }

    public boolean isValid() {
        return validationStatus;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    /**
     * Method to be called when an inputvalue is invalid
     * 
     * @param message String errormessage to be added
     * @param field   Node field to be styled
     */
    private void setInvalid(String message, Node field) {
        errorMessage += message;
        styleInput(field, false);
        validationStatus = false;
    }

    /**
     * Uses valDate from super to validate date and time and sets date if date and
     * time has valid values.
     * 
     * @param date LocalDate with datevalue
     * @param time String with timevalue
     * @throws IllegalArgumentException       If date is in the future
     * @throws PatternSyntaxException         If time is on wrong format
     * @throws ArrayIndexOutOfBoundsException If time is on the wrong format
     * @throws DateTimeException              If time contains invalid timevalues
     * @throws NullPointerException           If date is sat with ilegal time
     */
    void valDate(LocalDate date, String time) throws IllegalArgumentException, PatternSyntaxException,
            ArrayIndexOutOfBoundsException, DateTimeException, NullPointerException {
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
     * @throws PatternSyntaxException         if duration doesn't contains ":"
     * @throws ArrayIndexOutOfBoundsException if duration isn't on the format
     *                                        'hh:mm'
     * @throws NumberFormatException          if hours or/and minutes isn't numeric
     * @throws DateTimeException              if hours or/and minutes has illegal
     *                                        values
     */
    void valDuration(String duration)
            throws PatternSyntaxException, ArrayIndexOutOfBoundsException, NumberFormatException, DateTimeException {
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
    void valRating(String rating) throws IllegalArgumentException {
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
    void valAvgHr(String avgHr) throws NumberFormatException, IllegalArgumentException {
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
