package com.angrydevs.marsrover.view;

import com.angrydevs.marsrover.core.MarsRoverGame;
import com.angrydevs.marsrover.model.Obstacle;
import com.angrydevs.marsrover.model.Position;
import com.angrydevs.marsrover.model.Size;
import com.angrydevs.marsrover.util.Constants;
import playn.core.Image;
import playn.core.Texture;
import playn.scene.GroupLayer;
import playn.scene.ImageLayer;
import playn.scene.Layer;
import pythagoras.f.IDimension;
import react.RList;
import react.SignalView;
import react.Slot;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class GameView extends GroupLayer {
    private final MarsRoverGame game;
    private final MapView mapView;
    private final GroupLayer obstacleGroup = new GroupLayer();
    private final Map<Position, ImageLayer> obstacleViews = new HashMap<>();
    private final Map<String, Texture> textureMap = new HashMap<>();

    private MarsRoverView marsRoverView;

    public GameView (MarsRoverGame game, IDimension viewSize) {
        this.game = game;
        this.mapView = new MapView(game.map, viewSize);
        addCenterAt(mapView, viewSize.width()/2, viewSize.height()/2);
        addAt(obstacleGroup, mapView.tx(), mapView.ty());

        game.map.mainCharacterPosition.connect(new Slot<Position>() {
            @Override
            public void onEmit(Position position) {
                moveCharacter(position);
            }
        });

        game.map.mainCharacterDirection.connect(new Slot<Constants.Direction>() {
            @Override
            public void onEmit(Constants.Direction direction) {
                turnCharacter(direction);
            }
        });

        game.map.obstacles.connect(new RList.Listener<Obstacle>() {
            @Override
            public void onRemove(Obstacle obstacle) {
                clearObstacle(obstacle);
            }

            @Override
            public void onAdd(Obstacle obstacle) {
                addObstacle(obstacle);
            }
        });

    }

    @Override public void close () {
        super.close();
        Collection<Texture> textures = textureMap.values();
        for (Texture tex : textures) {
            tex.close();
        }
        textures.clear();
    }

    private void addObstacle(Obstacle obstacle) {
        ImageLayer obstacleView = new ImageLayer(getTexture(obstacle));
        obstacleView.setOrigin(Layer.Origin.CENTER);
        obstacleGroup.addAt(obstacleView, mapView.getCellCenter((int) obstacle.getPosition().x), mapView.getCellCenter((int) obstacle.getPosition().y));
        obstacleViews.put(obstacle.getPosition(), obstacleView);
    }

    private void clearObstacle(Obstacle obstacle) {
        ImageLayer obstacleVIew = obstacleViews.remove(obstacle.getPosition());
        if (obstacleVIew != null) obstacleVIew.close();
    }

    private void moveCharacter(Position at) {
        Position position = new Position(mapView.getCellCenter((int) at.x), mapView.getCellCenter((int) at.y));
        if (marsRoverView == null) {
            marsRoverView = new MarsRoverView(game.plat, obstacleGroup, position);
        } else {
            marsRoverView.move(position);
        }
    }

    private void turnCharacter(Constants.Direction direction) {
        if (marsRoverView != null) marsRoverView.turn(direction);
    }

    private Texture getTexture(Obstacle obstacle) {
        final String obstacleClassName = obstacle.getClass().getSimpleName();
        Texture existingTexture = textureMap.get(obstacleClassName);
        if (existingTexture != null) return existingTexture;

        Size obstacleSize = obstacle.getSize();
        float hSize = mapView.cellSize*obstacleSize.width - 2;
        float vSize = mapView.cellSize*obstacleSize.height - 2;
        final Texture texture = game.plat.graphics().createTexture(hSize, vSize, Texture.Config.UNMANAGED);
        game.plat.assets().getImage("images/rock.png").state.onSuccess(new SignalView.Listener<Image>() {
            @Override
            public void onEmit(Image image) {
                texture.update(image);
            }
        });
        onDisposed(texture.disposeSlot());
        textureMap.put(obstacleClassName, texture);
        return texture;
    }
}
