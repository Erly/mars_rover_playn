package com.angrydevs.marsrover.model;

import java.util.ArrayList;

/**
 * Created by Erlan on 03/03/2016.
 */
public class Map {

    public static Size size = new Size(10, 10);
    public static GameObject mainCharacter;
    private static ArrayList<Obstacle> obstacles = new ArrayList<>();

    private Map(){}

    public static boolean scan(Position position) {
        if (position.x < 0 || position.x >= size.width ||
                position.y < 0 || position.y >= size.height)
            throw new IndexOutOfBoundsException();

        if (mainCharacter != null && mainCharacter.position.equals(position))
            return true;

        for (int i = obstacles.size() - 1; i >= 0; i--) {
            if (obstacles.get(i).position.equals(position)) return true;
        }

        return false;
    }

    public static void addObstacle(Obstacle obstacle) {
        if (scan(obstacle.position))
            throw new IllegalArgumentException("There is already an object on that same position.");

        obstacles.add(obstacle);
    }

    public static void clear() {
        mainCharacter = null;
        obstacles.clear();
    }
}
