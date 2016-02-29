package com.angrydevs.marsrover.android;

import playn.android.GameActivity;

import com.angrydevs.marsrover.core.MarsRoverGame;

public class MarsRoverActivity extends GameActivity {

  @Override public void main () {
    new MarsRoverGame(platform());
  }
}
