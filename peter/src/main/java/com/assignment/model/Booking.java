package com.assignment.model;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import java.util.Set;
import java.util.HashSet;

public class Booking {
    public static LocalDateTime bookedAt;
    public static int nextBookingId = 1;
    public static int bookingValidPeriod = 120;
    public static Set<String> bookingList = new HashSet<>();
    public static String bookingInfo= "";

    public static String generateNewBookingId() {
        nextBookingId++;
        bookingInfo = Show.show1+"-"+Buyer.phoneNumber+"-"+nextBookingId++;
        bookingList.add(bookingInfo);
        return bookingInfo;
    } 

    public static Set<String> getBookingList() {
        return bookingList;
    }

    public static void setBookingTime() {
        bookedAt = LocalDateTime.now();
    }
    public static LocalDateTime getBookedAt() {
        return bookedAt;
    }

    public static void setBookingValidPeriod(int seconds) {
        bookingValidPeriod = seconds;
    }

    public static boolean isWithinBookingPeriod() {
        return bookedAt.until(LocalDateTime.now(), ChronoUnit.SECONDS) <= bookingValidPeriod;
    }
}