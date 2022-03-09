package trainingDiary;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Strength implements Workout {

    private LocalDateTime date;
    private int duration;
    private char rating;
    private String content;
    private List<Exercise> exercises = new ArrayList<>();

    /**
     * Konstrukøtr som innitialiserer en ny økt, med data tilknyttet økten.
     * 
     * @param date     Date til øktens dato
     * @param duration Int varighet på økten i minutter
     * @param rating   char terningkastverdi på økten
     * @param content  String kommentarer til økten
     */
    public Strength(LocalDateTime date, int duration, char rating, String content) {
        WorkoutValidate validator = new WorkoutValidate();

        validator.ValidateDate(date);
        validator.validateDuration(duration);
        validator.validateRating(rating);

        this.date = date;
        this.duration = duration;
        this.rating = rating;
        this.content = content;
    }

    /**
     * Legger til en øvelse i listen over øvelser til økten. Tar inn navn, antall
     * sett og repetisjoner
     * 
     * @param name String navn på øvelse
     * @param rep  Int[] med de ulike tallverdiene over reppetisjoner
     */
    void addExercise(String name, int[] rep) {
        Exercise newEx = new Exercise(name);

        for (int i = 0; i < rep.length; i++) {
            newEx.addRep(rep[i]);
        }
        exercises.add(newEx);
    }

    @Override
    public LocalDateTime getDate() {
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

    public List<Exercise> getExercises() {
        return exercises;
    }

    @Override
    public String toString() {
        String res = "Strength: Date: " + date + ", " + duration + " min, | Rating: " + rating + " | Content: "
                + content;
        for (Exercise exercise : exercises) {
            res += "\n" + exercise;
        }
        return res;
    }
}
