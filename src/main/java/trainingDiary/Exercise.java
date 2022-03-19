package trainingDiary;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

public class Exercise {

    String name;
    List<Integer> reps = new ArrayList<>();

    /**
     * Konstruktør til å sette en øvelse
     * 
     * @param name String øvelsesnavn
     */
    public Exercise(String name) {
        this.name = name;
    }

    /**
     * Tar inn tallverdi som representerer antall repetisjoner på det settet. Unntak
     * hvis antall repetisjoner registrert er større enn tillatt
     * 
     * @param rep Int antall repetisjoner
     */
    void addRep(List<Integer> reps) {
        for (Integer rep : reps) {
            if (rep <= 0) {
                throw new IllegalArgumentException("Ugyldig verdi på rep");
            } else {
                this.reps.add(rep);
            }
        }
    }

    public String getName() {
        return name;
    }

    public int getSets() {
        return reps.size();
    }

    public List<Integer> getReps() {
        return new ArrayList<>(reps);
    }

    @Override
    public String toString() {
        String res = name + ": ";

        if (new HashSet<Integer>(reps).size() == 1) {
            res += reps.size() + " x " + reps.get(0);
        } else {
            res += reps.stream()
                    .map(r -> String.valueOf(r))
                    .collect(Collectors.joining(", "));
        }

        return res;
    }

}
