package com.angrydevs.marsrover.core;

import com.angrydevs.marsrover.interfaces.ICommandParser;
import com.angrydevs.marsrover.model.*;
import com.angrydevs.marsrover.util.Constants;
import com.angrydevs.marsrover.view.GameView;
import com.angrydevs.marsrover.view.MapView;
import com.google.inject.Guice;
import com.google.inject.Injector;
import playn.core.*;
import playn.scene.ImageLayer;
import playn.scene.Layer;
import playn.scene.SceneGame;
import pythagoras.f.IDimension;
import react.SignalView;
import react.Slot;
import react.Value;

import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class MarsRoverGame extends SceneGame {

    public final Font gameFont = new playn.core.Font("Sans serif", Font.Style.PLAIN, 12);
    public final TextFormat gameTextFormat = new TextFormat(gameFont);
    public final Map map;
    public int numberOfObstacles = 12;
    /*public final Value<Piece> turn = Value.create(null);*/

    private final Injector injector;
    private final ICommandParser commandParser;

    private Value<String> errorMessage = Value.create(null);

    public MarsRoverGame(final Platform plat) {
        super(plat, Constants.FrameTime.SIXTY_FPS);
        injector = Guice.createInjector(new MarsRoverModule());
        commandParser = injector.getInstance(ICommandParser.class);
        map = new Map(new Size(25, 20));

        final IDimension size = plat.graphics().viewSize;

        rootLayer.add(new Layer() {
            @Override
            protected void paintImpl(Surface surface) {
                surface.setFillColor(Constants.Colors.MARS_RED).fillRect(0, 0, size.width(), size.height());
            }
        });

        rootLayer.addCenterAt(new MapView(map, size), size.width()/2, size.height()/2);
        rootLayer.add(new GameView(this, size));

        reset();

        plat.input().keyboardEvents.connect(new Keyboard.KeySlot() {
            @Override public void onEmit (Keyboard.KeyEvent event) {
                if (event.down) {
                    switch (event.key) {
                        case SPACE:
                            promptForCommands(plat);
                            break;
                        case R:
                            reset();
                            break;
                        case Q:
                        case ESCAPE:
                            System.exit(0);
                            break;
                        default:
                            break;
                    }
                }
            }
        });
        //promptForCommands(plat);

        errorMessage.connect(new Slot<String>() {
            @Override
            public void onEmit(String errorMsg) {
                if (errorMsg == null) return;
                TextLayout textLayout = plat.graphics().layoutText(errorMsg, gameTextFormat);
                Canvas image = plat.graphics().createCanvas(((int)Math.ceil(textLayout.size.width())),
                        (int)Math.ceil(textLayout.size.height()));
                image.fillText(textLayout, 0, 0);

                final Layer textLayer = new ImageLayer(image.toTexture());
                rootLayer.add(textLayer);
                Timer timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        rootLayer.remove(textLayer);
                    }
                }, 5000);
                errorMessage.update(null);
            }
        });
    }

    private void promptForCommands(Platform plat) {
        plat.input().getText(Keyboard.TextType.DEFAULT, "Introduce commands for the Mars Rover:", null)
                .onSuccess(new SignalView.Listener<String>() {
                    @Override
                    public void onEmit(String commandsStr) {
                        ArrayList<Constants.Movement> movements = commandParser.parseCommands(commandsStr);
                        processMovements(movements);
                    }
                });
    }

    private void processMovements(final ArrayList<Constants.Movement> movements) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (Constants.Movement movement : movements) {
                    try {
                        map.moveMainCharacter(movement);
                        Thread.sleep(10 * Constants.FrameTime.SIXTY_FPS);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        errorMessage.update("Can't advance further because an obstacle was encountered.");
                    }
                }
            }
        }).start();
    }

    private void reset() {
        map.clear();
        Random rand = new Random(System.currentTimeMillis());
        int i = 0, x, y;

        x = rand.nextInt(map.size.width);
        y = rand.nextInt(map.size.height);
        map.setMainCharacter(new MarsRover(), new Position(x, y));

        while (i < numberOfObstacles) {
            x = rand.nextInt(map.size.width);
            y = rand.nextInt(map.size.height);
            Position position = new Position(x, y);
            if (!map.scan(position)) {
                map.addObstacle(new Rock(position));
                i++;
            }
        }
    }
}
