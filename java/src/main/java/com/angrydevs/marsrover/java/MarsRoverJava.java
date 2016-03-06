package com.angrydevs.marsrover.java;

import playn.java.LWJGLPlatform;

import com.angrydevs.marsrover.core.MarsRoverGame;

public class MarsRoverJava {

    public static void main (String[] args) {
        LWJGLPlatform.Config config = new LWJGLPlatform.Config();
        // use config to customize the Java platform, if needed
        config.width = 1280;
        config.height = 720;
        //config.fullscreen = true;
        LWJGLPlatform plat = new LWJGLPlatform(config);
        new MarsRoverGame(plat);
        plat.start();
    }
}
