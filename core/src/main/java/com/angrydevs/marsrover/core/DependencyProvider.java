package com.angrydevs.marsrover.core;

import com.angrydevs.marsrover.interfaces.IMovable;
import com.angrydevs.marsrover.model.MarsRover;
import com.angrydevs.marsrover.model.Position;
import com.angrydevs.marsrover.model.Size;
import dagger.Module;
import dagger.Provides;

/**
 * Created by Erlan on 01/03/2016.
 */
@Module
public class DependencyProvider {
    @Provides static IMovable provideMarsRover() {
        Position pos = new Position(0, 0);
        Size size = new Size(1, 1);
        return new MarsRover(pos, size);
    }
}
