package trainingDiary;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Diary {

    List<Workout> diary = new ArrayList<>();

    void addWorkout(Workout workout) {
        diary.add(workout);
    }

    /**
     * Legger til en øvelse til økten hvis økten er av type Strength. Utløser unntak
     * hvis ikke.
     * 
     * @param strength Workout som skal få øvelse lagt til
     * @param name     String navn på type øvelse
     * @param reps     int[] tallrepresentasjon av antall repetisjoner
     */
    void addExercise(Workout strength, String name, int[] reps) {
        if (strength instanceof Strength) {
            Strength castStrength = (Strength) strength;
            castStrength.addExercise(name, reps);
        } else {
            throw new IllegalArgumentException("Workout is not a strength-workout, couldn't add exercise");
        }
    }

    @Override
    public String toString() {
        String res = "";
        for (Workout workout : diary) {
            res += workout + "\n";
        }
        return res;
    }
}
