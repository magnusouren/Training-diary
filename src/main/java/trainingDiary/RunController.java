package trainingDiary;

import java.time.LocalDate;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
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
}
