package trainingDiary;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import trainingDiary.validation.WorkoutValidate;

public class Strength implements IWorkout {

    private LocalDateTime dateTime;
    private int duration;
    private char rating;
    private String comments;
    private List<Exercise> exercises = new ArrayList<>();

    public Strength() {
    }

    /**
     * Constructor
     * 
     * @param dateTime LocalDateTime of Strength
     * @param duration int duration of Strength in minutes
     * @param rating   char rating of Strength
     * @param comment  String comment to Strength
     */
    public Strength(LocalDateTime dateTime, int duration, char rating, String comment) {
        setDate(dateTime);
        setDuration(duration);
        setRating(rating);
        setComment(comment);
    }

    @Override
    public void setDate(LocalDateTime date) throws IllegalArgumentException {
        WorkoutValidate.validateDate(date);
        this.dateTime = date;
    }

    @Override
    public void setDuration(int duration) throws IllegalArgumentException {
        WorkoutValidate.validateDuration(duration);
        this.duration = duration;
    }

    @Override
    public void setRating(char rating) throws IllegalArgumentException {
        WorkoutValidate.validateRating(rating);
        this.rating = rating;
    }

    @Override
    public void setComment(String comment) {
        this.comments = comment;
    }

    /**
     * Adds exercise to list of exercises
     * 
     * @param exercise Exercise
     * @throws IllegalArgumentException if exercise already exists in list
     * @throws NullPointerException     if exercise is null
     */
    public void addExercise(Exercise exercise) throws IllegalArgumentException, NullPointerException {
        if (!Objects.isNull(exercise)) {
            if (!exercises.contains(exercise))
                exercises.add(exercise);
            else
                throw new IllegalArgumentException("Exercise exists");
        } else
            throw new NullPointerException("Exercise cannot be null");

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
    public String getComment() {
        return comments;
    }

    /**
     * @return New List of exercises to workout
     */
    public List<Exercise> getExercises() {
        return new ArrayList<>(exercises);
    }

    @Override
    public String toString() {
        String res = String.format("""

                \t%25s
                \t________________________________________

                \tDate:%35s
                \tTime:%35s
                \tDuration:%27d min
                \tRating:%33c
                \t________________________________________

                \tExercises:
                """,
                this.getClass().getSimpleName(),
                getDate().format(DateTimeFormatter.ofPattern("dd.MM")),
                getDate().format(DateTimeFormatter.ofPattern("HH:mm")),
                getDuration(),
                getRating());

        res += "\t" + exercises.stream()
                .map(exercise -> exercise.toString())
                .collect(Collectors.joining("\n\t"));

        res += String.format("""

                \t________________________________________

                \tComments:
                \t%s
                """,
                getComment());

        return res;
    }

}
