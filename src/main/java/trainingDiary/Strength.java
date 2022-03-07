package trainingDiary;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Strength implements Workout {

    private Date date;
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
    public Strength(Date date, int duration, char rating, String content) {
        this.date = date;
        this.duration = duration;
        this.rating = rating;
        this.content = content;
    }

    /**
     * Legger til en øvelse i listen over øvelser til økten. Tar inn navn antall
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
        String res = "Strength: Date: " + date + ", " + duration + " min, | Rating: " + rating + " | Content: "
                + content + "\n";
        for (Exercise exercise : exercises) {
            res += exercise.getName() + ": " + exercise.getSets() + ": ";
            List<Integer> reps = exercise.getReps();
            for (Integer integer : reps) {
                res += integer + ", ";
            }
            res += "\n";
        }
        return res;
    }

    public static void main(String[] args) {
        Date now = new Date();
        Strength test = new Strength(now, 50, '3', "Braaa");
        int[] rep = { 4, 5, 6 };
        test.addExercise("benk", rep);
        System.out.println(test);
    }
}
