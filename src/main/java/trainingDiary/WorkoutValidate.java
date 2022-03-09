package trainingDiary;

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
    void ValidateDate(LocalDateTime date) {

        LocalDateTime today = LocalDateTime.now();
        if (today.isAfter(date)) {
            throw new IllegalArgumentException("Date could not be in the future");
        }

    }

    /**
     * Valideringsmetode som sjekker om varighet på økten er gyldig. Satt makstid er
     * bestemt til 5 timer. Utløser unntak hvis ugyldig.
     * 
     * @param duration int varighet i minutter på økt.
     */
    void validateDuration(int duration) {
        if (duration < 0)
            throw new IllegalArgumentException("Duration must be greater than 0");
        if (duration > 300)
            throw new IllegalArgumentException("A workout canot be longer than 5 hours");
    }

    /**
     * Tar inn en rating og validerer om denne er i en liste med gyldige verdier.
     * Utløser unntak hvis ikke
     * 
     * @param rating char rating som skal være tallverdi fra 1-6
     */
    void validateRating(char rating) {
        Collection<Character> values = new ArrayList<>();
        values.addAll(Arrays.asList('1', '2', '3', '4', '5', '6'));

        if (!values.contains(rating))
            throw new IllegalArgumentException("Illegal rating, must be in the interval 1-6");
    }
}
