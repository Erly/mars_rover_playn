package com.angrydevs.marsrover.model;

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

    public boolean equals (Position other) {
        return other.x == x && other.y == y;
    }

    @Override public boolean equals (Object other) {
        return (other instanceof Position) && equals((Position) other);
    }

    @Override
    public int hashCode() {
        int result = (int) x;
        result = 31*result + (int) y;
        return result;
    }
}
