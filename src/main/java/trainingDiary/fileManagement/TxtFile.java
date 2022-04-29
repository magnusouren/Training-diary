package trainingDiary.fileManagement;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.IllegalFormatException;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.stream.Collectors;

import trainingDiary.Diary;
import trainingDiary.Exercise;
import trainingDiary.Run;
import trainingDiary.Strength;
import trainingDiary.IWorkout;

public class TxtFile implements IfileManager {

    @Override
    public void write(String filename, Diary diary) throws IOException, RuntimeException, IllegalArgumentException {
        if (Objects.isNull(filename) || filename.isBlank())
            throw new IllegalArgumentException("Enter filename!");
        if (!filename.endsWith(".txt"))
            filename += ".txt";
        try (PrintWriter writer = new PrintWriter(
                new File("src/main/resources/trainingDiary/saves/" + filename))) {
            for (IWorkout workout : diary.getDiary()) {
                printWorkout(writer, workout);
            }
        }

    }

    @Override
    public Diary read(String filename) throws IOException, RuntimeException {
        Diary diary = new Diary();
        try (Scanner scanner = new Scanner(new File("src/main/resources/trainingDiary/saves/" + filename))) {

            while (scanner.hasNextLine()) {
                List<String> line = Arrays.stream(scanner.nextLine().split(",")).toList();

                String type = line.get(0);
                LocalDateTime dateTime = calcDate(line.get(1).split("-"));
                int duration = Integer.parseInt(line.get(2));
                char rating = line.get(3).charAt(0);
                String comments = line.get(4);

                if (type.equals("Strength")) {
                    Strength strength = new Strength(dateTime, duration, rating, comments);
                    diary.addWorkout(readExercise(strength, line));
                }

                else if (type.equals("Run")) {
                    int distance = Integer.parseInt(line.get(5));
                    int maxHeartRate = Integer.parseInt(line.get(6));
                    int averageHeartRate = Integer.parseInt(line.get(7));

                    diary.addWorkout(new Run(dateTime, duration, distance, rating, comments,
                            averageHeartRate, maxHeartRate));
                }
            }
        }

        return diary;
    }

    /**
     * Reads values for exercise and adds to strength. Iterates over list while it
     * has more exercises
     * 
     * @param strength Strength that gets exercises
     * @param line     List<String> with values from line being read
     * @return Strength with exercises
     */
    private Strength readExercise(Strength strength, List<String> line) {
        Iterator<String> exercises = line.subList(5, line.size()).iterator();
        while (exercises.hasNext()) {
            Exercise ex = new Exercise();
            ex.setName(exercises.next());
            ex.setWeight(Integer.parseInt(exercises.next()));

            Arrays.stream(exercises.next().split("/"))
                    .map(Integer::parseInt)
                    .forEach(r -> ex.addRep(r));

            strength.addExercise(ex);

        }
        return strength;
    }

    /**
     * Takes in a workout and print the workout in a line in a .txt-document.
     * Casts the workout depending on instance to get all values.
     * 
     * @param writer  Printwriter the file to be written to
     * @param workout Workout to be written
     * @throws IllegalFormatException Throws if some of the data is invalid
     */
    private void printWorkout(PrintWriter writer, IWorkout workout) throws IllegalFormatException {

        String commonFields = String.format("%s,%s,%s,%s,%s,",
                workout.getClass().getSimpleName(),
                workout.getDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd-hh:mm")),
                workout.getDuration(),
                workout.getRating(),
                workout.getComment().replace(",", ""));

        writer.print(commonFields);

        if (workout instanceof Run) {
            Run run = (Run) workout;

            writer.print(run.getDistance() + ",");
            writer.print(run.getMaxHeartRate() + ",");
            writer.print(run.getAvaerageHeartRate() + ",");

        } else if (workout instanceof Strength) {
            Strength strength = (Strength) workout;

            for (Exercise ex : strength.getExercises()) {
                writer.print(ex.getName() + "," + ex.getWeight() + ",");
                String reps = ex.getReps().stream()
                        .map(r -> String.valueOf(r))
                        .collect(Collectors.joining("/"));
                writer.print(reps + ",");
            }

        }
        writer.print("\n");
    }

    /**
     * Calculate a string with date-values to LocalDateTime
     * 
     * @param inpt String[] on the form yyyy-MM-dd-hh:mm
     * @return LocalDateTime
     * @throws RuntimeException throws if invalid values occurs.
     */
    private LocalDateTime calcDate(String[] inpt) throws RuntimeException {
        int year = Integer.valueOf(inpt[0]);
        int month = Integer.valueOf(inpt[1]);
        int day = Integer.valueOf(inpt[2]);
        LocalDate date = LocalDate.of(year, month, day);

        String[] hhmm = inpt[3].split(":");
        int hh = Integer.valueOf(hhmm[0]);
        int mm = Integer.valueOf(hhmm[1]);
        LocalTime time = LocalTime.of(hh, mm);

        LocalDateTime res = LocalDateTime.of(date, time);

        return res;
    }
}
