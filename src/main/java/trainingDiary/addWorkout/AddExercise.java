package trainingDiary.addWorkout;

import java.util.List;

import javafx.css.PseudoClass;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import trainingDiary.Exercise;

public class AddExercise {

    private boolean validationStatus;

    private Exercise exercise = new Exercise();

    public boolean validate(TextField name, TextField weight, List<TextField> reps) {

        validationStatus = true;

        setName(name);
        setWeight(weight);

        for (TextField field : reps) {
            if (!field.getText().equals(""))
                addRep(field);
        }

        if (!validationStatus) {
            exercise = null;
            return false;
        }

        return true;

    }

    /**
     * Returnerer Workout som er initilaisert med gyldige verdier
     * 
     * @return Exercise
     */
    public Exercise getExercise() {
        return exercise;
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
            exercise.setWeigth(weightVal);
            styleInput(weight, true);
        } catch (NumberFormatException e) {
            if (weight.getText().equals("")) {
                exercise.setWeigth(0);
                styleInput(weight, true);
            } else {
                styleInput(weight, false);
            }
        } catch (IllegalArgumentException e) {
            styleInput(weight, false);
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
        } catch (IllegalArgumentException e) {
            styleInput(rep, false);
        }
    }

}
