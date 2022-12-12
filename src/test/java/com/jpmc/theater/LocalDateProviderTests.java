package com.jpmc.theater;

import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;

public class LocalDateProviderTests {
    @Test
    void makeSureCurrentTime() {
        System.out.println("current time is - " + LocalDateProvider.singleton().currentTime());
    }
    
    @Test
    void makeSureCurrentDate() {
        System.out.println("current date is - " + LocalDateProvider.singleton().currentDate());
    }
    
    @Test
    void testGetFormatter() {
    	LocalDateTime now = LocalDateTime.of(LocalDateProvider.singleton().currentDate(), 
    			                             LocalDateProvider.singleton().currentTime());    
    	System.out.println("Formatted date time is - " + 
    			                             now.format(LocalDateProvider.singleton().getFormatter()));
    }
}
