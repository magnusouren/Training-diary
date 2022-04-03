package trainingDiary;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Exercise {

    private String name;
    private int weight;
    private List<Integer> reps = new ArrayList<>();

    public Exercise() {
    }

    public void setName(String name) {
        if (name.length() == 0)
            throw new IllegalArgumentException("Ugyldig verdi");
        this.name = name;
    }

    public void setWeigth(int weigth) {
        if (weigth < 0)
            throw new IllegalArgumentException("Ugyldig verdi");
        if (weigth > 300)
            throw new IllegalArgumentException("Ugyldig verdi");
        this.weight = weigth;
    }

    /**
     * Tar inn tallverdi som representerer antall repetisjoner på det settet. Unntak
     * hvis antall repetisjoner registrert er større enn tillatt
     * 
     * @param rep Int antall repetisjoner
     */
    public void addRep(int rep) {
        if (rep <= 0) {
            throw new IllegalArgumentException("Ugyldig verdi på rep");
        } else {
            this.reps.add(rep);
        }

    }

    public String getName() {
        return name;
    }

    public List<Integer> getReps() {
        return new ArrayList<>(reps);
    }

    public int getWeight() {
        return weight;
    }

    @Override
    public String toString() {
        String res = name + ": ";

        if (reps.stream().distinct().toList().size() == 1) {
            res += reps.size() + " x " + reps.get(0);
        } else {
            res += reps.stream()
                    .map(r -> String.valueOf(r))
                    .collect(Collectors.joining(", "));
        }
        if (weight != 0)
            res += " - " + weight + "kg";

        return res;
    }

}
