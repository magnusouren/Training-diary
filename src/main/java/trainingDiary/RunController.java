package trainingDiary;

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
    ChoiceBox<Integer> rating;

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

    }

    public void quit() {
        Stage stage = (Stage) cancel.getScene().getWindow();
        stage.close();
    }
}
