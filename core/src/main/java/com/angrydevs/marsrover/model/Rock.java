package com.angrydevs.marsrover.model;

public class Rock extends Obstacle {

    private static final String IMAGE_PATH = "images/rock.png";

    public Rock(Position position) {
        super(position, new Size(1, 1));
    }

    public Rock(Position position, Size size) {
        super(position, size);
    }
}
