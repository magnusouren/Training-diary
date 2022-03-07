package trainingDiary;

import java.util.ArrayList;
import java.util.List;

public class Exercise {

    String name;
    int sets;
    List<Integer> reps = new ArrayList<>();

    public Exercise(String name) {
        this.name = name;
    }

    void setSets(int sets) {
        this.sets = sets;
    }

    void addRep(int rep) {
        if (reps.size() < sets)
            reps.add(rep);
        else
            throw new IllegalArgumentException("To many sets added");
    }
}
