package com.angrydevs.marsrover.util;

import com.angrydevs.marsrover.interfaces.ICommandParser;

import java.util.ArrayList;

public class MarsRoverCommandParser implements ICommandParser {

    public ArrayList<Constants.Movement> parseCommands(String commandsStr) {
        String[] commands = commandsStr.split(",");
        ArrayList<Constants.Movement> movements = new ArrayList<>();
        for (int i = 0; i < commands.length; i++) {
            movements.add(parseCommand(commands[i]));
        }
        return movements;
    }

    private Constants.Movement parseCommand(String command) {
        switch (command) {
            case "f":
                return Constants.Movement.FORWARD;
            case "r":
                return Constants.Movement.RIGHT;
            case "b":
                return Constants.Movement.BACKWARD;
            case "l":
                return Constants.Movement.LEFT;
        }
        throw new IllegalArgumentException("Invalid command received. Commands can only be 'f', 'b', 'l' or 'r'");
    }
}
