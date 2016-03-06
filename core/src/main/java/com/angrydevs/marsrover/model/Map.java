package com.angrydevs.marsrover.model;

import com.angrydevs.marsrover.util.Constants;
import react.RList;
import react.Value;

import java.util.Collection;

public class Map {

    public Size size;
    private GameObject mainCharacter;
    public react.Value<Position> mainCharacterPosition = Value.create(null);
    public react.Value<Constants.Direction> mainCharacterDirection = Value.create(Constants.Direction.DOWN);
    public final RList<Obstacle> obstacles = RList.create();

    public Map(Size size){
        this.size = size;
    }

    public boolean scan(Position position) {
        if (position.x < 0 || position.x >= size.width ||
                position.y < 0 || position.y >= size.height)
            throw new IndexOutOfBoundsException();

        if (mainCharacter != null && mainCharacterPosition.equals(position))
            return true;

        for (Obstacle obstacle : obstacles) {
            if (obstacle.getPosition().equals(position)) return true;
        }

        return false;
    }

    public void addObstacle(Obstacle obstacle) {
        if (scan(obstacle.getPosition()))
            throw new IllegalArgumentException("There is already an object on that same position.");

        obstacles.add(obstacle);
    }

    public void addObstacles(Collection<Obstacle> obstacleCol) {
        for (Obstacle obstacle : obstacleCol) {
            if (scan(obstacle.getPosition()))
                throw new IllegalArgumentException("There is already an object on that same position.");
        }
        obstacles.addAll(obstacleCol);
    }

    public void setMainCharacter(GameObject mainCharacter, Position position) {
        if (scan(position))
            throw new IllegalArgumentException("There is already an object on that same position.");

        this.mainCharacter = mainCharacter;
        mainCharacterPosition.update(position);
    }

    public void moveMainCharacter(Constants.Movement movement) throws Exception {
        switch (movement) {
            case LEFT:
                turnLeft();
                return;
            case RIGHT:
                turnRight();
                return;
            case FORWARD:
            case BACKWARD:
                Position futurePos = getFuturePosition(movement);
                if (scan(futurePos)) {
                    throw new Exception("There is an obstacle in the position the character is trying to get to.");
                }
                mainCharacterPosition.update(futurePos);
                return;
        }
    }

    public void clear() {
        mainCharacter = null;
        obstacles.clear();
    }

    private void turnLeft() {
        switch (mainCharacterDirection.get()) {
            case UP:
                mainCharacterDirection.update(Constants.Direction.LEFT);
                return;
            case RIGHT:
                mainCharacterDirection.update(Constants.Direction.UP);
                return;
            case DOWN:
                mainCharacterDirection.update(Constants.Direction.RIGHT);
                return;
            case LEFT:
                mainCharacterDirection.update(Constants.Direction.DOWN);
        }
    }

    private void turnRight() {
        switch (mainCharacterDirection.get()) {
            case UP:
                mainCharacterDirection.update(Constants.Direction.RIGHT);
                return;
            case RIGHT:
                mainCharacterDirection.update(Constants.Direction.DOWN);
                return;
            case DOWN:
                mainCharacterDirection.update(Constants.Direction.LEFT);
                return;
            case LEFT:
                mainCharacterDirection.update(Constants.Direction.UP);
        }
    }

    private Position getFuturePosition(Constants.Movement movement) {
        int movementSign = movement == Constants.Movement.FORWARD ? 1 : -1;
        Position newPosition = new Position(mainCharacterPosition.get().x, mainCharacterPosition.get().y);
        switch (mainCharacterDirection.get()) {
            case UP:
                newPosition.y -= movementSign;
                break;
            case RIGHT:
                newPosition.x += movementSign;
                break;
            case DOWN:
                newPosition.y += movementSign;
                break;
            case LEFT:
                newPosition.x -= movementSign;
                break;
            default:
                throw new IllegalStateException("The main character is looking in an invalid direction");
        }
        if (newPosition.x < 0) newPosition.x = size.width - 1;
        if (newPosition.x >= size.width) newPosition.x = 0;
        if (newPosition.y < 0) newPosition.y = size.height - 1;
        if (newPosition.x >= size.height) newPosition.y = 0;
        return newPosition;
    }

}
