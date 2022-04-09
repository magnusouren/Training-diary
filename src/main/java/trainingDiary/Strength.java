package trainingDiary;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import trainingDiary.validation.WorkoutValidate;

public class Strength implements IWorkout {

    private LocalDateTime dateTime;
    private int duration;
    private char rating;
    private String comments;
    private List<Exercise> exercises = new ArrayList<>();
    private WorkoutValidate validator = new WorkoutValidate();

    public Strength() {
    }

    /**
     * Konstrukøtr som innitialiserer en ny økt, med data tilknyttet økten.
     * 
     * @param dateTime Date til øktens dato
     * @param duration Int varighet på økten i minutter
     * @param rating   char terningkastverdi på økten
     * @param content  String kommentarer til økten
     */
    public Strength(LocalDateTime dateTime, int duration, char rating, String content) {
        WorkoutValidate validator = new WorkoutValidate();

        validator.ValidateDate(dateTime);
        validator.validateDuration(duration);
        validator.validateRating(rating);

        this.dateTime = dateTime;
        this.duration = duration;
        this.rating = rating;
        this.comments = content;
    }

    /**
     * Legger til en øvelse i listen over øvelser til økten. Tar inn navn og antall
     * repetisjoner
     * 
     * @param name String navn på øvelse
     * @param rep  Int[] med de ulike tallverdiene over reppetisjoner
     */
    public void addExercise(Exercise exercise) {
        exercises.add(exercise);
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

    public void setDate(LocalDateTime date) {
        validator.ValidateDate(date);
        this.dateTime = date;
    }

    public void setDuration(int duration) {
        validator.validateDuration(duration);
        this.duration = duration;
    }

    public void setRating(char rating) {
        validator.validateRating(rating);
        this.rating = rating;
    }

    public void setContent(String content) {
        this.comments = content;
    }

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
                getContent());

        return res;
    }

}
