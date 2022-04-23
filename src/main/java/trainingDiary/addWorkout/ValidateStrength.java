package trainingDiary.addWorkout;

import java.time.LocalDate;

import javax.security.auth.login.FailedLoginException;

import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import trainingDiary.Strength;

public class ValidateStrength extends Commons {

    private boolean validationStatus = true;
    private String errorMessage = "";

    private Strength strength = new Strength();

    // public boolean isValid(DatePicker date, TextField time, TextField duration,
    // ChoiceBox<String> rating,
    // TextArea comments) {

    // valDate(date, time);
    // valDuration(duration);
    // valRating(rating);
    // strength.setComment(comments.getText());

    // return validationStatus;

    // }
    public boolean isValid() {
        return validationStatus;
    }

    /**
     * Uses valDate from super to validate date and time and set date if date and
     * time has valid values.
     * 
     * Sets correct errormessage if exception is thrown during validation.
     * 
     * @param date DatePicker with datevalue
     * @param time TextField with timevalue
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
     * @param duration TextField with duration-value
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
     * @param rating ChoiceBox<String> with value chosen by user
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
