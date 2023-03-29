package com.assignment.controller;

import com.assignment.constants.SystemContent;
import com.assignment.model.Booking;
import com.assignment.model.Seats;

import java.util.InputMismatchException;
import java.util.Scanner;

public class AdminCommand implements Command {

    private static Scanner scanner = new Scanner(System.in);

    public static void adminMenu() {
        
        System.out.println("");
        while (true) {
            System.out.println(SystemContent.LINEBREAK);
            System.out.println("【Admin Menu】");
            System.out.println(SystemContent.LINEBREAK);
            System.out.println("1. View booked seats");
            System.out.println("2. Add booking rows and seats");
            System.out.println("3. Edit booking valid period");
            System.out.println("4. Return to main menu");
            System.out.print("Please enter your choice: ");

            scanner = new Scanner(System.in);
            String choice = scanner.nextLine();
            switch (choice) {
                case "1":
                    adminCheckBookingMenu();
                    break;
                case "2":
                    adminAddingMenu();
                    break;
                case "3":
                    adminEditBookingMenu();
                    break;
                case "4":
                    return;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    private static void adminCheckBookingMenu() {
        System.out.println("Booked seats: " + Seats.getBookedSeats());
    }

    private static void adminAddingMenu() {
        if (Seats.getBookedSeats().size() > 0) {
            System.out.println("You are not allowed to customise the show row and column seats at the moment. As customer already made a booking.");
            return;
        }

        System.out.println("");
        System.out.print("Please enter number of rows(1<= row <=26): ");
        
        Integer rows = 0;
        scanner = new Scanner(System.in);
        Integer seats = 0;

        try {
            rows = scanner.nextInt();
            while(rows<1 || rows>26) {
                System.out.print("You have entered an Invalid number!");
                System.out.print("Please enter number of rows(1<= row <=26): ");
                rows = scanner.nextInt();
            }
    
            System.out.print("Please enter number of seats per row(1<= seats <=10): ");
            // initialise the new scanner
            scanner = new Scanner(System.in);
            seats = scanner.nextInt();

            while(seats<1 || seats>10) {
                System.out.print("You have entered an Invalid number!");
                System.out.print("Please enter number of seats per row(1<= seats <=10): ");
                seats = scanner.nextInt();
            }

            // Set the value in the end when admin input are all valid
            Seats.setNumOfRow(rows);
            Seats.setNumOfSeatPerRow(seats);
        } catch(InputMismatchException e){
            System.out.println("Invalid Input! Please enter valid number only!");
            return;
        }


        System.out.println(SystemContent.LINEBREAK);
        System.out.println("The show seats has been updated Successfully!");
        System.out.println("There are "+rows+" rows and "+seats+" seats per row available in the booking system.");
        System.out.println(SystemContent.LINEBREAK);
        System.out.println("");

        Seats.loadSeats();
        Seats.loadSeatAvailability();
        return;
    }

    private static void adminEditBookingMenu() {
        try {
            // initialise the new scanner
            scanner = new Scanner(System.in);
            System.out.print("Please enter a integer value for booking period.(Unit: second): ");

            Integer period = scanner.nextInt();

            if(period <= 0) {
                System.out.println("Invalid Input! Please enter valid postive integer number only!");
                return;
            }

            System.out.println("Thank you update the booking period! Buyer cannot cancelled the booking after "+period+" seconds.");
            Booking.setBookingValidPeriod(period);
        } catch(InputMismatchException e){
            System.out.println("Invalid Input! Please enter valid number only!");
            return;
        }
    }
}
