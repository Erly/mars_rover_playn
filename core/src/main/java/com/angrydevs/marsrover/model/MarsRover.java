package com.angrydevs.marsrover.model;

import com.angrydevs.marsrover.interfaces.IMovable;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Created by Erlan on 29/02/2016.
 */
public class MarsRover extends GameObject implements IMovable {

    public MarsRover(Size size) {
        super(size);
    }

    @Override
    public void move() throws NotImplementedException {
        throw new NotImplementedException();
    }
}
