package com.assignment.controller;

public class CommandFactory {
    public Command getCommand(String commandLine) throws InvalidCommandException {
        commandLine = commandLine.trim().replaceAll(" {2}", " ");
        String[] split = commandLine.split(" ");
        String mainCommand = split[0].toUpperCase();
        switch (mainCommand) {
            case "A":
                return new AdminCommand();
            case "B":
                return new BuyerCommand();
            case "Q":
                return new QuitCommand();
            default:
                throw new InvalidCommandException();
        }
    }
}