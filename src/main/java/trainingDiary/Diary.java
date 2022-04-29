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
        return new ArrayList<>(diary.stream()
                .filter(w -> w.getDate().getMonthValue() == month
                        && w.getDate().getYear() == year)
                .toList());

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

        totDuration(res, diary, "totDuration");
        averageRating(res, diary, "totAvgRating");
        totalWorkouts(res, diary, "totWorkouts");

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

        totDuration(res, new ArrayList<>(strengths), "strengthTotDuration");
        averageRating(res, new ArrayList<>(strengths), "strengthAvgRating");
        totalWorkouts(res, new ArrayList<>(strengths), "totStrengths");

        List<List<Exercise>> exercises = strengths.stream()
                .map(s -> (List<Exercise>) s.getExercises())
                .toList();

        res.put("kgLifted", exercises.stream()
                .mapToInt(l -> l.stream()
                        .mapToInt(e -> e.getWeight() * e.getReps()
                                .stream()
                                .reduce(0, Integer::sum))
                        .sum())
                .sum());

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

        totDuration(res, new ArrayList<>(runs), "runTotDuration");
        averageRating(res, new ArrayList<>(runs), "runAvgRating");
        totalWorkouts(res, new ArrayList<>(runs), "totRuns");

        res.put("runTotDistance", runs.stream()
                .mapToInt(r -> r.getDistance())
                .sum());

        return res;
    }

    /**
     * Method to get total duration from a list of workouts and map it to a
     * key-value in a map.
     * 
     * @param map      Map<String, Number> to get total duration added
     * @param workouts List<IWorkout> Workouts
     * @param key      String keyvalue
     */
    private void totDuration(Map<String, Number> map, List<IWorkout> workouts, String key) {
        map.put(key, workouts.stream()
                .mapToInt(w -> w.getDuration())
                .sum());
    }

    /**
     * Method to get average of rating on workouts and map it to a key-value in a
     * map.
     * 
     * @param map      Map<String, Number> to get average added
     * @param workouts List<IWorkout> Workouts
     * @param key      String keyvalue
     */
    private void averageRating(Map<String, Number> map, List<IWorkout> workouts, String key) {
        map.put(key, workouts.stream()
                .mapToInt(w -> Character.getNumericValue(w.getRating()))
                .summaryStatistics()
                .getAverage());
    }

    /**
     * Method to map total amount of workouts in a list to a key-value in a map.
     * 
     * @param map      Map<String, Number> to get size
     * @param workouts List<IWorkout> Workouts
     * @param key      String keyvalue
     */
    private void totalWorkouts(Map<String, Number> map, List<IWorkout> workouts, String key) {
        map.put(key, workouts.size());
    }

    @Override
    public String toString() {
        return diary.stream()
                .map(diary -> diary.toString())
                .collect(Collectors.joining("\n"));
    }
}
