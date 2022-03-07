package trainingDiary;

import java.util.ArrayList;
import java.util.List;

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
    void addRep(int rep) {
        if (rep < 0)
            throw new IllegalArgumentException("Rep must be greater than 0");
        reps.add(rep);
    }

    public String getName() {
        return name;
    }

    public int getSets() {
        return reps.size();
    }

    public List<Integer> getReps() {
        return reps;
    }

}
