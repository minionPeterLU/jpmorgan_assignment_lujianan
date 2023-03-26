package com.assignment;

import com.assignment.controller.CommandFactory;
import com.assignment.controller.Command;
import com.assignment.controller.InvalidCommandException;
import com.assignment.controller.QuitCommand;
import com.assignment.model.Booking;

import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

public class App {
    private static CommandFactory commandFactory;
    private static Scanner scanner;
    private static Map<String, Boolean> rowSeatAvailability = new HashMap<>();
    private static Map<Integer, Map<String, Boolean>> rowAvailability = new HashMap<>();
    private static Set<String> bookedSeats = new HashSet<>();
    private static String[][] availableSeatPlan = new String[8][10];
    private static int NUM_SEATS_ROW = 10; // number of seats in a row;
    private static int NUM_ROWS = 8; // number of rows;

    public static void main(String[] args) throws NumberFormatException, InterruptedException {
        scanner = new Scanner(System.in);
        commandFactory = new CommandFactory();

        // initialize seat availability
        loadSeats();
        loadSeatAvailability();

        while (true) {
            System.out.println("========================================================");
            System.out.println("【Peter's Show Booking App】");
            System.out.println("【Main Menu】");
            System.out.println("========================================================");
            System.out.println("What is your role?");
            System.out.println("a. Admin");
            System.out.println("b. Buyer");
            System.out.println("q. Exit");
            System.out.print("Please enter your choice: ");
            
            String userType = scanner.nextLine();
            process(userType);
        }
    }
    
    private static void loadSeats() {
        for(int i = 1; i <= NUM_ROWS; i++){
            char rowOrder = (char)(i+64);
            for (int j = 1; j <= NUM_SEATS_ROW; j++) {
                rowSeatAvailability.put(rowOrder+""+j, true);
            }
            rowAvailability.put(i,rowSeatAvailability);
        }
    }
    
    private static void loadSeatAvailability(){
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

    private static void process(String commandLine) { 
        Command command = null;
        try {
            if ("a".equalsIgnoreCase(commandLine)) {
                adminMenu();
            } else if ("b".equalsIgnoreCase(commandLine)) {
                buyerMenu();
            } else {
                command = commandFactory.getCommand(commandLine);
            }
        } catch (InvalidCommandException e) {
            System.out.println("");
            System.out.println("");
            System.out.println("Invalid command. Please try again!");
            System.out.println("");
            System.out.println("");
        }

        if (command instanceof QuitCommand) {
            quit();
        }
    }

    private static void quit() {
        scanner.close();
        System.out.println("========================================================");
        System.out.println("Thank you for using our application!");
        System.out.println("Hope to see you again!");
        System.out.println("========================================================");
        System.exit(0);
    }

    private static void adminMenu() {
        while (true) {
            System.out.println("========================================================");
            System.out.println("【Admin Menu】");
            System.out.println("========================================================");
            System.out.println("1. View booked seats");
            System.out.println("2. Add seats");
            System.out.println("3. Return to main menu");
            System.out.print("Please enter your choice: ");
            String choice = scanner.nextLine();
            switch (choice) {
                case "1":
                    System.out.println("Booked seats: " + bookedSeats);
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
    
    private static void adminAddingMenu(){
        if (bookedSeats.size() > 0) {
            System.out.println("You are not allowed to customise the show row and col seats at the moment. As customer already made a booking.");
            return;
        }

        System.out.print("Please enter number of rows(1<= row <=26): ");
        Integer rows = scanner.nextInt();
      
        while(rows<1 || rows>26) {
            System.out.print("You have entered an Invalid number!");
            System.out.print("Please enter number of rows(1<= row <=26): ");
        }
        NUM_ROWS = rows;

        System.out.print("Please enter number of seats per row(1<= seats <=10): ");
        Integer seats = scanner.nextInt();
        while(seats<1 || seats>10) {
            System.out.print("You have entered an Invalid number!");
            System.out.print("Please enter number of seats per row(1<= seats <=10): ");
        }

        NUM_SEATS_ROW = seats;
        System.out.println("========================================================");
        System.out.println("The show seats has been updated Successfully!");
        System.out.println("There are "+rows+" rows and "+seats+" seats per row available in the booking system.");
        System.out.println("========================================================");
        System.out.println();

        loadSeats();
        loadSeatAvailability();
    }

    private static void printAvailableSeats(){
        for(int i = 0; i < availableSeatPlan.length; i++){
            System.out.print("Row "+(i+1)+" : ");
            for(int j = 0; j < availableSeatPlan[i].length - 1; j++){
                System.out.print(availableSeatPlan[i][j]+" , ");
            }
            System.out.print(availableSeatPlan[i][availableSeatPlan[i].length - 1]);
            System.out.println("");
        }
    }

    private static void buyerMenu() {
        while (true) {
            System.out.println("========================================================");
            System.out.println("【Buyer Menu】");
            System.out.println("========================================================");
            System.out.println("1. Book seats");
            System.out.println("2. Cancel Booked seats");
            System.out.println("3. Return to main menu");
            System.out.print("Please enter your choice: ");
            String choice = scanner.nextLine();

            switch(choice) {
                case "1":
                    buyerBookingMenu();
                    break;
                case "2":
                    buyerCancelMenu();
                    break;
                case "3":
                    return;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    private static void buyerCancelMenu() {
        if(bookedSeats.size() == 0){
            System.out.println("Sorry you don't have any booking!");
            return;
        }

        System.out.println("Are you sure you want to cancel booking?(y/n)");

       
        String input = scanner.nextLine().toUpperCase().trim();
        if ("y".equalsIgnoreCase(input)) {
            if (Booking.isWithinTwoMinutes()) {
                System.out.println("Booking canceled.");
                Iterator<String> it = bookedSeats.iterator();
                while(it.hasNext()){
                    String seatId = it.next();
                    rowSeatAvailability.put(seatId,true);
                }
                bookedSeats = new HashSet<>();
                loadSeatAvailability();
            } else {
                System.out.println("Booking cannot be canceled as it's been more than 2 minutes.");
            }
        } else {
            return;
        }

    }

    private static void buyerBookingMenu() {
        while(true){
            System.out.println("========================================================");
            printAvailableSeats();
            System.out.println("========================================================");
            System.out.println("Enter seat to book(e.g. a1 or A1):");

            String input = scanner.nextLine().toUpperCase().trim();
            
            if (rowSeatAvailability.containsKey(input)) {
                if(rowSeatAvailability.get(input)){
                    System.out.print("Confirm booking? (Enter y/n) ");
                    String confirm = scanner.nextLine();
                    if ("y".equalsIgnoreCase(confirm)) {
                        int bookingId = Booking.generateNewBookingId();
                        bookedSeats.add(input);
                        rowSeatAvailability.put(input,false);
                        loadSeatAvailability();
                        Booking.setBookingTime();
                        System.out.println("Booking successful. Your booking ID is " + bookingId);
                        return;
                    } else {
                        System.out.println("Booking cancelled.");
                        return;
                    }
                } else {
                    System.out.println("Sorry this seat has been booked!");
                    return;
                }
            } else {
                System.out.println("Please enter a valid seat!");
            }
        }
    }
}