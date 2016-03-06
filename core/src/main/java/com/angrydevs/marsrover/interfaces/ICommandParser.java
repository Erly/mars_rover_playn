package com.angrydevs.marsrover.interfaces;

import com.angrydevs.marsrover.util.Constants;

import java.util.ArrayList;

/**
 * Created by Erlantz on 06/03/2016.
 */
public interface ICommandParser {
    ArrayList<Constants.Movement> parseCommands(String commandsStr);
}
