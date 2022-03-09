package trainingDiary;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Diary {

    private List<Workout> diary = new ArrayList<>();

    void addWorkout(Workout workout) {
        if (!diary.contains(workout))
            diary.add(workout);
        else
            throw new IllegalArgumentException("Workout already exists");
    }

    void removeWorkout(Workout workout) {
        diary.remove(workout);
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
        return diary;
    }

    @Override
    public String toString() {
        String res = "";
        for (Workout workout : diary) {
            res += workout + "\n";
        }
        return res;
    }

    public static void main(String[] args) {
        Diary diary = new Diary();

        Workout strength = new Strength(LocalDateTime.now(), 60, '5', "Bra");

        diary.addWorkout(strength);

        System.out.println(diary);

        diary.removeWorkout(strength);

        System.out.println(strength);

    }
}
