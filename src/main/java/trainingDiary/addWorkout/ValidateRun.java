package trainingDiary.addWorkout;

import java.time.LocalDate;

import trainingDiary.Run;
import trainingDiary.IWorkout;

public class ValidateRun extends Commons {

    private boolean validationStatus = true;
    private String errorMessage = "";

    private Run run = new Run();

    public boolean isValid() {
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
    public boolean valDate(LocalDate date, String time) {
        try {
            super.valDate(date, time, run);
            return true;
        } catch (Exception e) {
            errorMessage += e.getLocalizedMessage();
            validationStatus = false;
            return false;
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
    public boolean valDuration(String duration) {
        try {
            super.valDuration(duration, run);
            return true;
        } catch (Exception e) {
            errorMessage += e.getLocalizedMessage();
            validationStatus = false;
            return false;
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
    public boolean valRating(String rating) {
        try {
            super.valRating(rating, run);
            return true;
        } catch (IllegalArgumentException e) {
            errorMessage += e.getLocalizedMessage();
            validationStatus = false;
            return false;
        }
    }

    /**
     * Validates and sets distance if distance has a valid value.
     * Sets correct errormessage if distance is invalid.
     * If distance is invalid, validationStatus is set to false.
     * 
     * @param distance TextField with distance-value
     */
    public boolean valDistance(String distance) {
        try {
            run.setDistance(Integer.valueOf(distance));
            if (validationStatus)
                run.setAverageSpeed();
            return true;

        } catch (NumberFormatException e) {
            errorMessage += "Invalid distance, distance is not a number\n";
        } catch (IllegalArgumentException e) {
            errorMessage += e.getLocalizedMessage();
        }
        validationStatus = false;
        return false;

    }

    /**
     * Validates and sets maximum heartrate if maxHr has a valid value.
     * Sets correct errormessage if value is invalid.
     * If maxHR is invalid, validationStatus is set to false.
     * 
     * @param maxHr TextField with average heartrate-value
     */
    public boolean valMaxHr(String maxHr) {
        try {
            run.setMaxHeartRate(Integer.valueOf(maxHr));
            return true;
        } catch (NumberFormatException e) {
            errorMessage += "Invalid maximum heartrate, enter value\n";
        } catch (IllegalArgumentException e) {
            errorMessage += "Invalid average heartrate, " + e.getLocalizedMessage() + "\n";
        }
        validationStatus = false;
        return false;

    }

    /**
     * Validates and sets average heartrate if avgHr has a valid value
     * Sets correct errormessage if value is invalid.
     * If avgHr is invalid, validationStatus is set to false.
     * 
     * @param avgHr TextField with average heartrate-value
     */
    public boolean valAvgHr(String avgHr) {
        try {
            run.setAvaerageHeartRate(Integer.valueOf(avgHr));
            return true;
        } catch (NumberFormatException e) {
            errorMessage += "Invalid average heartrate, enter value\n";
        } catch (IllegalArgumentException e) {
            errorMessage += "Invalid average heartrate, " + e.getLocalizedMessage() + "\n";
        }
        validationStatus = false;
        return false;
    }

    public boolean valComment(String comment) {
        super.valComment(comment, run);
        return true;
    }

    /**
     * @return Run with valid fields only
     */
    public IWorkout getRun() {
        return run;
    }

    /**
     * @return String errormessage corresponding to its validity
     */
    public String getErrorMessage() {
        return this.errorMessage;
    }
}
