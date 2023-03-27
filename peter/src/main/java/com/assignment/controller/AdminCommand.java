package com.assignment.controller;

import com.assignment.constants.SystemContent;
import com.assignment.model.Seats;

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
            System.out.println("2. Add seats");
            System.out.println("3. Return to main menu");
            System.out.print("Please enter your choice: ");
            String choice = scanner.nextLine();
            switch (choice) {
                case "1":
                    System.out.println("Booked seats: " + Seats.getBookedSeats());
                    break;
                case "2":
                    adminAddingMenu();
                    break;
                case "3":
                    return;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    private static void adminAddingMenu() {
        if (Seats.getBookedSeats().size() > 0) {
            System.out.println("You are not allowed to customise the show row and column seats at the moment. As customer already made a booking.");
            return;
        }

        System.out.println("");
        System.out.print("Please enter number of rows(1<= row <=26): ");
        Integer rows = scanner.nextInt();
      
        while(rows<1 || rows>26) {
            System.out.print("You have entered an Invalid number!");
            System.out.print("Please enter number of rows(1<= row <=26): ");
            rows = scanner.nextInt();
        }

        Seats.setNumOfRow(rows);
        System.out.print("Please enter number of seats per row(1<= seats <=10): ");
        Integer seats = scanner.nextInt();
        // to prevent 2nd scanner going back to upper menu
        scanner = new Scanner(System.in);

        while(seats<1 || seats>10) {
            System.out.print("You have entered an Invalid number!");
            System.out.print("Please enter number of seats per row(1<= seats <=10): ");
            seats = scanner.nextInt();
        }

        Seats.setNumOfSeatPerRow(seats);
        System.out.println(SystemContent.LINEBREAK);
        System.out.println("The show seats has been updated Successfully!");
        System.out.println("There are "+rows+" rows and "+seats+" seats per row available in the booking system.");
        System.out.println(SystemContent.LINEBREAK);
        System.out.println("");

        Seats.loadSeats();
        Seats.loadSeatAvailability();
        return;
    }

}
