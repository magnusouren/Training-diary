package trainingDiary;

import java.time.LocalDateTime;

public class Run implements Workout {

    private LocalDateTime date;
    private String content;
    private char rating;
    private int duration;

    private int distance;
    private int avaerageHeartRate;
    private int maxHeartRate;
    private double averageSpeed;

    /**
     * Konstruktør til å sette tilstand til Run-objektet. Tar inn parameterverdier,
     * validerer og setter disse.
     * 
     * @param date             LocalDateTime tilhørende datoen til økten
     * @param duration         Int varighet på økt
     * @param distance         Int lengde på løpetur i kilometer
     * @param rating           Char tallverdi for karrakter på økten
     * @param content          String tekstinnhold til økten
     * @param averageHeartRate Int gjennomsnitt puls
     * @param maxHeartRate     Int makspuls
     */
    public Run(LocalDateTime date, int duration, int distance, char rating, String content, int averageHeartRate,
            int maxHeartRate) {

        WorkoutValidate validator = new WorkoutValidate();

        validator.ValidateDate(date);
        validator.validateDuration(duration);
        validator.validateRating(rating);

        validateDistance(distance);
        validateContent(content);
        validateHeartRate(averageHeartRate);
        validateHeartRate(maxHeartRate);

        this.date = date;
        this.duration = duration;
        this.distance = distance;
        this.content = content;
        this.avaerageHeartRate = averageHeartRate;
        this.maxHeartRate = maxHeartRate;
        this.rating = rating;

        setAverageSpeed(duration, distance);

    }

    /**
     * Validerer om lengden på økten i km er gyldig. Utløser unntak hvis ikke.
     * 
     * @param distance Int tallverdi som representerer lengden på løpetur.
     */
    private void validateDistance(int distance) {
        if (distance < 0)
            throw new IllegalArgumentException("Km must be grater than 0");
        if (distance > 100000)
            throw new IllegalArgumentException("Distance must be less than 100km");
    }

    private void validateContent(String content) {
        // TO-DO
    }

    /**
     * Valideringsmetode som tar inn en pulsverdi og validerer denne. Utløser unntak
     * hvis ikke.
     * 
     * @param heartRate Int pulsverdi
     */
    private void validateHeartRate(int heartRate) {
        if (heartRate < 0)
            throw new IllegalArgumentException("Heartrate sholud be grater than 0");
        if (heartRate > 225)
            throw new IllegalArgumentException("Heartrate cannot be grater than 225");
    }

    /**
     * Beregner gjennomsnittsfart ut ifra lengden og varighet.
     * 
     * @param duration int minutter varighet
     * @param distance int distanse i km
     */
    private void setAverageSpeed(double duration, double km) {
        this.averageSpeed = (km / 1000) / (duration / 60);
    }

    @Override
    public LocalDateTime getDate() {
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

    public int getDistance() {
        return distance;
    }

    public int getAvaerageHeartRate() {
        return avaerageHeartRate;
    }

    public double getAverageSpeed() {
        return averageSpeed;
    }

    public int getMaxHeartRate() {
        return maxHeartRate;
    }

    @Override
    public String toString() {
        return "Run: Date: " + date + ", " + distance + "m, " + duration + " min, | Rating: " + rating + " | Content: "
                + content + " | Max HR: " + maxHeartRate + " Av. HR: " + avaerageHeartRate + " | Av. speed: "
                + averageSpeed;
    }

}
