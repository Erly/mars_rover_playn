package com.angrydevs.marsrover.model;

/**
 * Created by Erlan on 29/02/2016.
 */
public class Obstacle extends GameObject {

    public Obstacle(Position initialPosition) {
        super(initialPosition, new Size(1, 1));
    }

    public Obstacle(Position initialPosition, Size size) {
        super(initialPosition, size);
    }
}
