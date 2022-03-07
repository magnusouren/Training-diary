package trainingDiary;

import java.util.Date;

public class Strength implements Workout {

    Date date;
    int duration;
    char rating;
    String content;

    public Strength(Date date, int duration, char rating, String content) {
        this.date = date;
        this.duration = duration;
        this.rating = rating;
        this.content = content;
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
        return "Strength: Date: " + date + ", " + duration + " min, | Rating: " + rating + " | Content: "
                + content;
    }

    public static void main(String[] args) {
        Date now = new Date();
        Strength test = new Strength(now, 50, '3', "Braaa");
        System.out.println(test);
    }
}
