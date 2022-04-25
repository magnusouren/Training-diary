package trainingDiary.fileManagement;

import java.io.IOException;

import trainingDiary.Diary;

public interface IfileManager {
    /**
     * Writes object to file. Each object is written in one line
     * 
     * @param filename String name on file to be saved/created
     * @param diary    Diary that should be saved
     * @throws IOException      throws if error occurs while opening or
     *                          creating the file
     * @throws RuntimeException throws if errors occurs while reading values
     */
    public void write(String filename, Diary diary) throws IOException;

    /**
     * Reads from existing file. Reading depends on instance of workout.
     * 
     * @param filename String filename to be read from
     * @return Diary with content from file
     * @throws IOException throws if error occurs if file don't exists or
     *                     problems occurs while opening file
     */
    public Diary read(String filename) throws IOException;
}
