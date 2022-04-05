package trainingDiary;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Run implements Workout {

    private LocalDateTime date;
    private String content;
    private char rating;
    private int duration;

    private int distance;
    private int avaerageHeartRate;
    private int maxHeartRate;
    private double averageSpeed;

    private WorkoutValidate validator = new WorkoutValidate();

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

        setDate(date);
        setDuration(duration);
        setDistance(distance);
        setRating(rating);
        setContent(content);
        setAvaerageHeartRate(averageHeartRate);
        setMaxHeartRate(maxHeartRate);
        setAverageSpeed();
    }

    public Run() {
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
            throw new IllegalArgumentException("Distance cannot be greater than 100km");
    }

    private void validateContent(String content) {
        // TO-DO
    }

    public void setDate(LocalDateTime date) {
        validator.ValidateDate(date);
        this.date = date;
    }

    public void setContent(String content) {
        validateContent(content);
        this.content = content;
    }

    public void setRating(char rating) {
        validator.validateRating(rating);
        this.rating = rating;
    }

    public void setDuration(int duration) {
        validator.validateDuration(duration);
        this.duration = duration;
    }

    public void setDistance(int distance) {
        validateDistance(distance);
        this.distance = distance;
    }

    public void setAvaerageHeartRate(int avaerageHeartRate) {
        validateHeartRate(avaerageHeartRate);
        this.avaerageHeartRate = avaerageHeartRate;
    }

    public void setMaxHeartRate(int maxHeartRate) {
        validateHeartRate(maxHeartRate);
        this.maxHeartRate = maxHeartRate;
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
     */
    public void setAverageSpeed() {
        this.averageSpeed = (this.distance / 1000) / (this.duration / 60);
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
        System.out.println(averageSpeed);
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

    public static void main(String[] args) {
        Workout run1 = new Run(LocalDateTime.of(2022, 03, 1, 12, 00), 30, 5000, '5', "Løp 1", 150, 200);
        System.out.println(run1);
    }

}
