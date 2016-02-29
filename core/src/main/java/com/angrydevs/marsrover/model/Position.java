package com.angrydevs.marsrover.model;

/**
 * Created by Erlan on 29/02/2016.
 */
public class Position {
    public float x, y;

    public Position(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void add(Position other) {
        x += other.x;
        y += other.y;
    }

    public void multiply(int multiplier) {
        x *= multiplier;
        y *= multiplier;
    }
}
