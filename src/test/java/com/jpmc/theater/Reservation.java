package com.jpmc.theater;

import java.util.Objects;

public class Reservation {
    private Customer customer;
    private Showing showing;
    private int audienceCount;

    public Reservation(Customer customer, Showing showing, int audienceCount) {
        this.customer = customer;
        this.showing = showing;
        this.audienceCount = audienceCount;
    }

    // Discount is applied to each ticket and multiplied by 4. 
    // Precision rounding is done at each ticket calculation.
    public double totalFee() {
        return showing.getFee() * audienceCount;
    }
    
    public String getCustomerName() {
        return customer.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Reservation)) return false;
        Reservation reservation = (Reservation) o;
        return Objects.equals(customer.toString(), reservation.customer.toString()) && 
        		Objects.equals(showing.toString(), reservation.showing.toString()) &&
        		audienceCount == reservation.audienceCount;
    }

    @Override
    public int hashCode() {
        return Objects.hash(customer.toString(), showing.toString(), audienceCount);
    }

    @Override
    public String toString() {
        return customer.toString() + " " + showing.toString() + " audience count: " + audienceCount;
    }
}