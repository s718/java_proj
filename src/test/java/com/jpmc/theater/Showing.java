package com.jpmc.theater;

import java.time.LocalDateTime;
import java.util.Objects;

import org.apache.commons.math3.util.Precision;

public class Showing {
    private Movie movie;
    private int sequenceOfTheDay;
    private LocalDateTime showStartTime;
    private double fee;

    public Showing(Movie movie, int sequenceOfTheDay, LocalDateTime showStartTime) {
        this.movie = movie;
        this.sequenceOfTheDay = sequenceOfTheDay;
        this.showStartTime = showStartTime;
        
        // Movie fee is calculated at Showing constructor because discount is depends on
        // showing related value such as sequence of the day, show start time.
        // Rounding is done after discount amount is subtracted to 2 decimal places.
        this.fee = Precision.round(movie.getTicketPrice() - 
        		               getDiscount(sequenceOfTheDay, movie, showStartTime), 2);
    }

    public Movie getMovie() {
        return this.movie;
    }

    public LocalDateTime getStartTime() {
        return this.showStartTime;
    }

    public boolean isSequence(int sequence) {
        return this.sequenceOfTheDay == sequence;
    }

    public double getFee() {       
    	return fee;
    }

    public int getSequenceOfTheDay() {
        return this.sequenceOfTheDay;
    }
    
    // Movie fee is calculated in Showing class because discount is depends on
    // showing related value such as sequence of the day, show start time.
    private double getDiscount(int showSequence, Movie movie, LocalDateTime showingTime) {
    	
    	double maxDiscount = 0;
    	
        if (movie.hasSpecialCode()) {
            // 20% discount for special movie
        	maxDiscount = Math.max(maxDiscount, movie.getTicketPrice() * 0.2);
        }
        
        if (showSequence == 1) {
        	maxDiscount = Math.max(maxDiscount, 3); // $3 discount for 1st show
        } else if (showSequence == 2) {
        	maxDiscount = Math.max(maxDiscount, 2); // $2 discount for 2nd show
        }
        
        // Off peak hour starts at 11AM
        LocalDateTime offPeakStartTime = LocalDateTime.of(showingTime.getYear(), 
        		                                          showingTime.getMonth(), 
        		                                          showingTime.getDayOfMonth(), 11, 00); 
        // Off peak hour ends at 4 PM
        LocalDateTime offPeakEndTime = LocalDateTime.of(showingTime.getYear(), 
        		                                        showingTime.getMonth(), 
        		                                        showingTime.getDayOfMonth(), 16, 00);
        
        if(showingTime.isEqual(offPeakStartTime) || // including 11 AM
        	showingTime.isEqual(offPeakEndTime) || // including 4 PM
          (showingTime.isAfter(offPeakStartTime) && showingTime.isBefore(offPeakEndTime))) { 
        	maxDiscount = Math.max(maxDiscount, movie.getTicketPrice() * 0.25);
        }
        
        if(showingTime.getDayOfMonth() == 7) {
        	maxDiscount = Math.max(maxDiscount, 1);
        }
        // biggest discount wins
        
          return maxDiscount;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Showing)) return false;
        Showing showing = (Showing) o;
        return Objects.equals(this.movie.toString(), showing.movie.toString()) && 
        		Objects.equals(this.showStartTime.toString(), showing.showStartTime.toString()) &&
        		Double.compare(this.fee, showing.getFee()) == 0  &&
        		sequenceOfTheDay == showing.sequenceOfTheDay;
    }

    @Override
    public int hashCode() {
        return Objects.hash(movie.toString(), sequenceOfTheDay, fee, showStartTime.toString());
    }

    @Override
    public String toString() {
        return "seq: " + sequenceOfTheDay + 
        		" start time: " + showStartTime.toString() + 
        		" movie: " + movie.getTitle() +
        		" fee: " + fee;
    }

}
