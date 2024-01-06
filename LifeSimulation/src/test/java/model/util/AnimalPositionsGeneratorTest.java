package model.util;

import model.Animal;
import model.Vector2d;
import model.WorldMap;
import org.junit.jupiter.api.Test;

public class AnimalPositionsGeneratorTest {
    @Test
    public void TestSelectRandomPositions(){
        int animalsToPlace = 10;
        AnimalPositionsGenerator animalsPositions = new AnimalPositionsGenerator(10, 10, animalsToPlace);
        for(Vector2d position: animalsPositions){
            System.out.println(position);
        }
    }
}
