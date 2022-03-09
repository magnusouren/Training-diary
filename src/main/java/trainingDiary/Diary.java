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
     * Method to add an exercise with reps to an existing workout with type
     * strength.
     * 
     * @param strength Workout to get exercise
     * @param name     String name of exercise to be added
     * @param reps     int[] with numbers of reps
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
