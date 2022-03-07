package trainingDiary;

import java.util.Date;

public interface Workout {

    public Date getDate();

    public int getDuration();

    public char getRating();

    public String getContent();

    void validateRating(char rating) {
        Collection<Character> values = new ArrayList<>();
        values.addAll(Arrays.asList('1', '2', '3', '4', '5', '6'));

        if (!values.contains(rating))
            throw new IllegalArgumentException("Illegal rating, must be from 1-6");
    }

    void validateDuration(int duration) {
        if (duration < 0)
            throw new IllegalArgumentException("Duration should be greater than 0");
        if (duration > 300)
            throw new IllegalArgumentException("A workout cant be longer than 5 hours");
    }

}
