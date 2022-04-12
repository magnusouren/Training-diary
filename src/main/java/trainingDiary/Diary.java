package trainingDiary;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Diary {

        private List<IWorkout> diary = new ArrayList<>();

        public void addWorkout(IWorkout workout) {

                Predicate<IWorkout> p = w -> w.getDate().toLocalDate().equals(workout.getDate().toLocalDate());

                if (diary.stream().noneMatch(p))
                        diary.add(workout);
                else
                        throw new IllegalArgumentException("Couldn't add two workouts at same day");
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

        /**
         * Filter workouts in diary based on given month and return list of workouts
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
        public Map<String, String> getTotalSummary() {
                Map<String, String> res = new HashMap<>();

                res.put("totDuration", String.valueOf(diary.stream()
                                .mapToInt(w -> w.getDuration())
                                .summaryStatistics()
                                .getSum()));

                res.put("totAvgRating", String.format("%.1f", diary.stream()
                                .mapToInt(w -> Character.getNumericValue(w.getRating()))
                                .summaryStatistics()
                                .getAverage()));

                res.put("totWorkouts", String.valueOf(diary.size()));

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
        public Map<String, String> getStrengthSummary() {
                Map<String, String> res = new HashMap<>();

                List<Strength> strengths = diary.stream()
                                .filter(w -> w instanceof Strength)
                                .map(w -> (Strength) w)
                                .toList();
                List<List<Exercise>> exercises = strengths.stream()
                                .map(s -> (List<Exercise>) s.getExercises())
                                .toList();

                res.put("strengthTotDuration", String.valueOf(strengths.stream()
                                .mapToInt(s -> s.getDuration())
                                .summaryStatistics()
                                .getSum()));

                res.put("strengthAvgRating", String.format("%.1f", strengths.stream()
                                .mapToInt(s -> Character.getNumericValue(s.getRating()))
                                .summaryStatistics()
                                .getAverage()));

                res.put("kgLifted", String.valueOf(exercises.stream()
                                .mapToInt(l -> l.stream()
                                                .mapToInt(e -> e.getWeight() * e.getReps()
                                                                .stream()
                                                                .reduce(0, Integer::sum))
                                                .sum())
                                .summaryStatistics()
                                .getSum()));

                res.put("totStrengths", String.valueOf(strengths.size()));

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
        public Map<String, String> getRunSummary() {
                Map<String, String> res = new HashMap<>();

                List<Run> runs = diary.stream()
                                .filter(w -> w instanceof Run)
                                .map(w -> (Run) w)
                                .toList();

                res.put("runTotDuration", String.valueOf(runs.stream()
                                .mapToInt(r -> r.getDuration())
                                .summaryStatistics()
                                .getSum()));

                res.put("runAvgRating", String.format("%.1f", runs.stream()
                                .mapToInt(r -> Character.getNumericValue(r.getRating()))
                                .summaryStatistics()
                                .getAverage()));

                res.put("runTotDistance", String.valueOf(runs.stream()
                                .mapToInt(r -> r.getDistance())
                                .summaryStatistics()
                                .getSum()));

                res.put("totRuns", String.valueOf(runs.size()));

                return res;
        }

        @Override
        public String toString() {
                return diary.stream()
                                .map(diary -> diary.toString())
                                .collect(Collectors.joining("\n"));
        }
}
