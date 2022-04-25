package trainingDiary.validation;

import java.time.LocalDate;

import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import trainingDiary.Strength;

public class ValidateStrength extends Commons {

    private boolean validationStatus = true;
    private String errorMessage = "";

    private Strength strength = new Strength();

    public ValidateStrength() {
    }

    public ValidateStrength(DatePicker strengthDate, TextField strengthTime, TextField strengthDuration,
            ChoiceBox<String> strengthRating, TextArea strengthComments) {

        try {
            valDate(strengthDate.getValue(), strengthTime.getText());
            styleInput(strengthDate.getEditor(), true);
            styleInput(strengthTime, true);
        } catch (Exception e) {
            validationStatus = false;
            errorMessage += e.getLocalizedMessage();
            styleInput(strengthDate.getEditor(), false);
            styleInput(strengthTime, false);
        }

        try {
            valDuration(strengthDuration.getText());
            styleInput(strengthDuration, true);
        } catch (Exception e) {
            validationStatus = false;
            errorMessage += e.getLocalizedMessage();
            styleInput(strengthDuration, false);
        }

        try {
            valRating(strengthRating.getValue());
            styleInput(strengthRating, true);
        } catch (Exception e) {
            validationStatus = false;
            errorMessage += e.getLocalizedMessage();
            styleInput(strengthRating, false);

        }

        try {
            valComment(strengthComments.getText());
        } catch (Exception e) {
            validationStatus = false;
            errorMessage += "A problem occured while setting comment";
            styleInput(strengthRating, false);
        }

    }

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
    void valDate(LocalDate date, String time) throws Exception {
        super.valDate(date, time, strength);
    }

    /**
     * Uses valDuration from super to validate and set duration if duration is
     * valid.
     * Sets correct errormessage if exception throws during validation.
     * 
     * @param duration String with duration-value
     * @return true/false if duration is valid/invalid
     */
    void valDuration(String duration) throws Exception {
        super.valDuration(duration, strength);
    }

    /**
     * Uses valRating from super to validate rating and set rating if rating has a
     * valid value.
     * Sets correct errormessage is exception is thrown during validation.
     * 
     * @param rating String with value chosen by user
     * @return true/false if rating is valid/invalid
     */
    void valRating(String rating) throws Exception {
        super.valRating(rating, strength);
    }

    /**
     * Uses supers valComment to set comment
     * 
     * @param comment String comment on workout
     * @return true - since comment doesn't have any validation
     */
    void valComment(String comment) {
        super.valComment(comment, strength);
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
