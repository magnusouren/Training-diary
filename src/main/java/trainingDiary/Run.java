package trainingDiary;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import trainingDiary.validation.WorkoutValidate;

public class Run implements IWorkout {

    private LocalDateTime dateTime;
    private String comments;
    private char rating;
    private int duration, distance, averageHeartRate, maxHeartRate;
    private double averageSpeed;

    private WorkoutValidate validator = new WorkoutValidate();

    /**
     * Consturctor that calls methods that sets fields
     * 
     * @param dateTime         LocalDateTime of Run
     * @param duration         int duration of Run in minutes
     * @param distance         int distance of Run in meters
     * @param rating           char rating of Run
     * @param comment          String comments to Run
     * @param averageHeartRate int Average heartrate of Run (BPM)
     * @param maxHeartRate     int Maximum heartrate of run (BPM)
     */
    public Run(LocalDateTime dateTime, int duration, int distance, char rating, String comment, int averageHeartRate,
            int maxHeartRate) {

        setDate(dateTime);
        setDuration(duration);
        setDistance(distance);
        setRating(rating);
        setComment(comment);
        setMaxHeartRate(maxHeartRate);
        setAvaerageHeartRate(averageHeartRate);
        setAverageSpeed();
    }

    public Run() {
    }

    @Override
    public void setDate(LocalDateTime date) {
        validator.ValidateDate(date);
        this.dateTime = date;
    }

    @Override
    public void setComment(String comment) {
        this.comments = comment;
    }

    @Override
    public void setRating(char rating) {
        validator.validateRating(rating);
        this.rating = rating;
    }

    @Override
    public void setDuration(int duration) {
        validator.validateDuration(duration);
        this.duration = duration;
    }

    /**
     * Sets distance of run if distance has valid value
     * 
     * @param distance
     * @throws IllegalArgumentException if distance is greater than 100000 or less
     *                                  than 0
     */
    public void setDistance(int distance) throws IllegalArgumentException {
        validateDistance(distance);
        this.distance = distance;
    }

    /**
     * Validates distance, throws exception if distance is less or equal or less
     * than 0 or greater than 100000
     * 
     * @param distance int distance of run i meters
     * @throws IllegalArgumentException if distance is greater than 100000 or less
     *                                  than 0
     */
    private void validateDistance(int distance) throws IllegalArgumentException {

        if (distance <= 0)
            throw new IllegalArgumentException("Invalid distance, must be grater than 0\n");
        if (distance > 100000)
            throw new IllegalArgumentException("Invalid distance, cannot be greater than 100km\n");
    }

    /**
     * Sets average heartrate if value is valid
     * 
     * @param avaerageHeartRate int average heartrate (BPM)
     * @throws IllegalArgumentException If avgHr is not in the interval [40,125] or
     *                                  greater than maxHr
     * 
     */
    public void setAvaerageHeartRate(int avaerageHeartRate) throws IllegalArgumentException {
        validateHeartRate(avaerageHeartRate);
        if (avaerageHeartRate > maxHeartRate)
            throw new IllegalArgumentException("cannot be greater than maximum heartrate");
        this.averageHeartRate = avaerageHeartRate;
    }

    /**
     * Sets maximum heartrate if value is valid
     * 
     * @param maxHeartRate int maximum heartrate (BPM)
     * @throws IllegalArgumentException If avgHr is not in the interval [40,125] or
     *                                  greater than maxHr
     */
    public void setMaxHeartRate(int maxHeartRate) throws IllegalArgumentException {
        validateHeartRate(maxHeartRate);
        this.maxHeartRate = maxHeartRate;
    }

    /**
     * Validates heartrate, must be greater than 39 and less than 226
     * 
     * @param heartRate int heartrate (BPM)
     * @throws IllegalArgumentException if heartrate is not in the interval [40,125]
     */
    private void validateHeartRate(int heartRate) throws IllegalArgumentException {
        if (heartRate < 40)
            throw new IllegalArgumentException("heartrate should be grater or equal than 40\n");
        if (heartRate > 225)
            throw new IllegalArgumentException("heartrate cannot be grater than 225\n");
    }

    /**
     * Calculates averagespeed of run
     */
    public void setAverageSpeed() {
        this.averageSpeed = (((double) this.distance / 1000) / ((double) this.duration / 60));
    }

    @Override
    public LocalDateTime getDate() {
        return dateTime;
    }

    @Override
    public int getDuration() {
        return duration;
    }

    @Override
    public char getRating() {
        return rating;
    }

    @Override
    public String getContent() {
        return comments;
    }

    public int getDistance() {
        return distance;
    }

    public int getAvaerageHeartRate() {
        return averageHeartRate;
    }

    public double getAverageSpeed() {
        return averageSpeed;
    }

    public int getMaxHeartRate() {
        return maxHeartRate;
    }

    @Override
    public String toString() {
        String res = String.format("""
                \n
                \t%22s
                \t________________________________________

                \tDate:%35s
                \tTime:%35s
                \tDuration:%27d min
                \tRating:%33c
                \t________________________________________

                \tDistance(meter):%24d
                \tAvereage speed: %24.2f
                \tMaximum heartrate: %21d
                \tAverage heartrate: %21d
                \t________________________________________

                \tComments:
                \t%s


                """,
                this.getClass().getSimpleName(),
                getDate().format(DateTimeFormatter.ofPattern("dd.MM")),
                getDate().format(DateTimeFormatter.ofPattern("HH:mm")),
                getDuration(),
                getRating(),
                getDistance(),
                getAverageSpeed(),
                getMaxHeartRate(),
                getAvaerageHeartRate(),
                getContent());
        return res;
    }

}
