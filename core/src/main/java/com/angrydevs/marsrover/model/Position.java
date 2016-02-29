package com.angrydevs.marsrover.model;

/**
 * Created by Erlan on 29/02/2016.
 */
public class Position {
    public float x, y;

    public void add(Position other) {
        x += other.x;
        y += other.y;
    }

    public void multiply(int multiplier) {
        x *= multiplier;
        y *= multiplier;
    }
}
