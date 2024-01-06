package model.util;

import model.Vector2d;
import model.WorldMap;
import org.junit.jupiter.api.Test;


public class GrassPositionsGeneratorTest {
    @Test
    public void TestSelectRandomPositions(){
        WorldMap map = new WorldMap(10, 10);
        int grassToPlace = 10;
        //TODO add getJungleBounds to WorldMap
        GrassPositionsGenerator grassPositions = new GrassPositionsGenerator(10, 10, grassToPlace, map.setJungleBounds(), map.getPlants());
        for(Vector2d position: grassPositions){
            System.out.println(position);
        }
    }

}
