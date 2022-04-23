package trainingDiary.validation;

import trainingDiary.Exercise;

public class ValidateExercise extends Commons {

    private boolean validationStatus = true;
    private String message = "";

    private Exercise exercise = new Exercise();

    public boolean isValid() {
        return validationStatus;
    }

    /**
     * Validates name and sets name if value is valid.
     * Sets correct errormessage if name is invalid.
     * If name is invalid, validattionstatus is set to false.
     * 
     * @param name String with name of exercise as value
     * @return true/false if name is valid/invalid
     */
    public boolean valName(String name) {
        try {
            String nameVal = name;
            exercise.setName(nameVal);
            return true;
        } catch (IllegalArgumentException e) {
            message += e.getLocalizedMessage();
            validationStatus = false;
        }
        return false;
    }

    /**
     * Validates weight and sets weight if it's value is valid.
     * If field is empty, weight is sat to 0 because an exercise don't need to have
     * a weight
     * 
     * @param weight String with weight of exercise as value
     * @return true/false if weight is valid/invalid
     */
    public boolean valWeight(String weight) {
        try {
            int weightVal = Integer.parseInt(weight);
            exercise.setWeight(weightVal);
            return true;
        } catch (NumberFormatException e) {
            if (weight.isBlank()) {
                exercise.setWeight(0);
                return true;
            } else {
                message += "Illegal weight, weight must be a number!\n";
            }
        } catch (IllegalArgumentException e) {
            message += e.getLocalizedMessage();
        }
        validationStatus = false;
        return false;

    }

    /**
     * Sets rep on exercise if it has a valid value
     * 
     * @param weight String with integer value of weight
     * @return true/false if rep is valid/invalid
     */
    public boolean valRep(String rep) {
        try {
            int repVal = Integer.parseInt(rep);
            exercise.addRep(repVal);
            return true;
        } catch (NumberFormatException e) {
            message += "Invalid set, must be numbers only\n";
        } catch (IllegalArgumentException e) {
            message += e.getLocalizedMessage();
        }
        validationStatus = false;
        return false;
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
