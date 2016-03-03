package com.angrydevs.marsrover.tests;

import com.angrydevs.marsrover.model.Map;
import com.angrydevs.marsrover.model.Obstacle;
import com.angrydevs.marsrover.model.Position;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Created by Erlan on 03/03/2016.
 */
public class MapTests {

    private static Obstacle obstacle = new Obstacle(new Position(2,3));

    @BeforeClass
    public static void prepareTestData() {
        Map.addObstacle(obstacle);
    }

    @Test
    public void scanForObstaclesShouldReturnFalse() {
        for (int x = 0; x < Map.size.width; x++) {
            for (int y = 0; y < Map.size.height; y++) {
                if (x != obstacle.position.x || y != obstacle.position.y) {
                    Assert.assertFalse(Map.scan(new Position(x, y)));
                }
            }
        }
    }

    @Test
    public void scanForObstaclesShouldReturnTrue() {
        Assert.assertFalse(Map.scan(new Position(obstacle.position.x, obstacle.position.y)));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void scanForObstaclesShouldThrowIndexOutOfBounds() {
        Map.scan(new Position(11, 5));
    }

    @Test(expected = IllegalArgumentException.class)
    public void addingAnObstacleWhereThereIsAlreadyOneShouldThrowIllegalArgumentException() {
        Map.addObstacle(new Obstacle(obstacle.position));
    }
}
