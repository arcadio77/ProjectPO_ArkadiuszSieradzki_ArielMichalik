package model.util;

import model.Vector2d;
import model.WorldMap;
import org.junit.jupiter.api.Test;


public class GrassPositionsGeneratorTest {
    @Test
    public void TestSelectRandomPositions(){
        WorldMap map = new WorldMap(10, 10);
        int grassToPlace = 10;
        GrassPositionsGenerator grassPositions = new GrassPositionsGenerator(10, 10, grassToPlace, map.getJungleBounds(), map.getPlants(), map.getRandom());
        for(Vector2d position: grassPositions){
            System.out.println(position);
        }
    }

}
