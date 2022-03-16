package trainingDiary;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;

public class trainingDiaryController {
    private Diary diary = new Diary();
    private int year = 2022;
    private int month = 3;

    public void testData() {

        Workout strength1 = new Strength(LocalDateTime.of(2022, 02, 1, 12, 00), 90, '6', "Veldig bra økt");
        Workout strength2 = new Strength(LocalDateTime.of(2022, 02, 2, 13, 30), 30, '2', "Ble syk dro tidlig...");
        Workout strength3 = new Strength(LocalDateTime.of(2022, 02, 3, 11, 00), 60, '4', "OK");
        Workout strength4 = new Strength(LocalDateTime.of(2022, 02, 4, 8, 30), 56, '5', "Likte økten");
        Workout strength5 = new Strength(LocalDateTime.of(2022, 02, 5, 04, 30), 90, '4', "Litt tungt men greit");
        Workout strength6 = new Strength(LocalDateTime.of(2022, 02, 6, 10, 00), 80, '1', "Elendig form");
        Workout strength7 = new Strength(LocalDateTime.of(2022, 02, 7, 20, 00), 60, '2', "Fortsatt dårlig");
        Workout strength8 = new Strength(LocalDateTime.of(2022, 03, 12, 18, 00), 75, '4', "Begynner å komme seg");
        Workout strength9 = new Strength(LocalDateTime.of(2022, 03, 13, 13, 00), 70, '5', "Bra igjen");

        diary.addWorkout(strength1);
        diary.addWorkout(strength2);
        diary.addWorkout(strength3);
        diary.addWorkout(strength4);
        diary.addWorkout(strength5);
        diary.addWorkout(strength6);
        diary.addWorkout(strength7);
        diary.addWorkout(strength8);
        diary.addWorkout(strength9);

        diary.addExercise(strength1, "Benkpress", 8, 8, 8);
        diary.addExercise(strength1, "Knebøy", 5, 5, 5);
        diary.addExercise(strength1, "Bicepscurl", 10, 8, 6);
        diary.addExercise(strength2, "Pushups", 20, 30);
        diary.addExercise(strength2, "Situps", 8, 7, 8);
        diary.addExercise(strength2, "Spensthopp", 10, 8, 6);
        diary.addExercise(strength3, "Benkpress", 8, 8, 8);
        diary.addExercise(strength3, "Knebøy", 5, 5, 5);
        diary.addExercise(strength3, "Bicepscurl", 10, 8, 6);
        diary.addExercise(strength4, "Pushups", 20, 30);
        diary.addExercise(strength4, "Situps", 8, 7, 8);
        diary.addExercise(strength4, "Spensthopp", 10, 8, 6);
        diary.addExercise(strength5, "Benkpress", 8, 8, 8);
        diary.addExercise(strength5, "Knebøy", 5, 5, 5);
        diary.addExercise(strength5, "Bicepscurl", 10, 8, 6);
        diary.addExercise(strength6, "Pushups", 20, 30);
        diary.addExercise(strength6, "Situps", 8, 7, 8);
        diary.addExercise(strength6, "Spensthopp", 10, 8, 6);
        diary.addExercise(strength7, "Benkpress", 8, 8, 8);
        diary.addExercise(strength7, "Knebøy", 5, 5, 5);
        diary.addExercise(strength7, "Bicepscurl", 10, 8, 6);
        diary.addExercise(strength8, "Pushups", 20, 30);
        diary.addExercise(strength8, "Situps", 8, 7, 8);
        diary.addExercise(strength8, "Spensthopp", 10, 8, 6);
        diary.addExercise(strength9, "Benkpress", 8, 8, 8);
        diary.addExercise(strength9, "Knebøy", 5, 5, 5);
        diary.addExercise(strength9, "Bicepscurl", 10, 8, 6);

        Workout run1 = new Run(LocalDateTime.of(2022, 03, 1, 12, 00), 60, 5000, '5', "Løp 1", 150, 200);
        Workout run2 = new Run(LocalDateTime.of(2022, 03, 2, 13, 30), 60, 7500, '6', "Løp 2", 160, 190);
        Workout run3 = new Run(LocalDateTime.of(2022, 03, 3, 11, 00), 60, 10000, '4', "Løp 3", 140, 170);
        Workout run4 = new Run(LocalDateTime.of(2022, 03, 4, 8, 30), 60, 8000, '3', "Løp 4", 150, 206);
        Workout run5 = new Run(LocalDateTime.of(2022, 03, 5, 04, 30), 60, 6000, '3', "Løp 5", 140, 200);
        Workout run6 = new Run(LocalDateTime.of(2022, 03, 6, 10, 00), 60, 12000, '5', "Løp 6 ", 155, 190);
        Workout run7 = new Run(LocalDateTime.of(2022, 03, 7, 20, 00), 60, 6500, '4', "Løp 7", 140, 196);
        Workout run8 = new Run(LocalDateTime.of(2022, 03, 8, 18, 00), 60, 7500, '4', "Løp 8 ", 160, 185);
        Workout run9 = new Run(LocalDateTime.of(2022, 03, 9, 13, 00), 60, 10000, '2', "Løp 9", 170, 194);

        diary.addWorkout(run1);
        diary.addWorkout(run2);
        diary.addWorkout(run3);
        diary.addWorkout(run4);
        diary.addWorkout(run5);
        diary.addWorkout(run6);
        diary.addWorkout(run7);
        diary.addWorkout(run8);
        diary.addWorkout(run9);
    }

    @FXML
    public GridPane calendar;

    @FXML
    public void initialize() {
        testData();
        List<Workout> workouts = diary.getDiary().stream()
                .filter(workout -> workout.getDate().getMonth().getValue() == this.month).collect(Collectors.toList());
        generateCalendar(workouts);

    }

    private void generateCalendar(List<Workout> workouts) {
        LocalDate monthSet = LocalDate.of(year, month, 1);

        while (monthSet.getMonth().getValue() == month) {
            calendar.add(createTitledPane(monthSet),
                    (monthSet.getDayOfMonth() % 7),
                    1 + (monthSet.getDayOfMonth()) / 7);
            monthSet = monthSet.plusDays(1);
        }
    }

    private TitledPane createTitledPane(LocalDate date) {
        String dateValue = String.valueOf(date.getDayOfMonth());
        TitledPane dateView = new TitledPane(dateValue + ".", createWorkoutButton(date));
        dateView.collapsibleProperty().set(false);
        dateView.setMaxHeight(Double.MAX_VALUE);
        return dateView;
        // return new TitledPane(date + ".", createItemButton(diary.getDiary().get(0)));
    }

    private Button createWorkoutButton(LocalDate monthSet) {

        List<Workout> stream = diary.getDiary().stream()
                .filter(w -> w.getDate().getDayOfMonth() == monthSet.getDayOfMonth()
                        && w.getDate().getMonthValue() == monthSet.getMonthValue()
                        && w.getDate().getYear() == monthSet.getYear())
                .collect(Collectors.toList());

        if (stream.isEmpty()) {
            Button button = new Button("----\n");
            button.wrapTextProperty().setValue(true);
            button.setStyle("-fx-text-alignment: center;-fx-background-color: white;-fx-border-color:black;");
            // button.setCursor(Cursor.HAND);
            button.setMaxWidth(Double.MAX_VALUE);
            button.setMaxHeight(Double.MAX_VALUE);

            return button;
        }

        else
            return createButton(stream.get(0));
    }

    private Button createButton(Workout workout) {
        Button button = new Button(
                workout.getDate().format(DateTimeFormatter.ofPattern("HH:mm")) + " "
                        + String.valueOf(workout.getClass().getSimpleName()));

        Font font = new Font("System", 12);
        button.setFont(font);
        button.wrapTextProperty().setValue(true);
        button.setStyle("-fx-text-alignment: center;-fx-background-color: white;-fx-border-color:black;");
        button.setCursor(Cursor.HAND);
        button.setOnAction((event) -> handleWorkoutSelect(workout));
        button.setMaxWidth(Double.MAX_VALUE);
        button.setMaxHeight(Double.MAX_VALUE);

        return button;
    }

    private void handleWorkoutSelect(Workout workout) {
        System.out.println(workout);
        // +++++
    }

    public static void main(String[] args) {

    }
}