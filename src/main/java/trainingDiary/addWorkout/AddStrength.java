package trainingDiary.addWorkout;

import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import trainingDiary.Strength;

public class AddStrength extends Commons {

    private boolean validationStatus = true;
    private String message = "";

    private Strength strength = new Strength();

    public boolean validate(DatePicker date, TextField time, TextField duration,
            ChoiceBox<String> rating,
            TextArea comments) {

        setDate(date, time);
        setDuration(duration);
        setRating(rating);
        strength.setContent(comments.getText());

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
    private void setDate(DatePicker date, TextField time) {
        try {
            super.valDate(date, time, strength);
        } catch (Exception e) {
            message += e.getLocalizedMessage();
            validationStatus = false;
        }
    }

    /**
     * Uses valDuration from super to validate and set duration if duration is
     * valid.
     * Sets correct errormessage if exception throws during validation.
     * 
     * @param duration TextField with duration-value
     */
    private void setDuration(TextField duration) {
        try {
            super.valDuration(duration, strength);
        } catch (Exception e) {
            message += e.getLocalizedMessage();
            validationStatus = false;
        }

    }

    /**
     * Uses valRating from super to validate rating and set rating if rating has a
     * valid value.
     * Sets correct errormessage is exception is thrown during validation.
     * 
     * @param rating ChoiceBox<String> with value chosen by user
     */
    private void setRating(ChoiceBox<String> rating) {
        try {
            super.valRating(rating, strength);
        } catch (IllegalArgumentException e) {
            message += e.getLocalizedMessage();
            validationStatus = false;
        }
    }

    /**
     * Returnerer Workout som er initilaisert med gyldige verdier
     * 
     * @return Workout
     */
    public Strength getStrength() {
        return strength;
    }

    public String getMessage() {
        return this.message;
    }
}
