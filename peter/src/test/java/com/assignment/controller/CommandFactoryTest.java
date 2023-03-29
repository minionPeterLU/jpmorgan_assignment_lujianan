package com.assignment.controller;

import org.junit.Before;
import org.junit.Test;

public class CommandFactoryTest {

    private CommandFactory commandFactory;

    @Before
    public void setUp() throws Exception {
        commandFactory = new CommandFactory();
    }

    @Test
    public void getCommandForAdminMenu() throws Exception {
        commandFactory.getCommand("A");
    }

    @Test
    public void getCommandForBuyerMenu() throws Exception {
        commandFactory.getCommand("B");
    }

    @Test
    public void getCommandForExit() throws Exception {
        commandFactory.getCommand("Q");
    }
}
