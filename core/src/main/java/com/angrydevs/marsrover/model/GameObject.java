package com.angrydevs.marsrover.model;

import com.angrydevs.marsrover.util.Constants;

/**
 * Created by Erlan on 29/02/2016.
 */
public abstract class GameObject {
    public Position position;
    private Size size;
    public Constants.Direction direction = Constants.Direction.DOWN;

    public GameObject(Position initialPosition, Size size) {
        this.size = size;
    }

    public Size getSize() {
        return size;
    }
}
