package trainingDiary;

import java.time.LocalDateTime;

public interface Workout {

    public LocalDateTime getDate();

    public int getDuration();

    public String getContent();

    public char getRating();

}
