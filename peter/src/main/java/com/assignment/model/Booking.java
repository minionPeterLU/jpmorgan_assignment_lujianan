package com.assignment.model;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class Booking {
    public static LocalDateTime bookedAt;
    public static int nextBookingId = 1;


    public static String generateNewBookingId() {
        nextBookingId++;

        return Show.show1+"-"+Buyer.phoneNumber+"-"+nextBookingId++;
    } 

    public static void setBookingTime() {
        bookedAt = LocalDateTime.now();
    }
    public static LocalDateTime getBookedAt() {
        return bookedAt;
    }

    public static boolean isWithinTwoMinutes() {
        return bookedAt.until(LocalDateTime.now(), ChronoUnit.SECONDS) <= 120;
    }
}