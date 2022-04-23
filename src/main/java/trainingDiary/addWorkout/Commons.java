package trainingDiary.addWorkout;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.regex.PatternSyntaxException;

import trainingDiary.IWorkout;

public class Commons {

    /**
     * Validates date and time-values validates, styles the fields relative to its
     * validity. Sets LocalDateTime to workout if values are valid
     * 
     * @param date    DatePicker with date-value
     * @param time    TextField with time-value
     * @param workout IWorkout to get date set
     * @return IWorkout with date sat
     * @throws IllegalArgumentException       If date i sin the future
     * @throws PatternSyntaxException         If time is on wrong format
     * @throws ArrayIndexOutOfBoundsException If time is on the wrong format
     * @throws DateTimeException              If time contains invalid timevalues
     * @throws NullPointerException           If date is sat with ilegal time
     */
    protected void valDate(LocalDate date, String time, IWorkout workout)
            throws IllegalArgumentException, ArrayIndexOutOfBoundsException, DateTimeException {
        LocalDateTime dateTime = LocalDateTime.of(date, valTime(time));
        workout.setDate(dateTime);

    }

    /**
     * Validates duration and styles inputfield relative to its validity.
     * Sets duration if values are valid.
     * 
     * @param duration TextField with timevalue
     * @param workout  IWorkout that gets duration set
     * @return IWorkout with valid duration
     */
    protected void valDuration(String duration, IWorkout workout) {

        try {
            String[] values = duration.split(":");

            int hours = Integer.valueOf(values[0]);
            int minutes = Integer.valueOf(values[1]);

            LocalTime t = LocalTime.of(hours, minutes);
            hours = t.getHour() * 60;
            minutes = t.getMinute();

            workout.setDuration(hours + minutes);

        } catch (PatternSyntaxException e) {
            throw new PatternSyntaxException("Invalid duration, must be on the format 'hh:mm'\n",
                    "(0?[0-9]|[1-5][0-9]):(0?[0-9]|[1-5][0-9])", -1);
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
     * @param rating  ChoiceBox<String> with chosen value
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
     * @param time TextField with timevalue
     * @return LocalTime value of the time when workout started
     * @throws IllegalArgumentException       If timevalue is not a string
     * @throws ArrayIndexOutOfBoundsException If format on time is illegal and
     *                                        values cannot be found
     * @throws DateTimeException              If the value of minutes or hours is
     *                                        out of valid range
     */
    private LocalTime valTime(String time) {
        String error = "Invalid date, cannot set date with illegal time\n";
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
