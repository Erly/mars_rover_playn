package com.angrydevs.marsrover.html;

import com.google.gwt.core.client.EntryPoint;
import playn.html.HtmlPlatform;
import com.angrydevs.marsrover.core.MarsRoverGame;

public class MarsRoverHtml implements EntryPoint {

  @Override public void onModuleLoad () {
    HtmlPlatform.Config config = new HtmlPlatform.Config();
    // use config to customize the HTML platform, if needed
    HtmlPlatform plat = new HtmlPlatform(config);
    plat.assets().setPathPrefix("marsrover/");
    new MarsRoverGame(plat);
    plat.start();
  }
}
