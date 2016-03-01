package com.angrydevs.marsrover.util;

/**
 * Created by Erlan on 21/02/2016.
 */
public class Constants {
    public static class Colors {
        public static final int BLACK = 0xFF000000;
        public static final int MARS_RED = 0xffe8784a;
    }

    /*public static class Direction {
        public final int value;
        private Direction(int value) {
            this.value = value;
        }

        public static final Direction UP = new Direction(0),
                                      RIGHT = new Direction(1),
                                      DOWN = new Direction(2),
                                      LEFT = new Direction(3);
    }*/

    public enum Direction {
        UP, RIGHT, DOWN, LEFT
    }

    public enum Movement {
        FORWARD, RIGHT, BACKWARD, LEFT
    }

    /*public static class Movement {
        public final char value;
        private Movement(char movementChar) {
            this.value = movementChar;
        }

        public static final Movement FORWARD = new Movement('F'),
                                     RIGHT = new Movement('R'),
                                     BACKWARD = new Movement('B'),
                                     LEFT = new Movement('L');
    }*/
}