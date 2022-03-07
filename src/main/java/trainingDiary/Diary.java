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
        Workout run1 = new Run(now, 60, 10, '4', "Test", 150, 180);
        Workout run2 = new Run(now, 80, 10, '4', "Test", 150, 180);
        Workout styrke = new Strength(now, 50, '3', "Braaa");

        Diary diary = new Diary();
        diary.addWorkout(run1);
        diary.addWorkout(run2);
        diary.addWorkout(styrke);

        System.out.println(diary);
    }
}
