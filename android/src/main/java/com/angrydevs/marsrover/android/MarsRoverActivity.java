package com.angrydevs.marsrover.android;

import playn.android.GameActivity;

import com.angrydevs.marsrover.core.MarsRover;

public class MarsRoverActivity extends GameActivity {

  @Override public void main () {
    new MarsRover(platform());
  }
}
