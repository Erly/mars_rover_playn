package com.angrydevs.marsrover.core;

import com.angrydevs.marsrover.interfaces.ICommandParser;
import com.angrydevs.marsrover.util.MarsRoverCommandParser;
import com.google.inject.AbstractModule;

public class MarsRoverModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(ICommandParser.class).to(MarsRoverCommandParser.class);
    }
}
