package trainingDiary.addWorkout;

import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.regex.PatternSyntaxException;

import javafx.application.Application;
import javafx.css.PseudoClass;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import trainingDiary.Strength;

public class AddStrength extends Commons {

    private boolean validationStatus;
    private String message;

    private Strength strength = new Strength();

    public boolean validate(DatePicker date, TextField time, TextField duration,
            ChoiceBox<String> rating,
            TextArea comments) {

        validationStatus = true;
        message = "";

        setDate(date, time);
        setDuration(duration);
        setRating(rating);
        strength.setContent(comments.getText());

        return validationStatus;

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

    /**
     * Validerer duration og stilsetter input-feltet etter gylidhet på input
     * 
     * @param durationVal String med tidverider på formatet (HH:mm)
     */
    private void setDuration(TextField duration) {
        try {
            super.setDuration(duration, strength);

        } catch (PatternSyntaxException e) {
            message += "Invalid duration, must be on the format 'hh:mm'\n";
        } catch (ArrayIndexOutOfBoundsException e) {
            message += "Invalid duration, must be on the format 'hh:mm'\n";
        } catch (NumberFormatException e) {
            message += "Invalid duration, must contains numbers on the format hh:mm\n";
        } catch (DateTimeException e) {
            message += "Invalid duration, invalid values for hours and/or minutes\n";
        } catch (IllegalArgumentException e) {
            message += e.getLocalizedMessage() + "\n";
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
            super.setRating(rating, strength);
        } catch (IllegalArgumentException e) {
            message += e.getLocalizedMessage() + "\n";
        }
    }

    /**
     * Tar inn dato og tid og lager et LocalDateTime-objekt setter LocalDateTime
     * til
     * run til dette tidspunktet, og stilsetter ChoiceBoxen til å være grønn. Hvis
     * unntak stilsettes ChoiceBoxen til å ha rød bakgrunn
     *
     * @param date    DatePicker med tilhørende datoverdi
     * @param timeVal LocalTime med tidspunkt
     */
    private void setDate(DatePicker date, TextField time) {
        try {
            super.setDate(date, time, strength);
        } catch (IllegalArgumentException e) {
            message += e.getLocalizedMessage() + "\n";
        } catch (NullPointerException e) {
            message += "Invalid date, can not set date with illegal time\n";
        }
    }

}
