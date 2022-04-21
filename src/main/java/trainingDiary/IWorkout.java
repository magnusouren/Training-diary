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
     */
    public void setDate(LocalDateTime date);

    /**
     * Sets duration of workout. If duration is less or equal than 0 or greater
     * than 300, exception is thrown
     * 
     * @param duration int duration in minutes
     */
    public void setDuration(int duration);

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
     */
    public void setRating(char rating);

}
