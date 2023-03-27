package com.assignment.model;

import java.util.Map;
import java.util.Set;
import java.util.HashMap;
import java.util.HashSet;

public class Seats {
    public static Set<String> bookedSeats = new HashSet<>();
    public static int NUM_SEATS_ROW = 10; // number of seats in a row;
    public static int NUM_ROWS = 8; // number of rows;
    public static Map<String, Boolean> rowSeatAvailability = new HashMap<>();
    public static Map<Integer, Map<String, Boolean>> rowAvailability = new HashMap<>();
    public static String[][] availableSeatPlan = new String[8][10];

    public static Set<String> getBookedSeats() {
        return bookedSeats;
    }

    public static Map<Integer, Map<String, Boolean>> getRowAvailabilitySeats() {
        return rowAvailability;
    }

    public static Map<String, Boolean> getRowSeatAvailablibity() {
        return rowSeatAvailability;
    }

    public static String[][] getAvailableSeatPlan() {
        return availableSeatPlan;
    }
    
    public static void occupySeat(String seatId) {
        rowSeatAvailability.put(seatId, false);
    }

    public static void cancelSeat(String seatId) {
        rowSeatAvailability.put(seatId,true);
    }

    public static void addSeatInBooking(String input) {
        bookedSeats.add(input);
    }

    public static void setNumOfSeatPerRow(int number) {
        NUM_SEATS_ROW = number;
    }

    public static void setNumOfRow(int number) {
        NUM_ROWS = number;
    }

    public static void resetBookedSeats() {
        bookedSeats = new HashSet<>();
    }

    public static void resetRowSeatAvailability() {
        rowSeatAvailability = new HashMap<>();
    }

    public static void resetRowAvailability() {
        rowAvailability = new HashMap<>();
    }

    public static void loadSeats() {
        for(int i = 1; i <= NUM_ROWS; i++){
            char rowOrder = (char)(i+64);
            for (int j = 1; j <= NUM_SEATS_ROW; j++) {
                rowSeatAvailability.put(rowOrder+""+j, true);
            }
            rowAvailability.put(i,rowSeatAvailability);
        }
    }

    public static void loadSeatAvailability(){
        availableSeatPlan = new String[NUM_ROWS][NUM_SEATS_ROW];
        for (int i = 0; i < NUM_ROWS; i++){
            char rowOrder = (char)(i+65);
            for (int j = 0; j < NUM_SEATS_ROW; j++) {
                String orderId = rowOrder+""+(j+1);
                if (rowSeatAvailability.get(orderId)) {
                    availableSeatPlan[i][j] = orderId;
                } else {
                    availableSeatPlan[i][j] = "booked";
                }
            }
        }
    }
}
