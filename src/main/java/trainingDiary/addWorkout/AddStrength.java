package trainingDiary.addWorkout;

import java.time.LocalDateTime;
import java.time.LocalTime;

import javafx.css.PseudoClass;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import trainingDiary.Strength;

public class AddStrength {

    private boolean validationStatus;

    private Strength strength = new Strength();

    public boolean validate(DatePicker date, TextField time, TextField duration, ChoiceBox<String> rating,
            TextArea comments) {

        validationStatus = true;

        setDate(date, validateTime(time));
        setDuration(duration);
        setRating(rating);

        if (!validationStatus) {
            strength = null;
            return false;
        }

        return true;

    }

    /**
     * Returnerer Workout som er initilaisert med gyldige verdier
     * 
     * @return Workout
     */
    public Strength getStrength() {
        return strength;
    }

    /**
     * Stilsetter Input-field i forhold til gyldigheten på input. Setter validation
     * false om ugyldig verdi.
     * 
     * @param field  Node input-felt
     * @param status boolean gyldig/ugyldig verdi på feltet
     */
    private void styleInput(Node field, boolean status) {

        final PseudoClass errorClass = PseudoClass.getPseudoClass("error");
        final PseudoClass validClass = PseudoClass.getPseudoClass("valid");

        field.pseudoClassStateChanged(validClass, status);
        field.pseudoClassStateChanged(errorClass, !status);
        if (validationStatus == true)
            validationStatus = status;

    }

    /**
     * Validerer duration og stilsetter input-feltet etter gylidhet på input
     * 
     * @param durationVal String med tidverider på formatet (HH:mm)
     */
    private void setDuration(TextField duration) {
        try {
            String durationVal = duration.getText();
            String[] values = durationVal.split(":");

            int hours = Integer.valueOf(values[0]);
            int minutes = Integer.valueOf(values[1]);

            strength.setDuration(hours * 60 + minutes);

            styleInput(duration, true);
        } catch (Exception e) {
            styleInput(duration, false);
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
            char ratingVal = rating.getValue().charAt(0);
            strength.setRating(ratingVal);
            styleInput(rating, true);
        } catch (IllegalArgumentException e) {
            styleInput(rating, false);
        }
    }

    /**
     * Tar inn dato og tid og lager et LocalDateTime-objekt setter LocalDateTime til
     * run til dette tidspunktet, og stilsetter ChoiceBoxen til å være grønn. Hvis
     * unntak stilsettes ChoiceBoxen til å ha rød bakgrunn
     * 
     * @param date    DatePicker med tilhørende datoverdi
     * @param timeVal LocalTime med tidspunkt
     */
    private void setDate(DatePicker date, LocalTime timeVal) {
        try {
            LocalDateTime dateTime = LocalDateTime.of(date.getValue(), timeVal);

            strength.setDate(dateTime);
            styleInput(date, true);

        } catch (RuntimeException e) {
            styleInput(date, false);
        }
    }

    /**
     * Tar inn en textverdi (hh:mm) og gjør om dette til et klokkeslett, stilsetter
     * TextField til å ha grønn bakgrunn. Hvis ugyldig form eller verdi settes
     * TextField til å ha rød bakgrunn.
     * 
     * @param time Textfield med tidspunkt på formen (hh:mm)
     * @return LocalTime klokkeslett basert på inputverdien
     * @return null hvis ugyldig form
     */
    private LocalTime validateTime(TextField time) {
        try {
            String timeVal = time.getText();
            String[] timeValues = timeVal.split(":");

            int hours = Integer.valueOf(timeValues[0]);
            int minutes = Integer.valueOf(timeValues[1]);

            LocalTime localTime = LocalTime.of(hours, minutes);
            styleInput(time, true);
            return localTime;

        } catch (Exception e) {
            styleInput(time, false);
            return null;
        }

    }
}
