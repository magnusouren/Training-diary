package trainingDiary.addWorkout;

import java.util.List;

import javafx.css.PseudoClass;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import trainingDiary.Exercise;

public class AddExercise extends Commons {

    private boolean validationStatus = true;
    private String message = "";

    private Exercise exercise = new Exercise();

    /**
     * Calls on methods that validates and sets values that need to be valid to
     * initialize a valid exercise-object
     * 
     * @param name   String name on exercise
     * @param weight int weight on exercise
     * @param reps   List<TextField> with values of reps
     * @return boolean validationStatus
     */
    public boolean validate(TextField name, TextField weight, List<TextField> reps) {

        setName(name);
        setWeight(weight);

        boolean containsRep = false;
        for (TextField field : reps) {
            if (!field.getText().isBlank()) {
                addRep(field);
                containsRep = true;
            }
        }

        if (!containsRep) {
            message += "Invalid sets, must contain at least one set";
            validationStatus = false;
        }

        return validationStatus;

    }

    /**
     * @return Exercise with valid fields only
     */
    public Exercise getExercise() {
        return exercise;
    }

    /**
     * @return String with errormessages
     */
    public String getMessage() {
        return message;
    }

    /**
     * Validates name and sets name if value is valid.
     * Styles inutfield acoording to validity.
     * Sets correct errormessage if name is invalid.
     * If name is invalid, validattionstatus is set to false.
     * 
     * @param name
     */
    private void setName(TextField name) {
        try {
            String nameVal = name.getText();
            exercise.setName(nameVal);

            styleInput(name, true);
        } catch (IllegalArgumentException e) {
            styleInput(name, false);
            message += e.getLocalizedMessage();
            validationStatus = false;
        }
    }

    /**
     * Validates weight and sets weight if it value is valid.
     * 
     * @param weight
     */
    private void setWeight(TextField weight) {
        try {
            int weightVal = Integer.parseInt(weight.getText());
            exercise.setWeight(weightVal);
            styleInput(weight, true);
            return;
        } catch (NumberFormatException e) {
            if (weight.getText().isBlank()) {
                exercise.setWeight(0);
                styleInput(weight, true);
            } else {
                styleInput(weight, false);
                message += "Illegal weight, must be a number!";
                validationStatus = false;
            }
        } catch (IllegalArgumentException e) {
            styleInput(weight, false);
            message += e.getLocalizedMessage() + "\n";
            validationStatus = false;
        }
    }

    /**
     * Setter weight på øvelsen, stilsetter weight etter gyldighet
     * 
     * @param weight TextField med nummerverdi for vekt
     */
    private void addRep(TextField rep) {
        try {
            int repVal = Integer.parseInt(rep.getText());
            exercise.addRep(repVal);
            styleInput(rep, true);
            return;
        } catch (NumberFormatException e) {
            message += "Invalid set, must consists of numbers only";
        } catch (IllegalArgumentException e) {
            message += e.getLocalizedMessage() + "\n";
        }
        styleInput(rep, false);

    }
}
