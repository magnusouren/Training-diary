package trainingDiary.fileManagement;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import trainingDiary.Diary;
import trainingDiary.Exercise;
import trainingDiary.IWorkout;
import trainingDiary.Run;
import trainingDiary.Strength;

public class TxtFileTest {

    Diary diary;
    Diary diary2;
    Run run;
    Strength strength;
    Exercise exercise;

    @BeforeEach
    public void setup() {
        diary = new Diary();
        run = new Run(LocalDateTime.of(2022, 01, 01, 12, 00), 60, 10000, '5', "Great run", 150, 190);
        strength = new Strength(LocalDateTime.of(2022, 01, 02, 12, 00), 90, '4', "Bad strength");
        exercise = new Exercise("Test", 90, 1, 2, 3);

        strength.addExercise(exercise);
        diary.addWorkout(strength);
        diary.addWorkout(run);
        testWrite();

    }

    @AfterEach
    public void deleFile() {
        File myObj = new File("src/main/resources/trainingDiary/saves/test.txt");

        try {
            myObj.delete();
        } catch (SecurityException e) {
            System.out.println("Error, file wasnt saved");
        }

    }

    @Test
    public void testWrite() {
        try {
            IfileManager fManager = new TxtFile();
            fManager.write("test.txt", diary);
            this.diary2 = fManager.read("test.txt");

        } catch (IOException e) {
            fail("Error during file writing");
        }
    }

    @Test
    public void equalsTest() throws IOException {

        Iterator<IWorkout> d1 = diary.getDiary().iterator();
        Iterator<IWorkout> d2 = diary2.getDiary().iterator();

        try {
            while (d1.hasNext()) {
                checkEqual(d1.next(), d2.next());
            }
        } catch (NoSuchElementException e) {
            fail("Diaries should have the same elements");
        }

    }

    private void checkEqual(IWorkout w1, IWorkout w2) {
        assertEquals(w1.getDate(), w2.getDate(), "Dates should be equal");
        assertEquals(w1.getDuration(), w2.getDuration(), "Duration should be equal");
        assertEquals(w1.getRating(), w2.getRating(), "Rating should be equal");
        assertEquals(w1.getComment(), w2.getComment(), "Comments should be equal");

        if (w1 instanceof Run) {
            assertEquals(((Run) w1).getDistance(), ((Run) w2).getDistance(), "Distance should be equal");
            assertEquals(((Run) w1).getAvaerageHeartRate(), ((Run) w2).getAvaerageHeartRate(),
                    "Avg Hr should be equal");
            assertEquals(((Run) w1).getMaxHeartRate(), ((Run) w2).getMaxHeartRate(), "Max Hr should be equal");
        } else if (w1 instanceof Strength) {
            Iterator<Exercise> ex1 = ((Strength) w1).getExercises().iterator();
            Iterator<Exercise> ex2 = ((Strength) w2).getExercises().iterator();

            while (ex1.hasNext()) {
                compareExercise(ex1.next(), ex2.next());
            }

        }
    }

    private void compareExercise(Exercise ex1, Exercise ex2) {
        assertEquals(ex2.getName(), ex2.getName(), "Names should be equal");
        assertEquals(ex2.getWeight(), ex2.getWeight(), "Weight should be equal");
        assertEquals(ex2.getReps(), ex2.getReps(), "Reps should be equal");
    }

    @Test
    public void errorReading() {
        assertThrows(IOException.class, () -> {
            IfileManager fManager = new TxtFile();
            diary2 = fManager.read("invalidFilename.txt");
        }, "Exception should be thrown when filepath doesn't excist");
    }

}
