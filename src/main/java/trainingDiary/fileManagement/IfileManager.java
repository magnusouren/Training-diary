package trainingDiary.fileManagement;

import java.io.FileNotFoundException;

import trainingDiary.Diary;

public interface IfileManager {
    public void write(String filename, Diary diary) throws FileNotFoundException;

    public Diary read(String filename) throws FileNotFoundException;
}
