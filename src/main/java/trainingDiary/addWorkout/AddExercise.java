package trainingDiary.addWorkout;

import java.util.List;

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
    public boolean isValid(TextField name, TextField weight, List<TextField> reps) {

        valName(name);
        valWeight(weight);

        boolean containsRep = false;
        for (TextField field : reps) {
            if (!field.getText().isBlank()) {
                valRep(field);
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
     * Validates name and sets name if value is valid.
     * Styles inutfield acoording to validity.
     * Sets correct errormessage if name is invalid.
     * If name is invalid, validattionstatus is set to false.
     * 
     * @param name TextField with name of exercise as value
     */
    private void valName(TextField name) {
        try {
            String nameVal = name.getText();
            exercise.setName(nameVal);
            styleInput(name, true);
        } catch (IllegalArgumentException e) {
            message += e.getLocalizedMessage();
            validationStatus = false;
            styleInput(name, false);
        }
    }

    /**
     * Validates weight and sets weight if it value is valid.
     * If field is empty, weight is sat to 0 because an exercise don't need to have
     * a weight
     * 
     * @param weight TextField with weight of exercise as value
     */
    private void valWeight(TextField weight) {
        try {
            int weightVal = Integer.parseInt(weight.getText());
            exercise.setWeight(weightVal);
            styleInput(weight, true);
            return;
        } catch (NumberFormatException e) {
            if (weight.getText().isBlank()) {
                exercise.setWeight(0);
                styleInput(weight, true);
                return;
            } else {
                message += "Illegal weight, weight must be a number!\n";
            }
        } catch (IllegalArgumentException e) {
            message += e.getLocalizedMessage();
        }
        validationStatus = false;
        styleInput(weight, false);

    }

    /**
     * Setter weight på øvelsen, stilsetter weight etter gyldighet
     * 
     * @param weight TextField med nummerverdi for vekt
     */
    private void valRep(TextField rep) {
        try {
            int repVal = Integer.parseInt(rep.getText());
            exercise.addRep(repVal);
            styleInput(rep, true);
            return;
        } catch (NumberFormatException e) {
            message += "Invalid set, must be numbers only\n";
        } catch (IllegalArgumentException e) {
            message += e.getLocalizedMessage();
        }
        styleInput(rep, false);

    }

    /**
     * @return Exercise with valid fields only
     */
    public Exercise getExercise() {
        return exercise;
    }

    /**
     * @return String errormessage corresponding to its validity
     */
    public String getMessage() {
        return message;
    }
}
