package trainingDiary.validation;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public class WorkoutValidate {

    /**
     * Trenger mer her
     * 
     * @param date
     */
    public void ValidateDate(LocalDateTime date) {

        LocalDateTime today = LocalDateTime.now();

        if (date.isAfter(today)) {
            throw new IllegalArgumentException("Illegal date, date can not be in the future");
        }

    }

    /**
     * Valideringsmetode som sjekker om varighet på økten er gyldig. Satt makstid er
     * bestemt til 5 timer. Utløser unntak hvis ugyldig.
     * 
     * @param duration int varighet i minutter på økt.
     */
    public void validateDuration(int duration) {
        if (duration <= 0)
            throw new IllegalArgumentException("Illegal duration, must be greater than 0:00");
        if (duration > 300)
            throw new IllegalArgumentException("Illegal duration, must be less than 5:00");
    }

    /**
     * Tar inn en rating og validerer om denne er i en liste med gyldige verdier.
     * Utløser unntak hvis ikke
     * 
     * @param rating char rating som skal være tallverdi fra 1-6
     */
    public void validateRating(char rating) {
        Collection<Character> values = new ArrayList<>();
        values.addAll(Arrays.asList('1', '2', '3', '4', '5', '6'));

        if (!values.contains(rating))
            throw new IllegalArgumentException("Illegal rating, pick a number in the interval [1-6]\n");
    }
}
