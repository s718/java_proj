package com.jpmc.theater;

import org.junit.jupiter.api.Test;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MovieTests {
    @Test
    void specialMovie() {
        Movie movie1 = new Movie("Spider-Man: No Way Home", Duration.ofMinutes(90), 12.5, 1);       
        assertTrue(movie1.hasSpecialCode(), "Should have special code");
        
        Movie movie2 = new Movie("Spider-Man: No Way Home", Duration.ofMinutes(90), 12.5, 0);       
        assertFalse(movie2.hasSpecialCode(), "Should not have special code");        
    }
    
    @Test
    void testEquals() {
        Movie movie1 = new Movie("Spider-Man: No Way Home", Duration.ofMinutes(90), 12.5, 1);
        Movie movie2 = new Movie("Spider-Man: No Way Home", Duration.ofMinutes(90), 12.5, 1);       
        assertEquals(movie1, movie2);
    }
}
