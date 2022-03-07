package trainingDiary;

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
        this.date = date;
        validateDuration(duration);
        this.duration = duration;
        validateKm(km);
        this.km = km;
        this.rating = rating;
        this.content = content;
        this.avaerageHeartRate = averageHeartRate;
        this.maxHeartRate = maxHeartRate;
        setAverageSpeed(duration, km);
    }

    private void setAverageSpeed(int duration, int km) {
        this.averageSpeed = duration / km;
    }

    private void validateKm(int km) {
        if (km < 0)
            throw new IllegalArgumentException("Km must be grater than 0");
        if (km > 100)
            throw new IllegalArgumentException("Km must be less than 100km");
    }

    private void validateDuration(int duration) {
        if (duration < 0)
            throw new IllegalArgumentException("Duration should be greater than 0");
        if (duration > 300)
            throw new IllegalArgumentException("A workout cant be longer than 5 hours");
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
