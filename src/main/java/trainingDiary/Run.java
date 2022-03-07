package trainingDiary;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

public class Run implements Workout {

    private Date date;
    private String content;
    private char rating;
    private int duration;

    private int distance;
    private int avaerageHeartRate;
    private int maxHeartRate;
    private int averageSpeed;

    /**
     * Konstruktør til å sette tilstand til Run-objektet. Tar inn parameterverdier,
     * validerer og setter disse.
     * 
     * @param date             Date tilhørende datoen til økten
     * @param duration         Int varighet på økt
     * @param distance         Int lengde på løpetur i kilometer
     * @param rating           Char tallverdi for karrakter på økten
     * @param content          String tekstinnhold til økten
     * @param averageHeartRate Int gjennomsnitt puls
     * @param maxHeartRate     Int makspuls
     */
    public Run(Date date, int duration, int distance, char rating, String content, int averageHeartRate,
            int maxHeartRate) {

        validateDuration(duration);
        validateDistance(distance);
        validateContent(content);
        validateHeartRate(averageHeartRate);
        validateHeartRate(maxHeartRate);
        validateRating(rating);

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
     * Valideringsmetode som sjekker om varighet på økten er gyldig. Satt makstid er
     * bestemt til 5 timer. Utløser unntak hvis ugyldig.
     * 
     * @param duration int varighet i minutter på økt.
     */
    private void validateDuration(int duration) {
        if (duration < 0)
            throw new IllegalArgumentException("Duration must be greater than 0");
        if (duration > 300)
            throw new IllegalArgumentException("A workout canot be longer than 5 hours");
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
     * Tar inn en rating og validerer om denne er i en liste med gyldige verdier.
     * Utløser unntak hvis ikke
     * 
     * @param rating char rating som skal være tallverdi fra 1-6
     */
    private void validateRating(char rating) {
        Collection<Character> values = new ArrayList<>();
        values.addAll(Arrays.asList('1', '2', '3', '4', '5', '6'));

        if (!values.contains(rating))
            throw new IllegalArgumentException("Illegal rating, must be in the interval 1-6");
    }

    /**
     * Beregner gjennomsnittsfart ut ifra lengden og varighet.
     * 
     * @param duration int minutter varighet
     * @param distance int distanse i km
     */
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
        return "Run: Date: " + date + ", " + distance + "m, " + duration + " min, | Rating: " + rating + " | Content: "
                + content + " | Max HR: " + maxHeartRate + " Av. HR: " + avaerageHeartRate + " | Av. speed: "
                + averageSpeed;
    }

    public static void main(String[] args) {
        Date now = new Date();
        Run test = new Run(now, 60, 10, '4', "Test", 150, 180);
        System.out.println(test);
    }
}
