package com.assignment;

import com.assignment.controller.AdminCommand;
import com.assignment.controller.BuyerCommand;
import com.assignment.controller.CommandFactory;
import com.assignment.controller.Command;
import com.assignment.controller.InvalidCommandException;
import com.assignment.controller.QuitCommand;
import com.assignment.model.Seats;
import com.assignment.constants.SystemContent;

import java.util.Scanner;

public class App {
    private static CommandFactory commandFactory;
    private static Scanner scanner;
    private static Command command;

    public static void main(String[] args) throws NumberFormatException, InterruptedException {
        scanner = new Scanner(System.in);
        commandFactory = new CommandFactory();

        // initialize seat availability
        Seats.loadSeats();
        Seats.loadSeatAvailability();

        while (true) {
            System.out.println(SystemContent.LINEBREAK);
            System.out.println("【Peter's Show Booking App】");
            System.out.println("【Main Menu】");
            System.out.println(SystemContent.LINEBREAK);
            System.out.println("What is your role?");
            System.out.println("a. Admin");
            System.out.println("b. Buyer");
            System.out.println("q. Exit");
            System.out.print("Please enter your choice: ");
            
            String userType = scanner.nextLine();
            process(userType);
        }
    }
    
    private static void process(String commandLine) { 
        command = null;

        try {
            if ("a".equalsIgnoreCase(commandLine)) {
                AdminCommand.adminMenu();
            } else if ("b".equalsIgnoreCase(commandLine)) {
                BuyerCommand.buyerMenu();
            } else {
                command = commandFactory.getCommand(commandLine);
            }
        } catch (InvalidCommandException e) {
            System.out.println("");
            System.out.println("Invalid command. Please try again!");
            System.out.println("");
        }

        if (command instanceof QuitCommand) {
            quit();
        }
    }

    private static void quit() {
        scanner.close();
        System.out.println(SystemContent.LINEBREAK);
        System.out.println("Thank you for using our application!");
        System.out.println("Hope to see you again!");
        System.out.println(SystemContent.LINEBREAK);
        System.exit(0);
    }
}