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
     * Styles node dependning on it's validity
     * 
     * @param field  Node input-field
     * @param status boolean valid/invalid input value
     */
    protected void styleInput(Node field, boolean status) {

        final PseudoClass errorClass = PseudoClass.getPseudoClass("error");
        final PseudoClass validClass = PseudoClass.getPseudoClass("valid");

        field.pseudoClassStateChanged(validClass, status);
        field.pseudoClassStateChanged(errorClass, !status);

    }

    /**
     * Validates duration and styles relative to its validity
     * Sets duration if values are valid
     * 
     * @param duration TextField with timevalue
     * @param workout  IWorkout that gets duration set
     * @return IWorkout with duration sat
     * @throws PatternSyntaxException         If timevalue doesn't contains ":"
     * @throws ArrayIndexOutOfBoundsException If format on timevalue is invalid
     * @throws NumberFormatException          If timevalues doesn't contains numbers
     * @throws DateTimeException              If values doesn't represents a valid
     *                                        time
     * @throws IllegalArgumentException       If duration on workout is invalid due
     *                                        to the conditions sat.
     */
    protected IWorkout valDuration(TextField duration, IWorkout workout)
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
     * Takes in choose of rating and sets if it's a legal value.
     * 
     * @param rating  ChoiceBox<String> with chosen value
     * @param workout IWorkout that gets rating set
     * @return IWorkout with rating sat
     * @throws IllegalArgumentException If chosen value is not in the interval 1-6.
     */
    protected IWorkout valRating(ChoiceBox<String> rating, IWorkout workout) throws IllegalArgumentException {
        styleInput(rating, false);
        char ratingVal = rating.getValue().charAt(0);
        workout.setRating(ratingVal);
        styleInput(rating, true);
        return workout;
    }

    /**
     * Validates date and time-values validates, styles the fields relative to its
     * validity. Sets LocalDateTime to workout if values are valid
     * 
     * @param date    DatePicker with date-value
     * @param time    TextField with time-value
     * @param workout IWorkout to get date set
     * @return IWorkout with date sat
     * @throws IllegalArgumentException If datetime is in the future
     * @throws NullPointerException     If format on time is invalid when
     *                                  LocalDateTime is set
     * @throws DateTimeException        If time is invalid
     */
    protected IWorkout valDate(DatePicker date, TextField time, IWorkout workout)
            throws IllegalArgumentException, NullPointerException, DateTimeException {

        LocalTime timeValue;
        String message = "Illegal date, cannot set date with illegal time\n";

        styleInput(date.getEditor(), false);
        styleInput(time, false);

        try {
            timeValue = valTime(time);
        } catch (IllegalArgumentException e) {
            throw new DateTimeException(message + "Invalid time, must contain numbers on the format hh:mm\n");
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new DateTimeException(message + "Invalid time, must be on the format 'hh:mm'\n");
        } catch (DateTimeException e) {
            throw new DateTimeException(message + "Invalid time, invalid values for hours and/or minutes\n");
        }

        LocalDateTime dateTime = LocalDateTime.of(date.getValue(), timeValue);
        workout.setDate(dateTime);
        styleInput(date.getEditor(), true);
        styleInput(time, true);

        return workout;

    }

    /**
     * Validates timevalue
     * 
     * @param time TextField with timevalue
     * @return LocalTime value of the time when workout started
     * @throws IllegalArgumentException       If timevalue is not a string
     * @throws ArrayIndexOutOfBoundsException If format on time is illegal and
     *                                        values cannot be found
     * @throws DateTimeException              If the value of any field is out of
     *                                        range
     */
    private LocalTime valTime(TextField time)
            throws IllegalArgumentException, ArrayIndexOutOfBoundsException, DateTimeException {

        String timeVal = time.getText();
        String[] timeValues = timeVal.split(":");

        int hours = Integer.valueOf(timeValues[0]);
        int minutes = Integer.valueOf(timeValues[1]);

        LocalTime localTime = LocalTime.of(hours, minutes);

        return localTime;
    }

}
