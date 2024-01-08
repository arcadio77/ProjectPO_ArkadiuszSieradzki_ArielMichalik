package model.util;
import model.Vector2d;
import model.WorldMap;
import org.junit.jupiter.api.Test;

import java.util.Random;

public class AnimalPositionsGeneratorTest {
    @Test
    public void TestSelectRandomPositions(){
        WorldMap map = new WorldMap(10, 10);
        int animalsToPlace = 10;
        AnimalPositionsGenerator animalsPositions = new AnimalPositionsGenerator(map, animalsToPlace);
        for(Vector2d position: animalsPositions){
            System.out.println(position);
        }
    }
}
