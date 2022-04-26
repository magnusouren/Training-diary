package trainingDiary.controller;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Control;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.control.Alert.AlertType;
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
import trainingDiary.fileManagement.IfileManager;
import trainingDiary.fileManagement.TxtFile;
import trainingDiary.validation.ValidateExercise;
import trainingDiary.validation.ValidateRun;
import trainingDiary.validation.ValidateStrength;

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
    private Text monthText, exerciseFeedback, feedbackSave, feedbackLoad, StotalDuration, SavgRating, StotalNumW,
            SrunDuration, SavgRunRating, SrunDistance, StotalNumRun, SstrengthDuration, SavgStrengthRating, SkgLifted,
            StotalNumStrength;

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
     * Runs when window is loaded.
     * Sets workouts to a list of workouts in chosen month.
     * Calls on method that adds inputfields to collections if its during startup.
     * Calls on method that generates a calendar in app.
     * Calls on method that displays summary data.
     */
    @FXML
    private void initialize() {
        workouts = diary.getWorkoutsPerMonth(month, year);

        if (Objects.isNull(runFields))
            initializeInputElements();

        updateDateField();
        generateCalendar();
        initializeFields();
        setSummary();
    }

    /**
     * Calls on methods which adds JavaFX elements to a collection, so the
     * collections could be used later
     */
    private void initializeInputElements() {
        setRunFields();
        setExerciseFields();
        setExerciseLabels();
        setStrengthLabels();
        setStrengthFields();
    }

    /**
     * Gets textvalue of chosen month and year, sets values to textelement in app
     */
    private void updateDateField() {
        String month = Month.of(this.month).name();
        month = month.substring(0, 1) + month.substring(1).toLowerCase();

        monthText.setText(month + " " + year);

    }

    /**
     * Clears calendar in case it has content.
     * Calculates which weekday that the first day of month belongs to.
     * Calls method that adds titledPanes for each day in month to right place in
     * grid.
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
     * Creates titledPane with datevalue in header, and adds correct content in
     * pane.
     * 
     * @param date LocalDate of the titledPane
     * @return TitledPane with date and content
     */
    private TitledPane createTitledPane(LocalDate date) {

        String dateValue = String.valueOf(date.getDayOfMonth());//

        TitledPane dateView = new TitledPane(dateValue + ".", createContent(date));
        dateView.collapsibleProperty().set(false);
        dateView.setMaxHeight(Double.MAX_VALUE);

        return dateView;
    }

    /**
     * If date has a workout a method for creating node with content is called.
     * If no workout an empty pane is returned.
     * 
     * @param date LocalDate that's getting content
     * @return Node button for workout/empty pane
     */
    private Node createContent(LocalDate date) {

        Optional<IWorkout> workout = workouts.stream()
                .filter(w -> w.getDate().getDayOfMonth() == date.getDayOfMonth())
                .findFirst();

        return (workout.isPresent()) ? workoutButton(workout.get()) : new Pane();

    }

    /**
     * Creates a button with timevalue and type for that workout.
     * Styles button and sets method to called on action.
     * 
     * @param workout IWorkout that is getting a button
     * @return Button with content related to the workout
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
     * Sets values to input-fields that has values that needs to be set during
     * initializing of application.
     * 
     * Sets values in rating-choiceboxes for the number 1 - 6
     * Sets datevalues in date-fields to todays date.
     * Calls on method that sets existing filenames to file-comboxes.
     */
    private void initializeFields() {
        ObservableList<String> ratings = FXCollections.observableArrayList("1", "2", "3", "4", "5", "6");

        runRating.setItems(ratings);
        runRating.setValue("-- Set rating --");

        strengthRating.setItems(ratings);
        strengthRating.setValue("-- Set rating --");

        runDate.setValue(LocalDate.now());
        strengthDate.setValue(LocalDate.now());

        setFilenames();
    }

    /**
     * Method to add existing files in directory that stores saved data to
     * comboboxes, this will make it easier for the user to save/load correct
     * files.
     */
    private void setFilenames() {

        Set<String> filenames = Stream.of(new File("src/main/resources/trainingDiary/saves/").listFiles())
                .filter(file -> !file.isDirectory())
                .map(File::getName)
                .collect(Collectors.toSet());

        filenameSave.getItems().clear();
        filenameLoad.getItems().clear();

        filenameSave.getItems().addAll(filenames);
        filenameLoad.getItems().addAll(filenames);
    }

    /**
     * Calls methods that displays summary-data
     */
    private void setSummary() {
        summaryTotal();
        summaryRun();
        summaryStrength();
    }

    /**
     * Sets textvalues from map to text-element in summary-tab in app.
     */
    private void summaryTotal() {
        Map<String, Number> sTotal = diary.getTotalSummary();
        StotalDuration.setText(sTotal.get("totDuration").toString());
        SavgRating.setText(sTotal.get("totAvgRating").toString());
        StotalNumW.setText(sTotal.get("totWorkouts").toString());
    }

    /**
     * Sets textvalues from map to text-element in summary-tab in app.
     */
    private void summaryRun() {
        Map<String, Number> sRun = diary.getRunSummary();
        SrunDuration.setText(sRun.get("runTotDuration").toString());
        SavgRunRating.setText(sRun.get("runAvgRating").toString());
        SrunDistance.setText(sRun.get("runTotDistance").toString());
        StotalNumRun.setText(sRun.get("totRuns").toString());
    }

    /**
     * Sets textvalues from map to text-element in summary-tab in app.
     */
    private void summaryStrength() {
        Map<String, Number> sStrength = diary.getStrengthSummary();
        SstrengthDuration.setText(sStrength.get("strengthTotDuration").toString());
        SavgStrengthRating.setText(sStrength.get("strengthAvgRating").toString());
        SkgLifted.setText(sStrength.get("kgLifted").toString());
        StotalNumStrength.setText(sStrength.get("totStrengths").toString());
    }

    /**
     * Method to add inputfields to collection for easier usage later in.
     */
    private void setRunFields() {
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
     * Method to add inputfields to collection for easier usage later in.
     */
    private void setStrengthFields() {
        strengthFields = new ArrayList<>(
                Arrays.asList(
                        strengthTime,
                        strengthDuration,
                        strengthDate,
                        strengthRating,
                        strengthComments,
                        btnAddStrength));
    }

    /**
     * Method to add inputfields to collection for easier usage later in.
     */
    private void setExerciseFields() {
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

    /**
     * Method to add inputfields to collection for easier usage later in.
     */
    private void setExerciseLabels() {
        exerciseLabels = new ArrayList<>(
                Arrays.asList(
                        exerciseLabelName,
                        exerciseLabelWeight,
                        exerciseLabelSet1,
                        exerciseLabelSet2,
                        exerciseLabelSet3,
                        exerciseLabelSet4));
    }

    /**
     * Method to add inputfields to collection for easier usage later in.
     */
    private void setStrengthLabels() {
        strengthLabels = new ArrayList<>(
                Arrays.asList(
                        strengthLabelDate,
                        strengthLabelTime,
                        strengthLabelDuration,
                        strengthLabelRating));
    }

    /**
     * Method that is called when user is interacting on the button that saves a
     * run.
     * 
     * Creates a temporary ValidateRun that contains validationmethods to validate
     * format on all inputfields.
     * 
     * If addRun.isValid returns true, the run with valid fields is beeing added to
     * the diary.
     * If addRun.isValid returns false an alertbox with errormessage is displayed.
     */
    @FXML
    private void addRun() {

        ValidateRun validateRun = new ValidateRun(runDate, runTime, runDuration, runDistance, runRating, runMaxHr,
                runAvgHr, runComments);

        if (validateRun.isValid()) {

            addWorkout(validateRun.getRun());
            clearInput(runFields);
            initialize();

        } else {
            showAlert(AlertType.ERROR, "Invalid inputs on new run", validateRun.getErrorMessage());
        }

        validateRun = null;

    }

    /**
     * Method that is called when user is interacting on the button that saves a
     * strength.
     * 
     * Creates a temporary validateStrength that contains validationmethods to
     * validate inputfields.
     * 
     * If validateStrength.isValid returns true, the strength is saved as
     * tempStrength, since this strength is later going to get exercises and then be
     * saved.
     * If validateStrength.isValid returns false an alertbox with errormessage is
     * displayed.
     */
    @FXML
    private void addStrength() {

        ValidateStrength validateStrength = new ValidateStrength(strengthDate, strengthTime, strengthDuration,
                strengthRating, strengthComments);

        if (validateStrength.isValid()) {

            switchStrengthInput(false);
            tempStrength = validateStrength.getStrength();

        } else {
            showAlert(AlertType.ERROR, "Invalid inputs on new strength", validateStrength.getMessage());
        }

        validateStrength = null;

    }

    /**
     * Method to be called when an exercise should be added to a strength-workout.
     * 
     * Creates a temporary validateExercise that contains validationmethods to
     * validate inputfields.
     * 
     * If validateExercise.isValid returns true, the exercise is added to
     * tempstrength. And inputfields are cleared.
     * 
     * If validateExercise.isValid returns false, an alertbox with errormessage is
     * displayed.
     */
    @FXML
    private void addExercise() {

        ValidateExercise validateExercise = new ValidateExercise(exerciseName, exerciseWeight,
                List.of(exerciseSet1, exerciseSet2, exerciseSet3, exerciseSet4));

        if (validateExercise.isValid()) {

            tempStrength.addExercise(validateExercise.getExercise());
            exerciseFeedback.setText(validateExercise.getExercise() + " added!");
            clearInput(exerciseFields);

        } else {
            showAlert(AlertType.ERROR, "Invalid inputs on exercise",
                    validateExercise.getMessage());
        }

        validateExercise = null;
    }

    /**
     * When user desides to save the workout, the workout are added to the diary,
     * and the method that clears the inputfields are called
     */
    @FXML
    private void saveStrength() {
        addWorkout(tempStrength);
        clearStrength();
        initialize();

    }

    /**
     * Adds workout to diary, if exception is thrown errormessage is shown in
     * alertbox.
     * 
     * @param workout IWorkout to be added to diary
     */
    private void addWorkout(IWorkout workout) {
        try {
            diary.addWorkout(workout);
        } catch (IllegalArgumentException e) {
            showAlert(AlertType.WARNING, "Your workout could not be saved", e.getLocalizedMessage());
        }
    }

    /**
     * Calls on methods that clears and sets inputs to default.
     * Switches disabled input-elements to be enabled and opposite.
     * Clears feedback-text that has been showed.
     */
    @FXML
    private void clearStrength() {
        tempStrength = null;

        clearInput(exerciseFields);
        clearInput(strengthFields);

        switchStrengthInput(true);
        exerciseFeedback.setText("");
    }

    /**
     * Enable/disable inputfields related to adding a strength-workout depending on
     * a boolean value.
     * If a field is getting disabled, the related label get's grey text-color.
     * If a field is getting enabled, the related label get's black text-color.
     * 
     * @param status boolean true=>default / false=>flipped
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
     * Itterates over a collection of inputfields.
     * 1) Clears eventual pseudoclasses on field
     * 2) Clears the textvalue the field has
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
            else if (f instanceof DatePicker) {
                ((DatePicker) f).getEditor().pseudoClassStateChanged(errorClass, false);
                ((DatePicker) f).getEditor().pseudoClassStateChanged(validClass, false);
            }
        });

    }

    /**
     * Sets month and year to today. Refresh window
     */
    @FXML
    private void today() {
        year = LocalDate.now().getYear();
        month = LocalDate.now().getMonthValue();

        initialize();
    }

    /**
     * Decrements the value of month. If the month is january, the new month is sat
     * to
     * december and the year is decremented
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
     * Increments the value of month. If the month is december, the new month is sat
     * to january and the year is incremented.
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
     * Method that displays popup with toString-content to a workout when a workout
     * is selected in calendar.
     * 
     * @param workout IWorkout to be shown
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
                setFilenames();

            } catch (IOException e) {
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
        } catch (IOException e) {
            feedbackLoad.setText("Choose a file!");
        } catch (RuntimeException e) {
            feedbackLoad.setText("Error, improper values!");
        }

        filenameSave.setValue(file);
        feedbackSave.setText("");
    }

    /**
     * Method that shows alertbox with header and appropriate errormessage
     * 
     * @param alertType AlertType of the error
     * @param header    String header to be displayed at alertbox
     * @param message   String errormassage
     */
    private void showAlert(AlertType alertType, String header, String message) {
        Alert alert = new Alert(alertType);
        alert.setHeaderText(header);
        alert.setContentText(message);
        alert.showAndWait();
    }

}
