package com.jpmc.theater;

import org.junit.jupiter.api.Test;
import org.json.simple.parser.ParseException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.ArrayList;

public class TheaterTests {
	 String inputJsonString = "{\"movies\" : [" +
			  "{ \"id\": \"spiderMan\", \"title\": \"Spider-Man: No Way Home\", \"duration\": 90, \"ticket_price\": \"12.5\", \"special_code\": 1 },"+
	          "{ \"id\": \"turningRed\", \"title\": \"Turning Red\", \"duration\": 85, \"ticket_price\": \"11\", \"special_code\": 0 }," +
	          "{ \"id\": \"theBatMan\", \"title\": \"The Batman\", \"duration\": 95, \"ticket_price\": \"9\", \"special_code\": 0 } ],"   +
	         " \"showings\": [" +
	         "{  \"sequence_of_the_day\": 1,     \"movie_id\": \"turningRed\",     \"start_time\": \"09:00\" }," +
	         "{  \"sequence_of_the_day\": 2,     \"movie_id\": \"spiderMan\",     \"start_time\": \"11:00\" }," +
	         "{  \"sequence_of_the_day\": 3,     \"movie_id\": \"theBatMan\",     \"start_time\": \"12:50\" }," +
	         "{  \"sequence_of_the_day\": 4,     \"movie_id\": \"turningRed\",     \"start_time\": \"15:30\" }," +
	         "{  \"sequence_of_the_day\": 5,     \"movie_id\": \"spiderMan\",     \"start_time\": \"16:10\" }," +
	         "{  \"sequence_of_the_day\": 6,     \"movie_id\": \"theBatMan\",     \"start_time\": \"17:50\" }," +
	         "{  \"sequence_of_the_day\": 7,     \"movie_id\": \"turningRed\",     \"start_time\": \"19:30\" }," +
	         "{  \"sequence_of_the_day\": 8,     \"movie_id\": \"spiderMan\",     \"start_time\": \"22:10\" }," +
	         "{  \"sequence_of_the_day\": 9,     \"movie_id\": \"theBatMan\",     \"start_time\": \"23:00\" } ]}"; 

	 String illFormattedInput = "{movies\" : [" +
			  "{ \"id\": \"spiderMan\", \"title\": \"Spider-Man: No Way Home\", \"duration\": 90, \"ticket_price\": \"12.5\", \"special_code\": 1 },"+
	         " \"showings\": [" +
	         "{  \"sequence_of_the_day\": 1,     \"movie_id\": \"turningRed\",     \"start_time\": \"09:00\" }," +
	         "{  \"sequence_of_the_day\": 2,     \"movie_id\": \"theBatMan\",     \"start_time\": \"23:00\" } ]}"; 

    @Test
    void totalFeeForCustomer() {
        Theater theater = new Theater(LocalDateProvider.singleton());
        List<Showing> showingList = null;
        try {
        	showingList = theater.getTodayShows(inputJsonString);        	
        }
        catch(ParseException pe) {
        	pe.printStackTrace();
        	return;
        }
        theater.loadSchedule(showingList);
        Customer john = new Customer("John Doe", "id-12345");
        Reservation reservation = theater.reserve(john, 2, 4);
        // Discount is applied to each ticket and multiplied by 4. 
        // Precision rounding is done at each ticket calculation.
        assertEquals(reservation.totalFee(), 37.52, "Total fee should be discounted ticket fee * ticket count.");
    }

    @Test
    void printMovieSchedule() {
        Theater theater = new Theater(LocalDateProvider.singleton());
        List<Showing> showingList = null;
        try {
        	showingList = theater.getTodayShows(inputJsonString);        	
        }
        catch(ParseException pe) {
        	pe.printStackTrace();
        	return;
        }
        theater.loadSchedule(showingList);
        theater.printSchedule();
    }
    
    @Test
    void printMovieScheduleJson() {
        Theater theater = new Theater(LocalDateProvider.singleton());
        List<Showing> showingList = null;
        try {
        	showingList = theater.getTodayShows(inputJsonString);        	
        }
        catch(ParseException pe) {
        	pe.printStackTrace();
        	return;
        }
        theater.loadSchedule(showingList);
        theater.printScheduleJson();
    }
    
    @Test
    void illFormattedInput() {
        Theater theater = new Theater(LocalDateProvider.singleton());
        assertThrows(ParseException.class, () -> {
        	theater.getTodayShows(illFormattedInput);
    	}, "ParseException was expected");
    }
    
	
	@Test
	void testHumanReadableFormat() {
		Movie movie1 = new Movie("Spider-Man: No Way Home", Duration.ofMinutes(90), 10, 1);
		Showing showing = new Showing(movie1, 1, 
				             LocalDateTime.of(LocalDate.of(2022, 12, 1), LocalTime.of(4, 10)));
		Theater theater = new Theater(LocalDateProvider.singleton());
        List<Showing> showingList = new ArrayList<Showing>();
        showingList.add(showing);        
        theater.loadSchedule(showingList);
		assertEquals("(1 hour 30 minutes)", 
				     theater.humanReadableFormat(showing.getMovie().getRunningTime()),
				     "Testing humanReableFormat: (x hour xx minutes)");
	}
}
