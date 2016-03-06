package com.angrydevs.marsrover.view;

import com.angrydevs.marsrover.core.MarsRoverGame;
import com.angrydevs.marsrover.model.Map;
import com.angrydevs.marsrover.util.Constants;
import playn.core.Surface;
import playn.scene.Layer;
import pythagoras.f.IDimension;

/**
 * Created by Erlan on 21/02/2016.
 */
public class MapView extends Layer {
    private static final float LINE_WIDTH = 2;
    private final Map map;

    public final float cellSize;

    public MapView(Map map, IDimension viewSize) {
        this.map = map;
        this.cellSize = calculateCellSize(viewSize);
    }

    @Override public float width () { return cellSize * map.size.width + LINE_WIDTH; }
    @Override public float height () { return cellSize * map.size.height + LINE_WIDTH; }

    @Override
    protected void paintImpl(Surface surface) {
        surface.setFillColor(Constants.Colors.BLACK);
        float top = 0, bottom = height(), left = 0, right = width();

        for (int y = 0; y <= map.size.height; y++) {
            float ypos = y*cellSize + 1;
            surface.drawLine(left, ypos, right, ypos, LINE_WIDTH);
        }

        // draw lines from left to right for each horizontal grid line
        for (int x = 0; x <= map.size.width; x++) {
            float xpos = x*cellSize + 1;
            surface.drawLine(xpos, top, xpos, bottom, LINE_WIDTH);
        }
    }

    public float getCellCenter(int cellCoord) {
        return cellCoord*cellSize + cellSize/2 + LINE_WIDTH/2;
    }

    private float calculateCellSize(IDimension viewSize) {

        float maxBoardWidth = viewSize.width() - 20;
        float maxBoardHeight = viewSize.height() - 20;

        return (float) Math.min(Math.floor(maxBoardWidth / map.size.width),
                                Math.floor(maxBoardHeight / map.size.height));
    }
}
