package trainingDiary;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.css.PseudoClass;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class RunController {

    @FXML
    DatePicker date;

    @FXML
    TextField duration;

    @FXML
    TextField distance;

    @FXML
    ChoiceBox<String> rating;

    @FXML
    TextField maxHr;

    @FXML
    TextField avgHr;

    @FXML
    TextArea comments;

    @FXML
    Button cancel;

    @FXML
    Button save;

    final PseudoClass errorClass = PseudoClass.getPseudoClass("error");
    final PseudoClass validClass = PseudoClass.getPseudoClass("valid");
    WorkoutValidate validator = new WorkoutValidate();

    @FXML
    private void initialize() {

        date.setValue(LocalDate.now());

        ObservableList<String> ratings = FXCollections.observableArrayList("1", "2", "3", "4", "5", "6");
        rating.setItems(ratings);
        rating.setValue("-- Set rating --");

    }

    public void quit() {
        Stage stage = (Stage) cancel.getScene().getWindow();
        stage.close();
    }

    public void save() {
        LocalDate dateVal = date.getValue();
        String durationVal = duration.getText();
        String distanceVal = distance.getText();
        String ratingVal = rating.getValue();
        String maxHrVal = maxHr.getText();
        String avgHrVal = avgHr.getText();
        String commentsVal = comments.getText();

        // validateDate(dateVal);
        validateDuration(durationVal);
        // validateDistance();
        // validateRating();
        // validateMaxHr();
        // validateAvgHr();
        // validateComments();

        System.out.println(
                "Date: " + dateVal + "\n" +
                        "Duration: " + durationVal + "\n" +
                        "Distance: " + distanceVal + "\n" +
                        "Rating: " + ratingVal + "\n" +
                        "Maximum HR: " + maxHrVal + "\n" +
                        "Average HR: " + avgHrVal + "\n" +
                        "Comments: " + commentsVal + "\n");

        // duration.pseudoClassStateChanged(validClass, true);
        // rating.pseudoClassStateChanged(validClass, true);

    }

    /**
     * Gir node klassen "error" og fjerner eventuelt klassen "valid"
     * 
     * @param field JavaFx-element som skal endre stil
     */
    private void invalidInput(Node field) {
        field.pseudoClassStateChanged(validClass, false);
        field.pseudoClassStateChanged(errorClass, true);
    }

    /**
     * Gir node klassen "valid" og fjerner eventuelt klassen "error"
     * 
     * @param field JavaFx-element som skal endre stil
     */
    private void validInput(Node field) {
        field.pseudoClassStateChanged(validClass, true);
        field.pseudoClassStateChanged(errorClass, false);
    }

    /**
     * Validerer duration og stilsetter input-feltet etter gylidhet på input
     * 
     * @param durationVal String med tidverider på formatet (HH:mm)
     */
    private void validateDuration(String durationVal) {
        try {
            String[] values = durationVal.split(":");
            int hours = Integer.valueOf(values[0]);
            int minutes = Integer.valueOf(values[1]);

            validator.validateDuration(hours * 60 + minutes);

            validInput(duration);

        } catch (Exception e) {
            invalidInput(duration);
        }
    }

    private void validateDate(LocalDateTime dateVal) {
        try {
            validator.ValidateDate(dateVal);
        } catch (IllegalArgumentException e) {
            date.pseudoClassStateChanged(errorClass, true);
        }
    }
}
