package trainingDiary;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

public class Run implements Workout {

    private Date date;
    private int duration;
    private int km;
    private char rating;
    private String content;
    private int avaerageHeartRate;
    private int maxHeartRate;
    private int averageSpeed;

    public Run(Date date, int duration, int km, char rating, String content, int averageHeartRate, int maxHeartRate) {

        validateDuration(duration);
        validateKm(km);
        validateRating(rating);
        validateContent(content);
        validateHeartRate(averageHeartRate);
        validateHeartRate(maxHeartRate);

        this.date = date;
        this.duration = duration;
        this.km = km;
        this.rating = rating;
        this.content = content;
        this.avaerageHeartRate = averageHeartRate;
        this.maxHeartRate = maxHeartRate;

        setAverageSpeed(duration, km);

    }

    private void validateContent(String content) {
    }

    private void validateHeartRate(int heartRate) {
        if (heartRate < 0)
            throw new IllegalArgumentException("Heartrate sholud be grater than 0");
        if (heartRate < 225)
            throw new IllegalArgumentException("Heartrate cannot be grater than 225");
    }

    private void validateRating(char rating) {
        Collection<Character> values = new ArrayList<>();
        values.addAll(Arrays.asList('1', '2', '3', '4', '5', '6'));

        if (!values.contains(rating))
            throw new IllegalArgumentException("Illegal rating, must be from 1-6");
    }

    private void validateDuration(int duration) {
        if (duration < 0)
            throw new IllegalArgumentException("Duration should be greater than 0");
        if (duration > 300)
            throw new IllegalArgumentException("A workout cant be longer than 5 hours");
    }

    private void validateKm(int km) {
        if (km < 0)
            throw new IllegalArgumentException("Km must be grater than 0");
        if (km > 100)
            throw new IllegalArgumentException("Km must be less than 100km");
    }

    private void setAverageSpeed(int duration, int km) {
        this.averageSpeed = duration / km;
    }

    @Override
    public Date getDate() {
        return date;
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
        return content;
    }

    @Override
    public String toString() {
        return "Run: Date: " + date + ", " + km + "km, " + duration + " min, | Rating: " + rating + " | Content: "
                + content + " | Max HR: " + maxHeartRate + " Av. HR: " + avaerageHeartRate + " | Av. speed: "
                + averageSpeed;
    }

    public static void main(String[] args) {
        Date now = new Date();
        Run test = new Run(now, 60, 10, '4', "Test", 150, 180);
        System.out.println(test);
    }
}
