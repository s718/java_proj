package com.jpmc.theater;

import java.time.LocalDate;
import java.time.LocalTime;

public class LocalDateProvider {
    private static LocalDateProvider instance = null;

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
}
