package com.angrydevs.marsrover.model;

public class Obstacle extends GameObject {

    private final Position position;

    public Obstacle(Position position, Size size) {
        super(size);
        this.position = position;
    }

    public Position getPosition() {
        return position;
    }
}
