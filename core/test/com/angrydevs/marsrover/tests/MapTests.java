package com.angrydevs.marsrover.tests;

import com.angrydevs.marsrover.model.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class MapTests {

    private Map map = new Map(new Size(10, 10));
    private Obstacle obstacle = new Rock(new Position(2,3));

    @Before
    public void prepareTestData() {
        map.addObstacle(obstacle);
    }

    @Test
    public void scanForObstaclesShouldReturnFalse() {
        for (int x = 0; x < map.size.width; x++) {
            for (int y = 0; y < map.size.height; y++) {
                if (x != obstacle.getPosition().x || y != obstacle.getPosition().y) {
                    Assert.assertFalse(map.scan(new Position(x, y)));
                }
            }
        }
    }

    @Test
    public void scanForObstaclesShouldReturnTrue() {
        Assert.assertTrue(map.scan(new Position(obstacle.getPosition().x, obstacle.getPosition().y)));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void scanForObstaclesShouldThrowIndexOutOfBounds() {
        map.scan(new Position(11, 5));
    }

    @Test(expected = IllegalArgumentException.class)
    public void addingAnObstacleWhereThereIsAlreadyOneShouldThrowIllegalArgumentException() {
        map.addObstacle(new Rock(obstacle.getPosition()));
    }
}
