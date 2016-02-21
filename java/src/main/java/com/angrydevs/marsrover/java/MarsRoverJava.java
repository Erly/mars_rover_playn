package com.angrydevs.marsrover.java;

import playn.java.LWJGLPlatform;

import com.angrydevs.marsrover.core.MarsRover;

public class MarsRoverJava {

  public static void main (String[] args) {
    LWJGLPlatform.Config config = new LWJGLPlatform.Config();
    // use config to customize the Java platform, if needed
    LWJGLPlatform plat = new LWJGLPlatform(config);
    new MarsRover(plat);
    plat.start();
  }
}
