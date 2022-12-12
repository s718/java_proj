package com.jpmc.theater;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class LocalDateProvider {
    private static LocalDateProvider instance = null;
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    /**
     * @return make sure to return singleton instance
     */
    public static LocalDateProvider singleton() {
        if (instance == null) {
            instance = new LocalDateProvider();
        }
            return instance;
        }

    public LocalDate currentDate() {
            return LocalDate.now();
    }
    
    public LocalTime currentTime() {
        return LocalTime.now();

    }
    
    public DateTimeFormatter getFormatter() {
        return formatter;

    }
    
}
