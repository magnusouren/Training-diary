package trainingDiary;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Diary {

    List<Workout> diary = new ArrayList<>();

    void addWorkout(Workout workout) {
        diary.add(workout);
    }

    @Override
    public String toString() {
        String res = "";
        for (Workout workout : diary) {
            res += workout + "\n";
        }
        return res;
    }
}
