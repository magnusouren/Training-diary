package trainingDiary.addWorkout;

import java.util.List;

import javafx.css.PseudoClass;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import trainingDiary.Exercise;

public class AddExercise {

    private boolean validationStatus;
    private String message;

    private Exercise exercise = new Exercise();

    public boolean validate(TextField name, TextField weight, List<TextField> reps) {

        validationStatus = true;
        message = "";

        setName(name);
        setWeight(weight);

        boolean containsRep = false;

        for (TextField field : reps) {
            if (!field.getText().equals("")) {
                addRep(field);
                containsRep = true;
            }
        }

        if (!containsRep) {
            message += "Invalid sets, must contain at least one set";
            validationStatus = containsRep;
        }

        return validationStatus;

    }

    /**
     * Returnerer Workout som er initilaisert med gyldige verdier
     * 
     * @return Exercise
     */
    public Exercise getExercise() {
        return exercise;
    }

    public String getMessage() {
        return message;
    }

    /**
     * Stilsetter Input-field i forhold til gyldigheten på input. Setter validation
     * false om ugyldig verdi.
     * 
     * @param field  Node input-felt
     * @param status boolean gyldig/ugyldig verdi på feltet
     */
    private void styleInput(Node field, boolean status) {

        final PseudoClass errorClass = PseudoClass.getPseudoClass("error");
        final PseudoClass validClass = PseudoClass.getPseudoClass("valid");

        field.pseudoClassStateChanged(validClass, status);
        field.pseudoClassStateChanged(errorClass, !status);
        if (validationStatus == true)
            validationStatus = status;

    }

    /**
     * Validerer name og stilsetter input-feltet etter gylidhet på input
     * 
     * @param name TextField med navn
     */
    private void setName(TextField name) {
        try {
            String nameVal = name.getText();
            exercise.setName(nameVal);

            styleInput(name, true);
        } catch (IllegalArgumentException e) {
            styleInput(name, false);
            message += e.getLocalizedMessage();
        }
    }

    /**
     * Setter weight på øvelsen, stilsetter weight etter gyldighet
     * 
     * @param weight TextField med nummerverdi for vekt
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
            }
        } catch (IllegalArgumentException e) {
            styleInput(weight, false);
            message += e.getLocalizedMessage() + "\n";
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
