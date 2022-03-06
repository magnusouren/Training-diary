package trainingDiary;

import java.util.Date;

public class Run implements Workout {

    Date date;
    int duration;
    char rating;
    String content;

    void Run() {

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

}
