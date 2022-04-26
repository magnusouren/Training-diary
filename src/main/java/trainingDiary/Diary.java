package trainingDiary;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Diary {

    private List<IWorkout> diary = new ArrayList<>();

    /**
     * Adds workout to diary, throws exception if workout already exists or a
     * workout already is added that day
     * 
     * @param workout IWorkout to be added
     */
    public void addWorkout(IWorkout workout) {

        Predicate<IWorkout> p = w -> w.getDate().toLocalDate().equals(workout.getDate().toLocalDate());

        if (!Objects.isNull(workout)) {
            if (diary.contains(workout))
                throw new IllegalArgumentException("Same workout couldn't be added twice\n");
            else if (diary.stream().noneMatch(p))
                diary.add(workout);
            else
                throw new IllegalArgumentException("Couldn't add two workouts at the same day\n");
        } else
            throw new NullPointerException("Workout cannot be null");

    }

    /**
     * Returns a new List with exercises in diary
     * 
     * @return List<IWorkout> with workouts in diary
     */
    public List<IWorkout> getDiary() {
        return new ArrayList<>(diary);
    }

    /**
     * Filter workouts in diary based on given month and return list of workouts
     * belonging to that month
     * 
     * @param month int month in year
     * @param year  int year-value
     * @return List<Workout> with workouts from given month
     */
    public List<IWorkout> getWorkoutsPerMonth(int month, int year) {
        return diary.stream()
                .filter(w -> w.getDate().getMonthValue() == month
                        && w.getDate().getYear() == year)
                .toList();

    }

    /**
     * Creates a map with summary-data based on all workouts in diary.
     * Calculates total duration.
     * Calculates avergae rating
     * Calculates amount of workouts
     * 
     * @return Map<String, String> with keyvalues and summary data
     */
    public Map<String, Number> getTotalSummary() {
        Map<String, Number> res = new HashMap<>();
        res.put("totDuration", (int) diary.stream()
                .mapToInt(w -> w.getDuration())
                .summaryStatistics()
                .getSum());

        res.put("totAvgRating", diary.stream()
                .mapToInt(w -> Character.getNumericValue(w.getRating()))
                .summaryStatistics()
                .getAverage());

        res.put("totWorkouts", diary.size());

        return res;

    }

    /**
     * Creates a map with summary-data based on all Strenght-workouts in diary.
     * Calculates total duration.
     * Calculates avergae rating
     * Calculates total kilos lifted
     * Calculates amount of Strength-workouts
     * 
     * @return Map<String, String> with data and keys.
     */
    public Map<String, Number> getStrengthSummary() {
        Map<String, Number> res = new HashMap<>();

        List<Strength> strengths = diary.stream()
                .filter(w -> w instanceof Strength)
                .map(w -> (Strength) w)
                .toList();
        List<List<Exercise>> exercises = strengths.stream()
                .map(s -> (List<Exercise>) s.getExercises())
                .toList();

        res.put("strengthTotDuration", strengths.stream()
                .mapToInt(s -> s.getDuration())
                .sum());

        res.put("strengthAvgRating", strengths.stream()
                .mapToInt(s -> Character.getNumericValue(s.getRating()))
                .summaryStatistics()
                .getAverage());

        res.put("kgLifted", exercises.stream()
                .mapToInt(l -> l.stream()
                        .mapToInt(e -> e.getWeight() * e.getReps()
                                .stream()
                                .reduce(0, Integer::sum))
                        .sum())
                .sum());

        res.put("totStrengths", strengths.size());

        return res;
    }

    /**
     * Creates a map with summary-data based on all Run-workouts in diary.
     * Calculates total duration.
     * Calculates avergae rating
     * Calculates total run distance
     * Calculates amount of run-workouts
     * 
     * @return Map<String, String> with data and keys.
     */
    public Map<String, Number> getRunSummary() {
        Map<String, Number> res = new HashMap<>();

        List<Run> runs = diary.stream()
                .filter(w -> w instanceof Run)
                .map(w -> (Run) w)
                .toList();

        res.put("runTotDuration", runs.stream()
                .mapToInt(r -> r.getDuration())
                .sum());

        res.put("runAvgRating", runs.stream()
                .mapToInt(r -> Character.getNumericValue(r.getRating()))
                .summaryStatistics()
                .getAverage());

        res.put("runTotDistance", runs.stream()
                .mapToInt(r -> r.getDistance())
                .sum());

        res.put("totRuns", runs.size());

        return res;
    }

    @Override
    public String toString() {
        return diary.stream()
                .map(diary -> diary.toString())
                .collect(Collectors.joining("\n"));
    }
}
