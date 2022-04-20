package trainingDiary;

import java.time.LocalDateTime;

public interface IWorkout {

    public LocalDateTime getDate();

    public int getDuration();

    public String getContent();

    public char getRating();

    public void setDate(LocalDateTime date);

    public void setDuration(int duration);

    public void setContent(String comment);

    public void setRating(char rating);

}
