package trainingDiary.validation;

import java.time.LocalDate;

import trainingDiary.Strength;

public class ValidateStrength extends Commons {

    private boolean validationStatus = true;
    private String errorMessage = "";

    private Strength strength = new Strength();

    /**
     * @return validationstatus on Strength
     */
    public boolean isValid() {
        return validationStatus;
    }

    /**
     * Uses valDate from super to validate date and time and set date if date and
     * time has valid values.
     * 
     * Sets correct errormessage if exception is thrown during validation.
     * 
     * @param date LocalDate with datevalue
     * @param time String with timevalue
     * @return true/false if date-time is valid/invalid
     */
    public boolean valDate(LocalDate date, String time) {
        try {
            super.valDate(date, time, strength);
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
     * 
     * @param duration String with duration-value
     * @return true/false if duration is valid/invalid
     */
    public boolean valDuration(String duration) {
        try {
            super.valDuration(duration, strength);
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
     * 
     * @param rating String with value chosen by user
     * @return true/false if rating is valid/invalid
     */
    public boolean valRating(String rating) {
        try {
            super.valRating(rating, strength);
            return true;
        } catch (IllegalArgumentException e) {
            errorMessage += e.getLocalizedMessage();
            validationStatus = false;
            return false;
        }
    }

    /**
     * Uses supers valComment to set comment
     * 
     * @param comment String comment on workout
     * @return true - since comment doesn't have any validation
     */
    public boolean valComment(String comment) {
        super.valComment(comment, strength);
        return true;
    }

    /**
     * @return Strength with valid fields only.
     */
    public Strength getStrength() {
        return strength;
    }

    /**
     * @return String errormessage corresponding to its validity
     */
    public String getMessage() {
        return this.errorMessage;
    }
}
