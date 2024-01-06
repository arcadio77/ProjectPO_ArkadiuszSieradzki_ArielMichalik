package model.util;
import model.Vector2d;
import org.junit.jupiter.api.Test;

import java.util.Random;

public class AnimalPositionsGeneratorTest {
    @Test
    public void TestSelectRandomPositions(){
        int animalsToPlace = 10;
        AnimalPositionsGenerator animalsPositions = new AnimalPositionsGenerator(10, 10, animalsToPlace, new Random());
        for(Vector2d position: animalsPositions){
            System.out.println(position);
        }
    }
}
