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
    public void getCommand() throws Exception {
        commandFactory.getCommand("Q");
    }
}
