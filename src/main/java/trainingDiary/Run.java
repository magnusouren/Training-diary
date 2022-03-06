package trainingDiary;

import java.util.Date;

public class Run implements Workout {

    Date date;
    int duration;
    int km;
    char rating;
    String content;
    int avaerageHeartRate;
    int maxHeartRate;
    int averageSpeed;

    public Run(Date date, int duration, int km, char rating, String content, int averageHeartRate, int maxHeartRate) {
        this.date = date;
        this.duration = duration;
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
                + content + " | MaxHR: " + maxHeartRate + " AvrgHR: " + avaerageHeartRate;
    }

    public static void main(String[] args) {
        Date now = new Date();
        Run test = new Run(now, 60, 10, '4', "Test", 150, 180);
        System.out.println(test);
    }
}
