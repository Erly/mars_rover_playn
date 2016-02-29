package com.angrydevs.marsrover.core;

import com.angrydevs.marsrover.util.Constants;
import playn.core.Surface;
import playn.scene.Layer;
import pythagoras.f.IDimension;

/**
 * Created by Erlan on 21/02/2016.
 */
public class MapView extends Layer {
    private static final float LINE_WIDTH = 2;
    private final MarsRoverGame game;

    public final float cellSize;

    public MapView(MarsRoverGame game, IDimension viewSize) {
        this.game = game;
        float maxBoardSize = Math.min(viewSize.width(), viewSize.height()) - 20;
        this.cellSize = (float)Math.floor(maxBoardSize / game.mapSize);
    }

    @Override public float width () { return cellSize * game.mapSize + LINE_WIDTH; }
    @Override public float height () { return width(); }

    @Override
    protected void paintImpl(Surface surface) {
        surface.setFillColor(Constants.Colors.BLACK);
        float top = 0, bottom = height(), left = 0, right = width();

        for (int y = 0; y <= game.mapSize; y++) {
            float ypos = y*cellSize + 1;
            surface.drawLine(left, ypos, right, ypos, LINE_WIDTH);
        }

        // draw lines from left to right for each horizontal grid line
        for (int x = 0; x <= game.mapSize; x++) {
            float xpos = x*cellSize + 1;
            surface.drawLine(xpos, top, xpos, bottom, LINE_WIDTH);
        }
    }

    public float getCellCenter(int cellCoord) {
        return cellCoord*cellSize + cellSize/2 + LINE_WIDTH/2;
    }
}
