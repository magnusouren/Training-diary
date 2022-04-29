package trainingDiary.validation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import trainingDiary.IWorkout;
import trainingDiary.Strength;

public class CommonsTest {

    IWorkout workout;
    Commons commons;

    @BeforeEach
    public void setup() {
        workout = new Strength();
        commons = new Commons();
    }

    @Test
    public void testValDate() throws Exception {
        LocalDate date = LocalDate.now().minusMonths(1);
        LocalTime lTime = LocalTime.of(12, 00);
        String time = "12:00";

        while (date.isBefore(LocalDate.now())) {
            Commons.valDate(date, time, workout);
            assertEquals(LocalDateTime.of(date, lTime), workout.getDate(), "Dates should be eqaul");
            date = date.plusDays(1);
        }
    }

    @Test
    public void testValDateTime() throws Exception {
        LocalDate date = LocalDate.of(2022, 01, 01);
        String time;
        LocalTime lTime;

        for (int i = 0; i < 24; i++) {
            for (int j = 0; j < 60; j++) {
                lTime = LocalTime.of(i, j);
                time = String.valueOf(i) + ":" + String.valueOf(j);
                Commons.valDate(date, time, workout);
                assertEquals(LocalDateTime.of(date, lTime), workout.getDate(), "Time should be " + i + ":" + j);
            }
        }

    }

    @Test
    public void testValDateException() {

        assertThrows(IllegalArgumentException.class, () -> {
            Commons.valDate(LocalDate.now().plusDays(1), "12:00", workout);
        }, "Exception should be thrown when setting date in the future");

        assertThrows(IllegalArgumentException.class, () -> {
            String time = LocalTime.now().plusMinutes(1).format(DateTimeFormatter.ofPattern("HH:mm"));
            Commons.valDate(LocalDate.now(), time, workout);
        }, "Exception should be thrown when setting date in the future");

        assertThrows(NullPointerException.class, () -> {
            Commons.valDate(LocalDate.now().plusDays(1), null, workout);
        }, "Exception should be thrown when setting date with illegal time");

        assertThrows(NullPointerException.class, () -> {
            Commons.valDate(null, "12:00", workout);
        }, "Exception should be thrown when setting date equals null");

    }

    @Test
    public void testValDateTimeException() {

        LocalDate date = LocalDate.now().minusDays(1);

        assertThrows(IllegalArgumentException.class, () -> {
            Commons.valDate(date, "10.00", workout);
        }, "Exception should be thrown when time has illegal format");

        assertThrows(ArrayIndexOutOfBoundsException.class, () -> {
            Commons.valDate(date, "1000", workout);
        }, "Exception should be thrown when time has illegal format");

        assertThrows(IllegalArgumentException.class, () -> {
            Commons.valDate(date, "-10:00", workout);
        }, "Exception should be thrown when time has illegal format");

        assertThrows(IllegalArgumentException.class, () -> {
            Commons.valDate(date, "10:-10", workout);
        }, "Exception should be thrown when time has illegal format");

        assertThrows(IllegalArgumentException.class, () -> {
            Commons.valDate(date, "-10:-10", workout);
        }, "Exception should be thrown when time has illegal format");

        assertThrows(DateTimeException.class, () -> {
            Commons.valDate(date, "12:90", workout);
        }, "Exception should be thrown when time has illegal format");

        assertThrows(DateTimeException.class, () -> {
            Commons.valDate(date, "00:90", workout);
        }, "Exception should be thrown when time has illegal format");

    }

    @Test
    public void testValDuration() throws Exception {

        String duration;

        // Tests duration on the format hh:mm, h:mm, hh:m and h:m
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 60; j++) {
                if (i == 0 && j == 0) {
                    continue;
                }
                duration = String.valueOf(i) + ":" + String.valueOf(j);
                Commons.valDuration(duration, workout);
                assertEquals(i * 60 + j, workout.getDuration(), "Durations should be equal when sat");
            }
        }

        // Test duraions on the format hh:mm only
        String[] twoDigits = { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09" };
        String hh;
        String mm;

        for (int i = 0; i < 5; i++) {
            if (i < 10)
                hh = twoDigits[i];
            else
                hh = String.valueOf(i);
            for (int j = 0; j < 60; j++) {
                if (j < 10) {
                    if (j == 0 && i == 0)
                        continue;
                    mm = twoDigits[j];
                } else
                    mm = String.valueOf(j);
                duration = hh + ":" + mm;
                Commons.valDuration(duration, workout);
                assertEquals((i * 60) + j, workout.getDuration(), "Durations should be equals when sat");
            }
        }

        // Test 5:00, should be longest duration to be legal

        duration = "05:00";
        Commons.valDuration(duration, workout);
        assertEquals(300, workout.getDuration(), "5:00 should be a legal duration");

    }

    @Test
    public void testValDurationExceptions() {

        assertThrows(IllegalArgumentException.class, () -> {
            Commons.valDuration("00:00", workout);
        }, "Shouldn't be allowed to set duration to 0 minutes");

        assertThrows(IllegalArgumentException.class, () -> {
            Commons.valDuration("-10:00", workout);
        }, "Negative values shouldn't be allowed to be a duration");

        assertThrows(IllegalArgumentException.class, () -> {
            Commons.valDuration("05:-10", workout);
        }, "Negative values shouldn't be allowed to be a duration");

        assertThrows(IllegalArgumentException.class, () -> {
            Commons.valDuration("-10:-10", workout);
        }, "Negative values shouldn't be allowed to be a duration");

        assertThrows(NullPointerException.class, () -> {
            Commons.valDuration(null, workout);
        }, "Null shouldn't be allowed to be a duration");

        assertThrows(IllegalArgumentException.class, () -> {
            Commons.valDuration("05:01", workout);
        }, "Durations greater than 5:00 shouldn't be allowed to be set");

        assertThrows(IllegalArgumentException.class, () -> {
            Commons.valDuration("23:59", workout);
        }, "Durations greater than 5:00 shouldn't be allowed to be set");

        assertThrows(DateTimeException.class, () -> {
            Commons.valDuration("00:90", workout);
        }, "Durations with minutes greater than 59 shouldnt be allowed to be set");

        assertThrows(ArrayIndexOutOfBoundsException.class, () -> {
            Commons.valDuration("40", workout);
        }, "Durations not on the format hh:mm should be illegal");

        assertThrows(ArrayIndexOutOfBoundsException.class, () -> {
            Commons.valDuration("90", workout);
        }, "Durations not on the format hh:mm should be illegal");

        assertThrows(NumberFormatException.class, () -> {
            Commons.valDuration("4.00", workout);
        }, "Durations not on the format hh:mm should be illegal");

        assertThrows(NumberFormatException.class, () -> {
            Commons.valDuration("ten", workout);
        }, "Durations not on the format hh:mm should be illegal");

    }

    @Test
    public void testRating() throws Exception {
        String[] ratings = { "1", "2", "3", "4", "5", "6" };
        for (String rating : ratings) {
            Commons.valRating(rating, workout);
            assertEquals(rating.charAt(0), workout.getRating(), "Workout should be allowed to get value from 1-6");
        }
    }

    @Test
    public void testRatingException() {
        assertThrows(IllegalArgumentException.class, () -> {
            Commons.valRating("-- Set rating --", workout);
        }, "Exception should be thrown if no rating was chosen");
    }

}
