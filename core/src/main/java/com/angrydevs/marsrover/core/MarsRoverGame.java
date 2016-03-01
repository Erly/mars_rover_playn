package com.angrydevs.marsrover.core;

import com.angrydevs.marsrover.util.Constants;
import com.angrydevs.marsrover.view.GameView;
import com.angrydevs.marsrover.view.MapView;
import playn.core.Keyboard;
import playn.core.Platform;
import playn.core.Surface;
import playn.scene.Layer;
import playn.scene.SceneGame;
import pythagoras.f.IDimension;
import react.RList;
import react.Value;

import java.util.Random;

public class MarsRoverGame extends SceneGame {

    public final int mapSize;
    public final RList<Coord> obstaclesCoords = RList.create();
    public int numberOfObstacles = 4;
    /*public final Value<Piece> turn = Value.create(null);*/
    public react.Value<Coord> roverCoords = Value.create(null);

    public MarsRoverGame(Platform plat) {
        super(plat, 16); // refresh the game each 16ms (60 times per second)
        this.mapSize = 12;

        final IDimension size = plat.graphics().viewSize;

        rootLayer.add(new Layer() {
            @Override
            protected void paintImpl(Surface surface) {
                surface.setFillColor(Constants.Colors.MARS_RED).fillRect(0, 0, size.width(), size.height());
            }
        });

        rootLayer.addCenterAt(new MapView(this, size), size.width()/2, size.height()/2);
        rootLayer.add(new GameView(this, size));

        reset();

        plat.input().keyboardEvents.connect(new Keyboard.KeySlot() {
            @Override public void onEmit (Keyboard.KeyEvent event) {
                if (event.down) {
                    Coord oldCord, newCoord = oldCord = roverCoords.get();
                    switch (event.key) {
                        case LEFT:
                            newCoord = new Coord(oldCord.x - 1, oldCord.y);
                            break;
                        case UP:
                            newCoord = new Coord(oldCord.x, oldCord.y - 1);
                            break;
                        case RIGHT:
                            newCoord = new Coord(oldCord.x + 1, oldCord.y);
                            break;
                        case DOWN:
                            newCoord = new Coord(oldCord.x, oldCord.y + 1);
                            break;
                        default:
                            break;
                    }
                    if (newCoord != oldCord && !obstaclesCoords.contains(newCoord)) {
                        roverCoords.update(newCoord);
                    }
                }
            }
        });
    }

    private void reset() {
        obstaclesCoords.clear();
        Random rand = new Random(System.currentTimeMillis());
        int i = 0, x, y;

        x = rand.nextInt(mapSize);
        y = rand.nextInt(mapSize);
        Coord rCoords = new Coord(x, y);
        roverCoords.update(rCoords);

        while (i < numberOfObstacles) {
            x = rand.nextInt(mapSize);
            y = rand.nextInt(mapSize);
            Coord coord = new Coord(x, y);
            if (!obstaclesCoords.contains(coord) && rCoords != coord) {
                obstaclesCoords.add(coord);
                i++;
            }
        }
    }

    public static class Coord {
        public final int x, y;

        public Coord(int x, int y) {
            assert x >= 0 && y >= 0;
            this.x = x;
            this.y = y;
        }

        public boolean equals (Coord other) {
            return other.x == x && other.y == y;
        }

        @Override public boolean equals (Object other) {
            return (other instanceof Coord) && equals((Coord)other);
        }

        @Override public int hashCode () { return x ^ y; }

        @Override public String toString () { return "+" + x + "+" + y; }
    }
}
