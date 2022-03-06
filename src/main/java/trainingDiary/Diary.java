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

    public static void main(String[] args) {
        Date now = new Date();
        Run run1 = new Run(now, 60, '5', "Braaa økt");
        Run run2 = new Run(now, 80, '2', "Dårlig økt");

        Diary diary = new Diary();
        diary.addWorkout(run1);
        diary.addWorkout(run2);

        System.out.println(diary);
    }
}