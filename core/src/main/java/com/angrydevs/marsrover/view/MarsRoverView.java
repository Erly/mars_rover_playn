package com.angrydevs.marsrover.view;

import com.angrydevs.marsrover.model.Position;
import com.angrydevs.marsrover.sprites.Sprite;
import com.angrydevs.marsrover.sprites.SpriteLoader;
import com.angrydevs.marsrover.util.Constants;
import playn.core.Platform;
import playn.scene.GroupLayer;
import playn.scene.Layer;
import react.Slot;

public class MarsRoverView {

    public static String IMAGE = "sprites/robo_tank_sprites.png";
    public static String JSON = "sprites/robo_tank_sprite.json";

    private final Sprite sprite;

    public MarsRoverView(Platform plat, final GroupLayer groupLayer, Position initialPosition) {
        sprite = SpriteLoader.getSprite(plat, IMAGE, JSON);
        sprite.layer.setOrigin(Layer.Origin.CENTER);
        sprite.layer.setTranslation(initialPosition.x, initialPosition.y);

        sprite.state.onSuccess(new Slot<Sprite>() {
            @Override public void onEmit(Sprite sprite) {
                sprite.setSprite(0);
                groupLayer.add(sprite.layer);
            }
        });
    }

    public void move(Position pos) {
        sprite.layer.setTranslation(pos.x, pos.y);
    }

    public void turn(Constants.Direction dir) {
        switch (dir) {
            case UP:
                sprite.setSprite("sprite_up");
                break;
            case RIGHT:
                sprite.setSprite("sprite_right");
                break;
            case DOWN:
                sprite.setSprite("sprite_down");
                break;
            case LEFT:
                sprite.setSprite("sprite_left");
                break;
        }
    }
}
