package com.angrydevs.marsrover.controller;

import com.angrydevs.marsrover.interfaces.IMovable;
import com.angrydevs.marsrover.model.MarsRover;
import com.angrydevs.marsrover.model.Obstacle;
import com.angrydevs.marsrover.util.Constants;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by Erlan on 29/02/2016.
 */
public class GameController {
    IMovable mainCharacter;
    List<Obstacle> obstacles;

    @Inject
    public GameController(IMovable mainCharacter) {
        this.mainCharacter = mainCharacter;
    }

    public void move(Constants.Movement movement) {
        mainCharacter.move(movement);
    }
}
