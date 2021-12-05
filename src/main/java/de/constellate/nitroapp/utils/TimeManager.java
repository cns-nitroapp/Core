package de.constellate.nitroapp.utils;

import java.time.DayOfWeek;
import java.time.LocalDate;

public class TimeManager {

    public static int getDayNumber() {
        LocalDate currentDate = LocalDate.now();
        DayOfWeek day = currentDate.getDayOfWeek();
        return day.getValue();
    }

    public static boolean isWeekend() {
        return getDayNumber() == 6 || getDayNumber() == 7;
    }

}
