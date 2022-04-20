package trainingDiary.addWorkout;

import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.regex.PatternSyntaxException;

import javafx.css.PseudoClass;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import trainingDiary.IWorkout;

public class Commons {
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

    }

    /**
     * Validerer duration og stilsetter input-feltet etter gylidhet på input
     * 
     * @param durationVal String med tidverider på formatet (HH:mm)
     */
    protected IWorkout setDuration(TextField duration, IWorkout workout)
            throws PatternSyntaxException, ArrayIndexOutOfBoundsException, NumberFormatException, DateTimeException,
            IllegalArgumentException {

        styleInput(duration, false);
        String durationVal = duration.getText();
        String[] values = durationVal.split(":");

        int hours = Integer.valueOf(values[0]);
        int minutes = Integer.valueOf(values[1]);

        LocalTime t = LocalTime.of(hours, minutes);
        hours = t.getHour() * 60;
        minutes = t.getMinute();

        workout.setDuration(hours + minutes);
        styleInput(duration, true);

        return workout;

    }

    /**
     * Setter rating til run, og stilsetter bakgrunn til TextField til grønn. Hvis
     * unntak stilsettes TextField til rød bakgrunn.
     * 
     * @param rating ChoiceBox<String> med tallverdier fra 1-6 + standardverdi
     */
    protected IWorkout setRating(ChoiceBox<String> rating, IWorkout workout) throws IllegalArgumentException {
        styleInput(rating, false);
        char ratingVal = rating.getValue().charAt(0);
        workout.setRating(ratingVal);
        styleInput(rating, true);
        return workout;
    }

    /**
     * Tar inn dato og tid og lager et LocalDateTime-objekt setter LocalDateTime til
     * run til dette tidspunktet, og stilsetter ChoiceBoxen til å være grønn. Hvis
     * unntak stilsettes ChoiceBoxen til å ha rød bakgrunn
     * 
     * @param date    DatePicker med tilhørende datoverdi
     * @param timeVal LocalTime med tidspunkt
     */
    protected IWorkout setDate(DatePicker date, TextField time, IWorkout workout)
            throws IllegalArgumentException, NullPointerException, DateTimeException {

        LocalTime timeValue;

        try {
            timeValue = validateTime(time);
        } catch (Exception e) {
            throw new DateTimeException(e.getLocalizedMessage());
        }

        styleInput(date, false);
        LocalDateTime dateTime = LocalDateTime.of(date.getValue(), timeValue);
        workout.setDate(dateTime);
        styleInput(date, true);

        return workout;

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
    private LocalTime validateTime(TextField time)
            throws IllegalArgumentException, ArrayIndexOutOfBoundsException, DateTimeException {

        styleInput(time, false);

        String timeVal = time.getText();
        String[] timeValues = timeVal.split(":");

        int hours = Integer.valueOf(timeValues[0]);
        int minutes = Integer.valueOf(timeValues[1]);

        LocalTime localTime = LocalTime.of(hours, minutes);
        styleInput(time, true);

        return localTime;
    }

}
