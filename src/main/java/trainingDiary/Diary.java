package trainingDiary;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Diary {

    private List<Workout> diary = new ArrayList<>();

    void addWorkout(Workout workout) {
        if (!diary.contains(workout))
            diary.add(workout);
        else
            throw new IllegalArgumentException("Workout already exists");
    }

    void removeWorkout(Workout workout) {
        if (diary.contains(workout))
            diary.remove(workout);
        else
            throw new IllegalArgumentException("Workout doesn't exist in diary");
    }

    /**
     * Legger til en øvelse til økten hvis økten er av type Strength. Utløser unntak
     * hvis ikke.
     * 
     * @param strength Workout som skal få øvelse lagt til
     * @param name     String navn på type øvelse
     * @param reps     int[] tallrepresentasjon av antall repetisjoner
     */
    void addExercise(Workout strength, String name, Integer... reps) {
        if (strength instanceof Strength) {
            Strength castStrength = (Strength) strength;
            castStrength.addExercise(name, reps);
        } else {
            throw new IllegalArgumentException("Workout is not a strength-workout, couldn't add exercise");
        }
    }

    public List<Workout> getDiary() {
        return new ArrayList<>(diary);
    }

    @Override
    public String toString() {
        return diary.stream()
                .map(diary -> diary.toString())
                .collect(Collectors.joining("\n"));
    }

    public static void main(String[] args) {
        Diary diary = new Diary();

        Workout strength = new Strength(LocalDateTime.now(), 60, '5', "Bra");
        Workout run = new Run(LocalDateTime.now(), 60, 50000, '5', "Løp", 150, 200);

        diary.addWorkout(strength);
        diary.addWorkout(run);

        diary.addExercise(strength, "benk", 5, 5, 5);
        diary.addExercise(strength, "knebøy", 5, 6, 5);

        System.out.println(diary);

    }
}
