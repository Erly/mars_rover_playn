package com.angrydevs.marsrover.view;

import com.angrydevs.marsrover.model.Position;
import com.angrydevs.marsrover.sprites.Sprite;
import com.angrydevs.marsrover.sprites.SpriteLoader;
import playn.core.Platform;
import playn.scene.GroupLayer;
import playn.scene.Layer;
import react.Slot;

/**
 * Created by Erlan on 22/02/2016.
 */
public class MarsRoverView {

    public static String IMAGE = "sprites/robo_tank_sprites.png";
    public static String JSON = "sprites/robo_tank_sprite.json";

    private final Sprite sprite;
    private boolean hasLoaded = false; // set to true when resources have loaded and we can update

    public MarsRoverView(Platform plat, final GroupLayer groupLayer, Position initialPosition) {
        sprite = SpriteLoader.getSprite(plat, IMAGE, JSON);
        sprite.layer.setOrigin(Layer.Origin.CENTER);
        sprite.layer.setTranslation(initialPosition.x, initialPosition.y);

        sprite.state.onSuccess(new Slot<Sprite>() {
            @Override public void onEmit(Sprite sprite) {
                sprite.setSprite(0);
                groupLayer.add(sprite.layer);
                hasLoaded = true;
            }
        });
    }

    public void move(Position pos) {
        if (sprite.layer.tx() == pos.x) {
            if (sprite.layer.ty() > pos.y) {
                sprite.setSprite("sprite_up");
            } else {
                sprite.setSprite("sprite_down");
            }
        } else if (sprite.layer.tx() > pos.x) {
            sprite.setSprite("sprite_left");
        } else {
            sprite.setSprite("sprite_right");
        }
        sprite.layer.setTranslation(pos.x, pos.y);
    }
}
