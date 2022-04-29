package trainingDiary.validation;

import java.util.List;

import javafx.scene.control.TextField;
import trainingDiary.Exercise;

public class ValidateExercise {

    private boolean validationStatus = true;
    private boolean containsRep = false;
    private String message = "";

    private Exercise exercise = new Exercise();

    public boolean isValid() {
        return validationStatus;
    }

    public ValidateExercise() {
    }

    public ValidateExercise(TextField name, TextField weight, List<TextField> reps) {

        try {
            valName(name.getText());
            Commons.styleInput(name, true);
        } catch (IllegalArgumentException e) {
            Commons.styleInput(name, false);
            message += e.getLocalizedMessage();
            validationStatus = false;
        }

        try {
            valWeight(weight.getText());
            Commons.styleInput(weight, true);
        } catch (Exception e) {
            Commons.styleInput(weight, false);
            message += e.getLocalizedMessage();
            validationStatus = false;
        }

        for (TextField rep : reps) {
            if (!rep.getText().isEmpty()) {
                containsRep = true;
                try {
                    valRep(rep.getText());
                    Commons.styleInput(rep, true);
                }

                catch (Exception e) {
                    Commons.styleInput(rep, false);
                    message += e.getLocalizedMessage();
                    validationStatus = false;
                }
            }
        }
        if (!containsRep) {
            validationStatus = false;
            message += "Illegal sets, must contain at least one set\n";
        }

    }

    /**
     * Validates name and sets name if value is valid.
     * Sets correct errormessage if name is invalid.
     * If name is invalid, validattionstatus is set to false.
     * 
     * @param name String with name of exercise as value
     * @return true/false if name is valid/invalid
     * @throws IllegalArgumentException if name has illegal format
     * 
     */
    void valName(String name) throws IllegalArgumentException {
        exercise.setName(name);
    }

    /**
     * Validates weight and sets weight if it's value is valid.
     * If field is empty, weight is sat to 0 because an exercise don't need to have
     * a weight
     * 
     * @param weight String with weight of exercise as value
     * @return true/false if weight is valid/invalid
     * @throws NumberFormatException     if weight is not a number
     * @throws IllegalArgumentExceptioin if weight is not in the interval [0,300]
     */
    void valWeight(String weight) throws NumberFormatException, IllegalArgumentException {
        try {
            exercise.setWeight(Integer.parseInt(weight));
        } catch (NumberFormatException e) {
            if (weight.isBlank()) {
                exercise.setWeight(0);
            } else {
                throw new NumberFormatException("Illegal weight, weight must be a number or empty!\n");
            }
        }
    }

    /**
     * Sets rep on exercise if it has a valid value
     * 
     * @param weight String with integer value of weight
     * @return true/false if rep is valid/invalid
     * @throws NumberFormatException    if rep is not a number
     * @throws IllegalArgumentException if rep is greater than 1000 or less or equal
     *                                  than 0
     */
    void valRep(String rep) throws NumberFormatException, IllegalArgumentException {
        try {
            exercise.addRep(Integer.parseInt(rep));
        } catch (NumberFormatException e) {
            throw new NumberFormatException("Invalid set, must be numbers only\n");
        }
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
