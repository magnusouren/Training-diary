package trainingDiary;

import java.util.Date;

public class Run implements Workout {

    Date date;
    int duration;
    int km;
    char rating;
    String content;
    int avaerageHeartRate;
    int averageSpeed;
    int maxHeartRate;

    public Run(Date date, int duration, int km, char rating, String content) {
        this.date = date;
        this.duration = duration;
        this.rating = rating;
        this.content = content;
        this.km = km;
        setAverageSpeed(duration, km);
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
                + content;
    }

    public static void main(String[] args) {
        Date now = new Date();
        Run test = new Run(now, 60, 10, '4', "Test");
        System.out.println(test);
    }
}
