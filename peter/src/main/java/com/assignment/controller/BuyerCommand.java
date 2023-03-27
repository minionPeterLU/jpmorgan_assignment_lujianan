package com.assignment.controller;

import com.assignment.constants.SystemContent;
import com.assignment.model.Booking;
import com.assignment.model.Seats;

import java.util.Iterator;
import java.util.Scanner;

public class BuyerCommand implements Command {

    private static Scanner scanner = new Scanner(System.in);;

    public static void buyerMenu() {
        System.out.println("");
        while (true) {
            System.out.println(SystemContent.LINEBREAK);
            System.out.println("【Buyer Menu】");
            System.out.println(SystemContent.LINEBREAK);
            System.out.println("1. Book seats");
            System.out.println("2. View Booked seats");
            System.out.println("3. Cancel Booked seats");
            System.out.println("4. Return to main menu");
            System.out.print("Please enter your choice: ");
            String choice = scanner.nextLine();

            switch(choice) {
                case "1":
                    buyerBookingMenu();
                    break;
                case "2":
                    buyerViewBookingMenu();
                    break;
                case "3":
                    buyerCancelMenu();
                    break;
                case "4":
                    return;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    private static void buyerBookingMenu() {
        while(true) {
            System.out.println(SystemContent.LINEBREAK);
            printAvailableSeats();
            System.out.println(SystemContent.LINEBREAK);
            System.out.println("Enter seat to book(e.g. a1 or A1): ");
            String input = scanner.nextLine().toUpperCase().trim();
            
            if (Seats.getRowSeatAvailablibity().containsKey(input)) {
                if(Seats.getRowSeatAvailablibity().get(input)){
                    System.out.print("Confirm booking? (Enter y/n): ");
                    String confirm = scanner.nextLine();
                    if ("y".equalsIgnoreCase(confirm)) {
                        String bookingId = Booking.generateNewBookingId();
                        Seats.addSeatInBooking(input);
                        Seats.occupySeat(input);
                        Seats.loadSeatAvailability();
                        Booking.setBookingTime();
                        System.out.println("Booking successful. Your booking ID is " + bookingId);
                        return;
                    } else {
                        System.out.println("Booking cancelled.");
                        return;
                    }
                } else {
                    System.out.println("Sorry! This seat has been booked!");
                    return;
                }
            } else {
                System.out.println("Please enter a valid seat!");
            }
        }
    }

    
    private static void buyerViewBookingMenu(){
        if (Seats.getBookedSeats().size() > 0) {

            System.out.println("You've booked " + Seats.getBookedSeats());
        } else {
            System.out.println("Sorry! You don't have any booking!");
        }
    }

    private static void buyerCancelMenu() {
        if(Seats.getBookedSeats().size() == 0){
            System.out.println("Sorry! You don't have any booking!");
            return;
        }

        System.out.print("Are you sure you want to cancel booking?(y/n): ");
        String input = scanner.nextLine().toUpperCase().trim();

        if ("y".equalsIgnoreCase(input)) {
            if (Booking.isWithinTwoMinutes()) {
                System.out.println("Booking cancelled.");
                Iterator<String> it = Seats.getBookedSeats().iterator();
                while(it.hasNext()){
                    String seatId = it.next();
                    Seats.cancelSeat(seatId);
                }
                Seats.resetBookedSeats();
                Seats.loadSeatAvailability();
            } else {
                System.out.println("Booking cannot be canceled as it's been more than 2 minutes.");
            }
        } else {
            return;
        }

    }

    private static void printAvailableSeats(){
        String[][] availableSeatPlan = Seats.getAvailableSeatPlan();
        for(int i = 0; i < availableSeatPlan.length; i++){
            System.out.print("Row "+(i+1)+" : ");
            for(int j = 0; j < availableSeatPlan[i].length - 1; j++){
                System.out.print(availableSeatPlan[i][j]+" , ");
            }
            System.out.print(availableSeatPlan[i][availableSeatPlan[i].length - 1]);
            System.out.println("");
        }
    }
}
