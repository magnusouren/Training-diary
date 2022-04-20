package trainingDiary.addWorkout;

import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import trainingDiary.Run;
import trainingDiary.IWorkout;

public class AddRun extends Commons {

    private boolean validationStatus = true;
    private String errorMessage = "";

    private Run run = new Run();

    /**
     * Method that calls on all methods the inputs that needs to be validatetd to
     * initialize a valid
     * Run-object.
     * 
     * @param date     DatePicker with datevalue
     * @param time     TextField with timevalue
     * @param duration TextField with duration-value
     * @param distance TextField with distance-value
     * @param rating   ChoiceBox<String> with rating-value
     * @param maxHr    TextField with maximum heartrate-value
     * @param avgHr    TextField with average heartrate-value
     * @param comments TextArea with comments to the workout
     * @return
     */
    public boolean validate(DatePicker date, TextField time, TextField duration, TextField distance,
            ChoiceBox<String> rating,
            TextField maxHr,
            TextField avgHr,
            TextArea comments) {

        valDate(date, time);
        valDuration(duration);
        valDistance(distance);
        valRating(rating);
        valMaxHr(maxHr);
        valAvgHr(avgHr);
        run.setContent(comments.getText());

        if (validationStatus)
            run.setAverageSpeed();

        return validationStatus;

    }

    /**
     * Uses valDate from super to validate date and time and set date if date and
     * time has valid values.
     * Sets correct errormessage if exception is thrown during validation.
     * If date is invalid, validationStatus is set to false
     * 
     * @param date DatePicker with datevalue
     * @param time TextField with timevalue
     */
    private void valDate(DatePicker date, TextField time) {
        try {
            super.valDate(date, time, run);
        } catch (Exception e) {
            errorMessage += e.getLocalizedMessage();
            validationStatus = false;
        }

    }

    /**
     * Uses valDuration from super to validate and set duration if duration is
     * valid.
     * Sets correct errormessage if exception throws during validation.
     * If duration is invalid, validationStatus is set to false
     * 
     * @param duration TextField with duration-value
     */
    private void valDuration(TextField duration) {
        try {
            super.valDuration(duration, run);
        } catch (Exception e) {
            errorMessage += e.getLocalizedMessage();
            validationStatus = false;
        }

    }

    /**
     * Uses valRating from super to validate rating and set rating if rating has a
     * valid value.
     * Sets correct errormessage is exception is thrown during validation.
     * If rating is invalid, validationStatus is set to false.
     * 
     * @param rating ChoiceBox<String> with value chosen by user
     */
    private void valRating(ChoiceBox<String> rating) {
        try {
            super.valRating(rating, run);
        } catch (IllegalArgumentException e) {
            errorMessage += e.getLocalizedMessage();
            validationStatus = false;
        }
    }

    /**
     * Validates and sets distance if distance has a valid value.
     * Sets correct errormessage if distance is invalid.
     * If distance is invalid, validationStatus is set to false.
     * 
     * @param distance TextField with distance-value
     */
    private void valDistance(TextField distance) {
        try {
            String distanceVal = distance.getText();
            run.setDistance(Integer.valueOf(distanceVal));
            styleInput(distance, true);
            return;

        } catch (NumberFormatException e) {
            errorMessage += "Invalid distance, distance is not a number\n";
        } catch (IllegalArgumentException e) {
            errorMessage += e.getLocalizedMessage();
        }

        styleInput(distance, false);
        validationStatus = false;

    }

    /**
     * Validates and sets maximum heartrate if maxHr has a valid value.
     * Sets correct errormessage if value is invalid.
     * If maxHR is invalid, validationStatus is set to false.
     * 
     * @param maxHr TextField with average heartrate-value
     */
    private void valMaxHr(TextField maxHr) {
        try {
            String maxHrVal = maxHr.getText();
            run.setMaxHeartRate(Integer.valueOf(maxHrVal));
            styleInput(maxHr, true);
            return;
        } catch (NumberFormatException e) {
            errorMessage += "Invalid maximum heartrate, enter value\n";
        } catch (IllegalArgumentException e) {
            errorMessage += "Invalid average heartrate, " + e.getLocalizedMessage() + "\n";
        }
        validationStatus = false;
        styleInput(maxHr, false);

    }

    /**
     * Validates and sets average heartrate if avgHr has a valid value
     * Sets correct errormessage if value is invalid.
     * If avgHr is invalid, validationStatus is set to false.
     * 
     * @param avgHr TextField with average heartrate-value
     */
    private void valAvgHr(TextField avgHr) {
        try {
            String avgHrVal = avgHr.getText();
            run.setAvaerageHeartRate(Integer.valueOf(avgHrVal));
            styleInput(avgHr, true);
            return;
        } catch (NumberFormatException e) {
            errorMessage += "Invalid average heartrate, enter value\n";
        } catch (IllegalArgumentException e) {
            errorMessage += "Invalid average heartrate, " + e.getLocalizedMessage() + "\n";
        }
        validationStatus = false;
        styleInput(avgHr, false);

    }

    /**
     * @return Run with valid fields only
     */
    public IWorkout getRun() {
        return run;
    }

    /**
     * @return String errormessage in terms of validity
     */
    public String getErrorMessage() {
        return this.errorMessage;
    }
}
