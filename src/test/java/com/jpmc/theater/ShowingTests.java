package com.jpmc.theater;


import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class ShowingTests {
	
	@Test
    void testGetMovie() {
		 Movie movie1 = new Movie("Spider-Man: No Way Home", Duration.ofMinutes(90), 10, 1);
		 Showing showing = new Showing(movie1, 5, LocalDateTime.of(LocalDate.of(2022, 12, 1), LocalTime.of(4, 10)));
	       		 
		 assertEquals(movie1, showing.getMovie(), "Showing should have same movie as what was passed.");	    
	}

	@Test
    void testGetStartTime() {
		 Movie movie1 = new Movie("Spider-Man: No Way Home", Duration.ofMinutes(90), 10, 1);
		 Showing showing = new Showing(movie1, 5, LocalDateTime.of(LocalDate.of(2022, 12, 1), LocalTime.of(4, 10)));

		 assertEquals(LocalDateTime.of(LocalDate.of(2022, 12, 1), LocalTime.of(4, 10)), 
				         showing.getStartTime(), "Showing should have same time as 12/1/2022 4:10");
	}

	@Test
    void testIsSequence() {
		 Movie movie1 = new Movie("Spider-Man: No Way Home", Duration.ofMinutes(90), 10, 1);
		 Showing showing = new Showing(movie1, 5, LocalDateTime.of(LocalDate.of(2022, 12, 1), LocalTime.of(4, 10)));
		
		 assertTrue(showing.isSequence(5), "Showing sequence 5 should be true.");
		 assertFalse(showing.isSequence(1), "Showing sequence 1 should be false.");
	}


	@Test
    void testGetSequenceOfTheDay() {
		 Movie movie1 = new Movie("Spider-Man: No Way Home", Duration.ofMinutes(90), 10, 1);
		 Showing showing = new Showing(movie1, 5, LocalDateTime.of(LocalDate.of(2022, 12, 1), LocalTime.of(4, 10)));

		 assertEquals(5, showing.getSequenceOfTheDay(), "Sequence of the day should be 5.");
		 assertNotEquals(1, showing.getSequenceOfTheDay(), "Sequence of the day should not be equal to 1.");
	}

	    
    @Test
    void specialMovieWith20PercentDiscount() {
        Movie movie1 = new Movie("Spider-Man: No Way Home", Duration.ofMinutes(90), 10, 1);
        Showing showing = new Showing(movie1, 5, LocalDateTime.of(LocalDate.of(2022, 12, 1), LocalTime.of(18, 10)));
        // Special code gives 20% discount
        assertEquals(8, showing.getFee(), "Showing should have 20% discount from special code.");       
    }
    
    @Test
    void firstShowOfTheDayDiscount() {
        Movie movie1 = new Movie("Spider-Man: No Way Home", Duration.ofMinutes(90), 10, 0);
        Showing showing = new Showing(movie1, 1, LocalDateTime.of(LocalDate.of(2022, 12, 1), LocalTime.of(10, 30)));
        // First show gets $3 discount
        assertEquals(7, showing.getFee(), "Showing should get $3 discount for 1st sequence of the day.");        
    }
    
    @Test
    void secondShowShowOfTheDayDiscount() {
        Movie movie1 = new Movie("Spider-Man: No Way Home", Duration.ofMinutes(90), 10, 0);
        Showing showing = new Showing(movie1, 2, LocalDateTime.of(LocalDate.of(2022, 12, 1), LocalTime.of(17, 10)));
        // Second show gets $2 discount
        assertEquals(8, showing.getFee(), "Showing should get $2 discount for 2nd sequence of the day.");
    }
    
    @Test
    void daySevenDiscount() {
        Movie movie1 = new Movie("Spider-Man: No Way Home", Duration.ofMinutes(90), 10, 0);
        Showing showing = new Showing(movie1, 3, LocalDateTime.of(LocalDate.of(2022, 12, 7), LocalTime.of(20, 10)));
        // Day 7 gets $1 discount
        assertEquals(9, showing.getFee(), "Showing should get $1 discount for 7th day of the month.");
    }
    
    
    @Test
    void offPeakHourWith25PercentDiscount() {
        Movie movie1 = new Movie("Spider-Man: No Way Home", Duration.ofMinutes(90), 10, 0);
        Showing showing = new Showing(movie1, 5, LocalDateTime.of(LocalDate.of(2022, 12, 1), LocalTime.of(15, 10)));
        // Off peak hour 25% discount
        assertEquals(7.5, showing.getFee(), "Showing should get 25% discount for off peak hour (start time 3:10 pm.");  
        
        Movie movie2 = new Movie("Spider-Man: No Way Home", Duration.ofMinutes(90), 10, 0);
        Showing showing2 = new Showing(movie2, 4, LocalDateTime.of(LocalDate.of(2022, 12, 1), LocalTime.of(11, 0)));
        // Off peak hour 25% discount
        assertEquals(7.5, showing2.getFee(), "Showing should get 25% discount for off peak hour (start time 11 am.");  
    }
    @Test
    void regularHourWithoutDiscount() {               
        Movie movie1 = new Movie("Spider-Man: No Way Home", Duration.ofMinutes(90), 10, 0);
        Showing showing2 = new Showing(movie1, 4, LocalDateTime.of(LocalDate.of(2022, 12, 1), LocalTime.of(17, 0)));
        
        assertEquals(10, showing2.getFee(), "No discount should be applied. Same as the ticket price.");  
    }
    
    @Test
    void getMaxDiscount() {  
    	 Movie movie1 = new Movie("Spider-Man: No Way Home", Duration.ofMinutes(90), 10, 1);
         Showing showing1 = new Showing(movie1, 2, LocalDateTime.of(LocalDate.of(2022, 12, 1), LocalTime.of(11, 0)));
         // Off peak hour discount %25 is the max discount than special code(20%) and second show discount($2)
         assertEquals(7.5, showing1.getFee(), "Max discount should be $2.5 for off peak hour showing."); 
         
        Movie movie2 = new Movie("Spider-Man: No Way Home", Duration.ofMinutes(90), 10, 1);
        Showing showing2 = new Showing(movie2, 1, LocalDateTime.of(LocalDate.of(2022, 12, 1), LocalTime.of(11, 0)));
        // Fist show $3 is the max discount
        assertEquals(7, showing2.getFee(), "Max discount should be $3 for being a first show of the day.");  
        
        Movie movie3 = new Movie("Spider-Man: No Way Home", Duration.ofMinutes(90), 10, 1);
        Showing showing3 = new Showing(movie3, 2, LocalDateTime.of(LocalDate.of(2022, 12, 1), LocalTime.of(11, 0)));
        // Off peak hour discount %25 is more discount than special code(20%) and second show discount($2)
        assertEquals(7.5, showing3.getFee(), "Max discount should be %25 from off peak hour discount."); 
    }
}

