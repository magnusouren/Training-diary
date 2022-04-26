package trainingDiary.validation;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import javafx.css.PseudoClass;
import javafx.scene.Node;
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
     * Validates and sets date and time of workout
     * 
     * @param date    LocalDate of workout
     * @param time    String timevalue on format hh:mm
     * @param workout IWorkout to get date
     * @throws IllegalArgumentException       If date i sin the future
     * @throws ArrayIndexOutOfBoundsException If time is on the wrong format
     * @throws DateTimeException              If time contains invalid timevalues
     * @throws NullPointerException           If date is sat with ilegal time
     */
    protected void valDate(LocalDate date, String time, IWorkout workout)
            throws IllegalArgumentException, ArrayIndexOutOfBoundsException, DateTimeException, NullPointerException {
        LocalDateTime dateTime = LocalDateTime.of(date, valTime(time));
        workout.setDate(dateTime);

    }

    /**
     * Validates duration and sets duration if values are valid.
     * 
     * @param duration String with timevalue on format hh:mm
     * @param workout  IWorkout that gets duration set
     * @return IWorkout with valid duration
     * @throws NullPointerException           if duration is null
     * @throws ArrayIndexOutOfBoundsException if duration isn't on the format
     *                                        'hh:mm'
     * @throws NumberFormatException          if hours or/and minutes isn't numeric
     * @throws DateTimeException              if hours or/and minutes has illegal
     *                                        values
     * @throws IllegalArgumentException       If
     */
    protected void valDuration(String duration, IWorkout workout)
            throws NullPointerException, ArrayIndexOutOfBoundsException, NumberFormatException, DateTimeException,
            IllegalArgumentException {

        duration = duration.strip();
        if (duration.length() > 5)
            throw new NumberFormatException("Invalid format on duration, must be on the format hh:mm");

        try {
            String[] values = duration.split(":");

            int hours = Integer.valueOf(values[0]);
            int minutes = Integer.valueOf(values[1]);

            LocalTime t = LocalTime.of(hours, minutes);
            hours = t.getHour() * 60;
            minutes = t.getMinute();

            workout.setDuration(hours + minutes);

        } catch (NullPointerException e) {
            throw new NullPointerException("Invalid duration, duration cannot be null");
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new ArrayIndexOutOfBoundsException("Invalid duration, must be on the format 'hh:mm'\n");
        } catch (NumberFormatException e) {
            throw new NumberFormatException("Invalid duration, must contains numbers on the format hh:mm\n");
        } catch (DateTimeException e) {
            throw new DateTimeException("Invalid duration, invalid values for hours and/or minutes\n");
        }
    }

    /**
     * Takes in choose of rating and sets rating if it's a legal value.
     * 
     * @param rating  String with chosen value
     * @param workout IWorkout that gets rating set
     * @return IWorkout with valid rating
     * @throws IllegalArgumentException If chosen value is not in the interval 1-6.
     */
    protected void valRating(String rating, IWorkout workout) throws IllegalArgumentException {
        char ratingVal = rating.charAt(0);
        workout.setRating(ratingVal);
    }

    protected void valComment(String comment, IWorkout workout) {
        workout.setComment(comment);
    }

    /**
     * Validates timevalue and returns a LocalTime value with valid value.
     * 
     * @param time String with timevalue
     * @return LocalTime value of the time when workout started
     * @throws IllegalArgumentException       If timevalue is not a string
     * @throws ArrayIndexOutOfBoundsException If format on time is illegal and
     *                                        values cannot be found
     * @throws DateTimeException              If the value of minutes or hours is
     *                                        out of valid range
     */
    private LocalTime valTime(String time)
            throws IllegalArgumentException, ArrayIndexOutOfBoundsException, DateTimeException {
        String error = "Invalid date, cannot set date with illegal time\n";

        time = time.strip();
        if (time.length() > 5)
            throw new IllegalArgumentException("Invalid format on duration, must be on the format hh:mm");

        try {
            String[] timeValues = time.split(":");

            int hours = Integer.valueOf(timeValues[0]);
            int minutes = Integer.valueOf(timeValues[1]);

            LocalTime localTime = LocalTime.of(hours, minutes);

            return localTime;
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(error + "Invalid time, must be on the format 'hh:mm'\n");
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new ArrayIndexOutOfBoundsException(error + "Invalid time, must be on the format 'hh:mm'\n");
        } catch (DateTimeException e) {
            throw new DateTimeException(error + "Invalid time, invalid values for hours and/or minutes\n");
        }
    }

}
