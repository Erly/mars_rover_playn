package com.angrydevs.marsrover.view;

import com.angrydevs.marsrover.core.MarsRoverGame;
import com.angrydevs.marsrover.model.Position;
import playn.core.Image;
import playn.core.Texture;
import playn.scene.GroupLayer;
import playn.scene.ImageLayer;
import playn.scene.Layer;
import pythagoras.f.IDimension;
import react.RList;
import react.SignalView;
import react.Slot;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Erlan on 21/02/2016.
 */
public class GameView extends GroupLayer {
    private final MarsRoverGame game;
    private final MapView mapView;
    private final GroupLayer obstacleGroup = new GroupLayer();
    private final Map<MarsRoverGame.Coord, ImageLayer> obstacleViews = new HashMap<>();
    final Texture obstacleTex;
    private MarsRoverView marsRoverView;

    public GameView (MarsRoverGame game, IDimension viewSize) {
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

        game.roverCoords.connect(new Slot<MarsRoverGame.Coord>() {
            @Override
            public void onEmit(MarsRoverGame.Coord coord) {
                spawnRover(coord);
            }
        });

        game.obstaclesCoords.connect(new RList.Listener<MarsRoverGame.Coord>() {
            @Override
            public void onRemove(MarsRoverGame.Coord coord) {
                clearObstacle(coord);
            }

            @Override
            public void onAdd(MarsRoverGame.Coord coord) {
                addObstacle(coord);
            }
        });

        onDisposed(obstacleTex.disposeSlot());
    }

    @Override public void close () {
        super.close();
        obstacleTex.close();
    }

    private void addObstacle(MarsRoverGame.Coord at) {
        ImageLayer obstacleView = new ImageLayer(obstacleTex);
        obstacleView.setOrigin(Layer.Origin.CENTER);
        obstacleGroup.addAt(obstacleView, mapView.getCellCenter(at.x), mapView.getCellCenter(at.y));
        obstacleViews.put(at, obstacleView);
    }

    private void clearObstacle(MarsRoverGame.Coord at) {
        ImageLayer obstacleVIew = obstacleViews.remove(at);
        if (obstacleVIew != null) obstacleVIew.close();
    }

    public void spawnRover(MarsRoverGame.Coord at) {
        Position position = new Position(mapView.getCellCenter(at.x), mapView.getCellCenter(at.y));
        if (marsRoverView == null) {
            marsRoverView = new MarsRoverView(game.plat, obstacleGroup, position);
        } else {
            marsRoverView.move(position);
        }
    }
}
