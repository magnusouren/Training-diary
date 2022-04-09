package trainingDiary.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.css.PseudoClass;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Control;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import trainingDiary.Diary;
import trainingDiary.Strength;
import trainingDiary.IWorkout;
import trainingDiary.addWorkout.AddExercise;
import trainingDiary.addWorkout.AddRun;
import trainingDiary.addWorkout.AddStrength;
import trainingDiary.fileManagement.IfileManager;
import trainingDiary.fileManagement.TxtFile;

public class trainingDiaryController {

    @FXML
    private DatePicker runDate, strengthDate;

    @FXML
    private TextField runDuration, strengthDuration, runDistance, runMaxHr, runAvgHr, runTime, strengthTime,
            exerciseName, exerciseWeight, exerciseSet1, exerciseSet2, exerciseSet3, exerciseSet4;

    @FXML
    private ChoiceBox<String> runRating, strengthRating;

    @FXML
    private TextArea runComments, strengthComments;

    @FXML
    private GridPane calendar;

    @FXML
    private Text monthText, exerciseFeedback, feedbackSave, feedbackLoad;

    @FXML
    private Button btnAddExercise, btnSaveStrength, btnAddStrength, btnCancelStrength;

    @FXML
    private Label strengthLabelDate, strengthLabelTime, strengthLabelDuration, strengthLabelRating, exerciseLabelName,
            exerciseLabelWeight, exerciseLabelSet1, exerciseLabelSet2, exerciseLabelSet3,
            exerciseLabelSet4;

    @FXML
    private ComboBox<String> filenameSave, filenameLoad;

    private Collection<Control> runFields, strengthFields, exerciseFields;
    private Collection<Label> exerciseLabels, strengthLabels;

    private Collection<IWorkout> workouts;

    private int year = LocalDate.now().getYear();
    private int month = LocalDate.now().getMonthValue();

    private Diary diary = new Diary();
    private Strength tempStrength;

    /**
     * Methods that runs when window is loaded. Calls on methods that displays
     * correct content in the window. If runfields is empty, which means that the
     * window is getting loaded for the first time, the method
     * initializeInputElements() is getting called.
     * 
     */
    @FXML
    public void initialize() {
        updateDateField();
        getWorkouts();
        generateCalendar();
        initializeRatings();
        setFiles();
        if (Objects.isNull(runFields))
            initializeInputElements();

    }

    /**
     * Henter tekstverdien til satt månede og endrer feltet øverst i vinduet til
     * satt månede i tekst (med stor forbokstav) + årstall
     */
    private void updateDateField() {
        String month = Month.of(this.month).name();
        month = month.substring(0, 1) + month.substring(1).toLowerCase();

        monthText.setText(month + " " + year);

        runDate.setValue(LocalDate.now()); // Denne kan med fordel flyttes på senere
        strengthDate.setValue(LocalDate.now()); // Denne kan med fordel flyttes på senere

    }

    /**
     * Henter ut alle økter som er innenfor den satte måneden
     */
    private void getWorkouts() {
        this.workouts = diary.getDiary().stream()
                .filter(w -> w.getDate().getMonthValue() == this.month
                        && w.getDate().getYear() == this.year)
                .toList();

    }

    /**
     * Fjerner eventuelle elementer i calendar.
     * Kalkulerer hvilken ukedag den 1. denne måneden faller på.
     * Genererer TitledPanes med datoverdier for hver dag i måneden.
     */
    private void generateCalendar() {
        calendar.getChildren().clear();

        LocalDate date = LocalDate.of(year, month, 1);
        int dateVal = date.getDayOfWeek().getValue() - 1;

        while (date.getMonth().getValue() == this.month) {
            calendar.add(createTitledPane(date),
                    (dateVal % 7),
                    dateVal / 7);
            dateVal++;
            date = date.plusDays(1);
        }
    }

    /**
     * Calls on methods which adds JavaFX elements to a collection, so the
     * collections could be used later
     */
    private void initializeInputElements() {
        initializeRunFields();
        initializeExerciseFields();
        initializeExerciseLabels();
        initializeStrengthLabels();
        initializeStrengthFields();
    }

    /**
     * Metode som lager et TitledPane med dato pluss eventuel knapp for treningsøkt
     * tilhørende den dagen
     * 
     * @param date Dato som skal få tilordnet TitledPane
     * @return TitledPane med dato som tekstverdi og eventuell knapp for økt
     */
    private TitledPane createTitledPane(LocalDate date) {

        String dateValue = String.valueOf(date.getDayOfMonth());//

        TitledPane dateView = new TitledPane(dateValue + ".", createContent(date));
        dateView.collapsibleProperty().set(false);
        dateView.setMaxHeight(Double.MAX_VALUE);

        return dateView;
    }

    /**
     * Metode som lager knapp for økt tilhørende dato, hvis ikke returnerer tom
     * knapp.
     * 
     * @param date Dato som skal få tildelt knapp
     * @return Button med økt/tomt innhold
     */
    private Node createContent(LocalDate date) {

        Optional<IWorkout> workout = workouts.stream()
                .filter(w -> w.getDate().getDayOfMonth() == date.getDayOfMonth())
                .findFirst();

        return (workout.isPresent()) ? workoutButton(workout.get()) : new Pane();

    }

    /**
     * Metode som lager knapp for knapp med økt. Definerer stil og funksjonskall ved
     * interaksjon
     * 
     * @param workout Workout økt som skal få tildelt knapp
     * @return Button med innhold tilknyttet økt
     */
    private Button workoutButton(IWorkout workout) {
        String content = workout.getDate().format(DateTimeFormatter.ofPattern("HH:mm")) + " "
                + String.valueOf(workout.getClass().getSimpleName());

        Button button = new Button(content);

        Font font = new Font("System", 12);

        button.setFont(font);
        button.wrapTextProperty().setValue(true);
        button.setStyle("-fx-text-alignment: center;-fx-background-color: white;");
        button.setCursor(Cursor.HAND);
        button.setOnAction((event) -> handleWorkoutSelect(workout));
        button.setMaxWidth(Double.MAX_VALUE);
        button.setMaxHeight(Double.MAX_VALUE);

        return button;
    }

    /**
     * Gir hvert av rating-input-elementene innhold med tall 1-6 og standardverdi
     */
    private void initializeRatings() {
        ObservableList<String> ratings = FXCollections.observableArrayList("1", "2", "3", "4", "5", "6");

        runRating.setItems(ratings);
        runRating.setValue("-- Set rating --");

        strengthRating.setItems(ratings);
        strengthRating.setValue("-- Set rating --");
    }

    /**
     * Metode som legger til alle input-felt tilhørende run i en Collection
     */
    private void initializeRunFields() {
        runFields = new ArrayList<>(
                Arrays.asList(
                        runTime,
                        runDuration,
                        runDistance,
                        runMaxHr,
                        runAvgHr,
                        runDate,
                        runRating,
                        runComments));
    }

    /**
     * Metode som legger til alle input-felt tilhørende run i en Collection
     */
    private void initializeStrengthFields() {
        strengthFields = new ArrayList<>(
                Arrays.asList(
                        strengthTime,
                        strengthDuration,
                        strengthDate,
                        strengthRating,
                        strengthComments,
                        btnAddStrength));
    }

    private void initializeExerciseFields() {
        exerciseFields = new ArrayList<>(
                Arrays.asList(
                        exerciseName,
                        exerciseWeight,
                        exerciseSet1,
                        exerciseSet2,
                        exerciseSet3,
                        exerciseSet4,
                        btnCancelStrength,
                        btnAddExercise,
                        btnSaveStrength));
    }

    private void initializeExerciseLabels() {
        exerciseLabels = new ArrayList<>(
                Arrays.asList(
                        exerciseLabelName,
                        exerciseLabelWeight,
                        exerciseLabelSet1,
                        exerciseLabelSet2,
                        exerciseLabelSet3,
                        exerciseLabelSet4));
    }

    private void initializeStrengthLabels() {
        strengthLabels = new ArrayList<>(
                Arrays.asList(
                        strengthLabelDate,
                        strengthLabelTime,
                        strengthLabelDuration,
                        strengthLabelRating));
    }

    /**
     * Metode som kalles når bruker legger til ny løpeøkt. Bruker metoder fra AddRun
     * for å validere og sette tilstandene
     */
    @FXML
    private void addRun() {
        AddRun addRun = new AddRun();

        if (addRun.validate(runDate, runTime, runDuration, runDistance, runRating, runMaxHr, runAvgHr,
                runComments)) {

            diary.addWorkout(addRun.getRun());

            clearInput(runFields);
            initialize();

        }
    }

    /**
     * Metode som kalles når bruker legger til ny styrkeøkt. Bruker metoder fra
     * AddWorkout for å validere og sette tilstandene.
     */
    @FXML
    private void addStrength() {
        AddStrength addStrength = new AddStrength();

        if (addStrength.validate(strengthDate, strengthTime, strengthDuration, strengthRating, strengthComments)) {
            switchStrengthInput(false);
            tempStrength = addStrength.getStrength();

        }
    }

    /**
     * Method to be called when an exercise should be added to a strength-workout.
     * If the exercise has valid input-values, the exercise are added to the
     * strength-workout. If values are invalid, the object are being set equals
     * null, and feedback displayed to the user
     */
    @FXML
    private void addExercise() {
        AddExercise addExercise = new AddExercise();

        if (addExercise.validate(exerciseName, exerciseWeight,
                List.of(exerciseSet1, exerciseSet2, exerciseSet3, exerciseSet4))) {

            tempStrength.addExercise(addExercise.getExercise());
            exerciseFeedback.setText(addExercise.getExercise() + " added!");
            clearInput(exerciseFields);

        } else {
            addExercise = null;
            exerciseFeedback.setText("Illegal value(s) on this exercise");
        }

    }

    /**
     * When user desides to save the workout, the workout are added to the diary,
     * and the method that clears the inputfields are called
     */
    @FXML
    private void saveStrength() {
        diary.addWorkout(tempStrength);
        clearStrength();

    }

    /**
     * Method that sets the temporary strength to null, clears all
     * strength inputfields, and sets the disabilty on the inputfields to default.
     * Clears the feedbackfield with feedback to user
     */
    @FXML
    private void clearStrength() {
        tempStrength = null;

        clearInput(exerciseFields);
        clearInput(strengthFields);

        switchStrengthInput(true);
        exerciseFeedback.setText("");
        initialize();
    }

    /**
     * Enable/disable inputfields related to adding a strength-workout depending on
     * a boolean value.
     * If a field is getting disabled, the related label get's grey text-color.
     * If a field is getting enabled, the related label get's black text-color.
     * 
     * @param status boolean true=>default - false=>flipped
     */
    private void switchStrengthInput(boolean status) {

        exerciseFields.forEach(f -> f.setDisable(status));
        strengthFields.forEach(f -> f.setDisable(!status));

        if (status) {
            exerciseLabels.forEach(f -> f.setTextFill(Color.web("grey")));
            strengthLabels.forEach(f -> f.setTextFill(Color.web("black")));
        } else {
            exerciseLabels.forEach(f -> f.setTextFill(Color.web("black")));
            strengthLabels.forEach(f -> f.setTextFill(Color.web("grey")));
        }

    }

    /**
     * Tar inn en liste med Controll-objekter.
     * Ittererer over alle elementene i Collectionen og:
     * 1) Fjerner eventuelle psuedoklasser som er gitt til objektet
     * 2) Sjekker instansen til objektet og behandler det deretter
     * 
     * Om TextField eller TextArea -> fjern innhold
     * 
     * Om DatePicker -> set verdi til dagens dato
     */
    private void clearInput(Collection<Control> inputFields) {
        final PseudoClass errorClass = PseudoClass.getPseudoClass("error");
        final PseudoClass validClass = PseudoClass.getPseudoClass("valid");

        inputFields.forEach((f) -> {
            f.pseudoClassStateChanged(errorClass, false);
            f.pseudoClassStateChanged(validClass, false);
            if (f instanceof TextField)
                ((TextField) f).clear();
            else if (f instanceof TextArea)
                ((TextArea) f).clear();
            else if (f instanceof DatePicker)
                ((DatePicker) f).setValue(LocalDate.now());
        });

    }

    /**
     * Setter feltene månede og år tilsvarende dagens månede og årstall
     */
    @FXML
    private void today() {
        year = LocalDate.now().getYear();
        month = LocalDate.now().getMonthValue();

        initialize();
    }

    /**
     * Dekrementerer verdien til månede, hvis januar senkes årstallet med 1 og
     * månede settes til desember
     */
    @FXML
    private void prevMonth() {
        if (month == 1) {
            month = 12;
            year--;
        } else
            month--;

        initialize();
    }

    /**
     * Inkrementerer verdien til månede, hvis desember øker årstall med 1 og månede
     * settes til januar
     */
    @FXML
    private void nextMonth() {
        if (month == 12) {
            month = 1;
            year++;
        } else
            month++;

        initialize();

    }

    /**
     * Metoden som kalles når en økt blir valgt
     * 
     * @param workout Workout økt som skal vises
     */
    private void handleWorkoutSelect(IWorkout workout) {
        Stage stage = new Stage();

        Text text = new Text(workout.toString());
        text.setStyle("-fx-font-family: 'monospaced';");

        TextFlow flow = new TextFlow(text);

        stage.setScene(new Scene(flow, 430, 360));
        stage.show();

    }

    /**
     * Saves diary to file. If filename is not according to requirements changes
     * file type to .txt. Provides user with necessary feedback
     */
    @FXML
    private void handleSave() {
        IfileManager fileManager = new TxtFile();
        String file = filenameSave.getValue();

        if (!Objects.isNull(file) && !file.isBlank()) {
            if (!file.endsWith(".txt"))
                file = file + ".txt";

            try {
                fileManager.write(file, diary);
                feedbackSave.setText("'" + file + "' was saved!");
                initialize();

            } catch (FileNotFoundException e) {
                feedbackSave.setText(
                        "A problem occurder when tryin to save '" + file + "' \nTry again with a new filename");
            } catch (RuntimeException e) {
                System.out.println("Invalid values in diary, couldn't save");
            }

        } else {
            feedbackSave.setText("Enter filename!");
        }

        feedbackLoad.setText("");

    }

    /**
     * Method to load file and display that file in window.
     * Gets chosen file from user and uses methods from TxtFile (extends ManageFile)
     * Prints message to user if file wasn1t found.
     * Sets value of filenameSave to chosen file for easier saving later
     */
    @FXML
    private void handleLoad() {
        IfileManager fileManager = new TxtFile();
        String file = filenameLoad.getValue();
        try {
            diary = fileManager.read(file);
            initialize();
            feedbackLoad.setText("'" + file + "' was loaded");
        } catch (FileNotFoundException e) {
            feedbackLoad.setText("Choose a file!");
        } catch (RuntimeException e) {
            feedbackLoad.setText("Error, improper values!");
        }

        filenameSave.setValue(file);
        feedbackSave.setText("");
    }

    /**
     * Method to add existing files in directory to the input fields, this will make
     * it easier for the user to save/load files
     */
    private void setFiles() {

        Set<String> files = Stream.of(new File("src/main/resources/trainingDiary/saves/").listFiles())
                .filter(file -> !file.isDirectory())
                .map(File::getName)
                .collect(Collectors.toSet());

        filenameSave.getItems().clear();
        filenameLoad.getItems().clear();

        filenameSave.getItems().addAll(files);
        filenameLoad.getItems().addAll(files);
    }

}
