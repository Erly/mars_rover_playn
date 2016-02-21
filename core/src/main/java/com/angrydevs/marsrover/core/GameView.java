package com.angrydevs.marsrover.core;

import playn.core.Image;
import playn.core.Texture;
import playn.scene.GroupLayer;
import playn.scene.ImageLayer;
import playn.scene.Layer;
import pythagoras.f.IDimension;
import react.RList;
import react.SignalView;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Erlan on 21/02/2016.
 */
public class GameView extends GroupLayer {
    private final MarsRover game;
    private final MapView mapView;
    private final GroupLayer obstacleGroup = new GroupLayer();
    private final Map<MarsRover.Coord, ImageLayer> obstacleViews = new HashMap<>();
    final Texture obstacleTex;

    public GameView (MarsRover game, IDimension viewSize) {
        this.game = game;
        this.mapView = new MapView(game, viewSize);
        addCenterAt(mapView, viewSize.width()/2, viewSize.height()/2);
        addAt(obstacleGroup, mapView.tx(), mapView.ty());

        float size = mapView.cellSize - 2, hsize = size/2;
        obstacleTex = game.plat.graphics().createTexture(size, size, Texture.Config.UNMANAGED);
        game.plat.assets().getImage("images/rock.png").state.onSuccess(new SignalView.Listener<Image>() {
            @Override
            public void onEmit(Image image) {
                obstacleTex.update(image);
            }
        });

        game.obstacles.connect(new RList.Listener<MarsRover.Coord>() {
            @Override
            public void onRemove(MarsRover.Coord coord) {
                clearObstacle(coord);
            }

            @Override
            public void onAdd(MarsRover.Coord coord) {
                addObstacle(coord);
            }
        });

        onDisposed(obstacleTex.disposeSlot());
    }

    @Override public void close () {
        super.close();
        obstacleTex.close();
    }

    private void addObstacle(MarsRover.Coord at) {
        ImageLayer obstacleView = new ImageLayer(obstacleTex);
        obstacleView.setOrigin(Layer.Origin.CENTER);
        obstacleGroup.addAt(obstacleView, mapView.getCellCenter(at.x), mapView.getCellCenter(at.y));
        obstacleViews.put(at, obstacleView);
    }

    private void clearObstacle(MarsRover.Coord at) {
        ImageLayer obstacleVIew = obstacleViews.remove(at);
        if (obstacleVIew != null) obstacleVIew.close();
    }
}
