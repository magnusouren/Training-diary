package trainingDiary.validation;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.regex.PatternSyntaxException;

import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import trainingDiary.Strength;

public class ValidateStrength {

    private boolean validationStatus = true;
    private String errorMessage = "";

    private Strength strength = new Strength();

    public ValidateStrength() {
    }

    public ValidateStrength(DatePicker strengthDate, TextField strengthTime, TextField strengthDuration,
            ChoiceBox<String> strengthRating, TextArea strengthComments) {

        try {
            valDate(strengthDate.getValue(), strengthTime.getText());
            Commons.styleInput(strengthDate.getEditor(), true);
            Commons.styleInput(strengthTime, true);
        } catch (IllegalArgumentException | ArrayIndexOutOfBoundsException | DateTimeException
                | NullPointerException e) {
            setInvalid(e.getLocalizedMessage(), strengthTime);
            Commons.styleInput(strengthDate.getEditor(), false);
        }

        try {
            valDuration(strengthDuration.getText());
            Commons.styleInput(strengthDuration, true);
        } catch (ArrayIndexOutOfBoundsException | DateTimeException | IllegalArgumentException e) {
            setInvalid(e.getLocalizedMessage(), strengthDuration);
        }

        try {
            valRating(strengthRating.getValue());
            Commons.styleInput(strengthRating, true);
        } catch (IllegalArgumentException e) {
            setInvalid(e.getLocalizedMessage(), strengthRating);
        }

        valComment(strengthComments.getText());

    }

    /**
     * Method to be called when an inputvalue is invalid
     * 
     * @param message String errormessage to be added
     * @param field   Node field to be styled
     */
    private void setInvalid(String message, Node field) {
        this.errorMessage += message;
        Commons.styleInput(field, false);
        validationStatus = false;
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
    void valDate(LocalDate date, String time) throws IllegalArgumentException, PatternSyntaxException,
            ArrayIndexOutOfBoundsException, DateTimeException, NullPointerException {
        Commons.valDate(date, time, strength);
    }

    /**
     * Uses valDuration from super to validate and set duration if duration is
     * valid.
     * Sets correct errormessage if exception throws during validation.
     * 
     * @param duration String with duration-value
     * @return true/false if duration is valid/invalid
     */
    void valDuration(String duration)
            throws PatternSyntaxException, ArrayIndexOutOfBoundsException, NumberFormatException, DateTimeException,
            IllegalArgumentException {
        Commons.valDuration(duration, strength);
    }

    /**
     * Uses valRating from super to validate rating and set rating if rating has a
     * valid value.
     * Sets correct errormessage is exception is thrown during validation.
     * 
     * @param rating String with value chosen by user
     * @return true/false if rating is valid/invalid
     */
    void valRating(String rating) throws IllegalArgumentException {
        Commons.valRating(rating, strength);
    }

    /**
     * Uses supers valComment to set comment
     * 
     * @param comment String comment on workout
     * @return true - since comment doesn't have any validation
     */
    void valComment(String comment) {
        Commons.valComment(comment, strength);
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
