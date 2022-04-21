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
     * Konstruktør til å sette tilstand til Run-objektet. Tar inn parameterverdier,
     * validerer og setter disse.
     * 
     * @param dateTime         LocalDateTime tilhørende datoen til økten
     * @param duration         Int varighet på økt
     * @param distance         Int lengde på løpetur i kilometer
     * @param rating           Char tallverdi for karrakter på økten
     * @param content          String tekstinnhold til økten
     * @param averageHeartRate Int gjennomsnitt puls
     * @param maxHeartRate     Int makspuls
     */
    public Run(LocalDateTime dateTime, int duration, int distance, char rating, String content, int averageHeartRate,
            int maxHeartRate) {

        setDate(dateTime);
        setDuration(duration);
        setDistance(distance);
        setRating(rating);
        setContent(content);
        setMaxHeartRate(maxHeartRate);
        setAvaerageHeartRate(averageHeartRate);
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

        if (distance <= 0)
            throw new IllegalArgumentException("Invalid distance, must be grater than 0\n");
        if (distance > 100000)
            throw new IllegalArgumentException("Invalid distance, cannot be greater than 100km\n");
    }

    private void validateContent(String content) {
        // TO-DO
    }

    @Override
    public void setDate(LocalDateTime date) {
        validator.ValidateDate(date);
        this.dateTime = date;
    }

    @Override
    public void setContent(String content) {
        validateContent(content);
        this.comments = content;
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

    public void setDistance(int distance) {
        validateDistance(distance);
        this.distance = distance;
    }

    public void setAvaerageHeartRate(int avaerageHeartRate) {
        validateHeartRate(avaerageHeartRate);
        if (avaerageHeartRate > maxHeartRate)
            throw new IllegalArgumentException("Inavlid average heartrate, cannot be greater than maximum heartrate");
        this.averageHeartRate = avaerageHeartRate;
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
        if (heartRate <= 0)
            throw new IllegalArgumentException("heartrate should be grater than 0\n");
        if (heartRate > 225)
            throw new IllegalArgumentException("heartrate cannot be grater than 225\n");
    }

    /**
     * Beregner gjennomsnittsfart ut ifra lengden og varighet.
     * 
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

    public static void main(String[] args) {
        IWorkout run1 = new Run(LocalDateTime.of(2022, 03, 1, 12, 00), 30, 5000, '5', "Løp 1", 150, 200);
        System.out.println(run1);
    }

}
