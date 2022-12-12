package com.jpmc.theater;

import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ReservationTests {

    @Test
    void totalFee() {
        var customer = new Customer("John Doe", "unused-id");
        var showing = new Showing(
                new Movie("Spider-Man: No Way Home", Duration.ofMinutes(90), 12.5, 0),
                3,
                LocalDateTime.of(LocalDate.of(2022, 12, 1), LocalTime.of(10, 0))
        );
        assertTrue(new Reservation(customer, showing, 3).totalFee() == 37.5);
        
        var customer2 = new Customer("Jane Doe", "unused-id");
        var showing2 = new Showing(
                new Movie("Spider-Man: No Way Home", Duration.ofMinutes(90), 10, 1),
                4,
                LocalDateTime.of(LocalDate.of(2022, 12, 1), LocalTime.of(10, 0))
        );
        // 20% discount for special code 8*3 = 24
        assertTrue(new Reservation(customer2, showing2, 3).totalFee() == 24);
    }
    

    @Test
    void totalFeeForOne() {
        var customer = new Customer("John Doe", "unused-id");
        var showing = new Showing(
                new Movie("Spider-Man: No Way Home", Duration.ofMinutes(90), (float) 12.5, 0),
                3,
                LocalDateTime.of(LocalDate.of(2022, 12, 1), LocalTime.of(10, 0))
        );
        assertTrue(new Reservation(customer, showing, 1).totalFee() == 12.5);
    }
}
