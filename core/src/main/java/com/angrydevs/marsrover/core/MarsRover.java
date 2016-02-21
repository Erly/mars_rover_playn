package com.angrydevs.marsrover.core;

import com.angrydevs.marsrover.core.com.angrydevs.marsrove.util.Constants;
import playn.core.Platform;
import playn.core.Surface;
import playn.scene.Layer;
import playn.scene.SceneGame;
import pythagoras.f.IDimension;
import react.RList;

import java.util.Random;

public class MarsRover extends SceneGame {

    public final int mapSize;
    public final RList<Coord> obstacles = RList.create();
    public int numberOfObstacles = 4;
  /*public final Value<Piece> turn = Value.create(null);*/

    public MarsRover(Platform plat) {
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

        /*// create and add background image layer
        Image bgImage = plat.assets().getImage("images/bg.png");
        ImageLayer bgLayer = new ImageLayer(bgImage);
        // scale the background to fill the screen
        bgLayer.setSize(plat.graphics().viewSize);
        rootLayer.add(bgLayer);*/
    }

    private void reset() {
        obstacles.clear();
        Random rand = new Random(System.currentTimeMillis());
        int i = 0, x, y;
        while (i < numberOfObstacles) {
            x = rand.nextInt(mapSize);
            y = rand.nextInt(mapSize);
            Coord coord = new Coord(x, y);
            if (!obstacles.contains(coord)) {
                obstacles.add(coord);
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
