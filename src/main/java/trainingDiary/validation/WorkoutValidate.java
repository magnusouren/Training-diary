package trainingDiary.validation;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public class WorkoutValidate {

    /**
     * Checks if date is in the past
     * 
     * @param date LocalDateTime for the exercise.
     * @throws IllegalArgumentException If date is in the future
     */
    public static void validateDate(LocalDateTime date) throws IllegalArgumentException {

        LocalDateTime today = LocalDateTime.now();

        if (date.isAfter(today)) {
            throw new IllegalArgumentException("Invalid date, date can not be in the future\n");
        }

    }

    /**
     * Validates if the duration of the workout is legal.
     * Must be greater than 0:00 and less or equal than 5:00.
     * 
     * @param duration int duration of workout
     * @throws IllegalArgumentException if duration is not in the interval
     *                                  [0:01-5:00]
     */
    public static void validateDuration(int duration) throws IllegalArgumentException {
        if (duration <= 0)
            throw new IllegalArgumentException("Invalid duration, must be greater than 0:00\n");
        if (duration > 300)
            throw new IllegalArgumentException("Invalid duration, must be less than 5:00\n");
    }

    /**
     * Validates if rating has a valid value.
     * Must be in the interval [1-6].
     * 
     * @param rating char rating of the exercise
     * @throws IllegalArgumentException If ranking is not in the interval [1-6]
     */
    public static void validateRating(char rating) throws IllegalArgumentException {
        Collection<Character> values = new ArrayList<>();
        values.addAll(Arrays.asList('1', '2', '3', '4', '5', '6'));

        if (!values.contains(rating))
            throw new IllegalArgumentException("Invalid rating, pick a number in the interval [1-6]\n");
    }
}
