package trainingDiary;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Diary {

    private List<IWorkout> diary = new ArrayList<>();

    public void addWorkout(IWorkout workout) {
        if (!diary.contains(workout))
            diary.add(workout);
        else
            throw new IllegalArgumentException("Workout already exists");
    }

    public void removeWorkout(IWorkout workout) {
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
    public void addExercise(IWorkout strength, Exercise exercise, String name, int weigth, List<Integer> reps) {
        if (strength instanceof Strength) {
            Strength castStrength = (Strength) strength;
            castStrength.addExercise(exercise);
        } else {
            throw new IllegalArgumentException("Workout is not a strength-workout, couldn't add exercise");
        }
    }

    public List<IWorkout> getDiary() {
        return new ArrayList<>(diary);
    }

    public List<IWorkout> getWorkoutsPerMonth(int month, int year) {
        return diary.stream()
                .filter(w -> w.getDate().getMonthValue() == month
                        && w.getDate().getYear() == year)
                .toList();

    }

    @Override
    public String toString() {
        return diary.stream()
                .map(diary -> diary.toString())
                .collect(Collectors.joining("\n"));
    }

}
