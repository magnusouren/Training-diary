package trainingDiary;

import java.time.LocalDateTime;

public interface IWorkout {

    public LocalDateTime getDate();

    public int getDuration();

    public String getContent();

    public char getRating();

    /**
     * Validates and sets date. If datetime is in the future, exception is thrown
     * 
     * @param date LocalDateTime of workout
     * @throws IllegalArgumentException If date is in the future
     */
    public void setDate(LocalDateTime date) throws IllegalArgumentException;

    /**
     * Sets duration of workout. If duration is less or equal than 0 or greater
     * than 300, exception is thrown
     * 
     * @param duration int duration in minutes
     * @throws IllegalArgumentException if duration is not in the
     *                                  interval
     *                                  [0:01-5:00]
     */
    public void setDuration(int duration) throws IllegalArgumentException;

    /**
     * Sets comment to workout
     * 
     * @param comment String comment
     */
    public void setComment(String comment);

    /**
     * Validates if rating is a number from 1 to 6, throws exception if not
     * 
     * @param rating char rating of workout
     * @throws IllegalArgumentException If ranking is not in the interval [1-6]
     */
    public void setRating(char rating) throws IllegalArgumentException;

}
