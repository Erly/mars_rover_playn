package com.angrydevs.marsrover.util;

/**
 * Created by Erlan on 21/02/2016.
 */
public class Constants {
    public static class Colors {
        public static final int BLACK = 0xFF000000;
        public static final int MARS_RED = 0xffe8784a;
    }

    public static class Direction {
        private int direction;
        private Direction(int direction) {
            this.direction = direction;
        }

        public static final Direction UP = new Direction(0),
                                      RIGHT = new Direction(1),
                                      DOWN = new Direction(2),
                                      LEFT = new Direction(3);
    }

    public static class Movement {
        private char movementChar;
        private Movement(char movementChar) {
            this.movementChar = movementChar;
        }

        public static final Movement FORWARD = new Movement('F'),
                                     RIGHT = new Movement('R'),
                                     BACKWARD = new Movement('B'),
                                     LEFT = new Movement('L');
    }
}