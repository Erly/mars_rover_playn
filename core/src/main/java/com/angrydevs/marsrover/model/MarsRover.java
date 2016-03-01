package com.angrydevs.marsrover.model;

import com.angrydevs.marsrover.interfaces.IMovable;
import com.angrydevs.marsrover.util.Constants;
import dagger.Module;

/**
 * Created by Erlan on 29/02/2016.
 */
@Module
public class MarsRover extends GameObject implements IMovable {

    public MarsRover(Position initialPosition, Size size) {
        super(initialPosition, size);
    }

    @Override
    public void move(Constants.Movement movement) {
        switch (movement) {
            case LEFT:
                turnLeft();
                return;
            case RIGHT:
                turnRight();
                return;
            case FORWARD:
                return;
            case BACKWARD:
                return;
        }
    }

    private void turnLeft() {
        switch (direction) {
            case UP:
                direction = Constants.Direction.LEFT;
                return;
            case RIGHT:
                direction = Constants.Direction.UP;
                return;
            case DOWN:
                direction = Constants.Direction.RIGHT;
                return;
            case LEFT:
                direction = Constants.Direction.DOWN;
        }
    }

    private void turnRight() {
        switch (direction) {
            case UP:
                direction = Constants.Direction.RIGHT;
                return;
            case RIGHT:
                direction = Constants.Direction.DOWN;
                return;
            case DOWN:
                direction = Constants.Direction.LEFT;
                return;
            case LEFT:
                direction = Constants.Direction.UP;
        }
    }
}
