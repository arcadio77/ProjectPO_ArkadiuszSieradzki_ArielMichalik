package model;

import model.enums.MapDirection;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Random;
import static org.junit.jupiter.api.Assertions.*;

public class GenomeTest {

    @Test
    public void testBreedWithDifferentData1() {
        testBreedWithDifferentData();
    }

    @Test
    public void testBreedWithDifferentData2() {
        testBreedWithDifferentData();
    }

    @Test
    public void testBreedWithDifferentData3() {
        testBreedWithDifferentData();
    }

    @Test
    public void testBreedWithDifferentData4() {
        testBreedWithDifferentData();
    }

    @Test
    public void testBreedWithDifferentData5() {
        testBreedWithDifferentData();
    }
    @Test
    public void testBreedWithDifferentData6() {
        testBreedWithDifferentData();
    }
    @Test
    public void testBreedWithDifferentData7() {
        testBreedWithDifferentData();
    }
    @Test
    public void testBreedWithDifferentData8() {
        testBreedWithDifferentData();
    }
    @Test
    public void testBreedWithDifferentData9() {
        testBreedWithDifferentData();
    }
    @Test
    public void testBreedWithDifferentData10() {
        testBreedWithDifferentData();
    }
    @Test
    public void testBreedWithDifferentData11() {
        testBreedWithDifferentData();
    }
    @Test
    public void testBreedWithDifferentData12() {
        testBreedWithDifferentData();
    }
    @Test
    public void testBreedWithDifferentData13() {
        testBreedWithDifferentData();
    }
    @Test
    public void testBreedWithDifferentData14() {
        testBreedWithDifferentData();
    }
    @Test
    public void testBreedWithDifferentData15() {
        testBreedWithDifferentData();
    }
    @Test
    public void testBreedWithDifferentData16() {
        testBreedWithDifferentData();
    }
    @Test
    public void testBreedWithDifferentData17() {
        testBreedWithDifferentData();
    }
    @Test
    public void testBreedWithDifferentData18() {
        testBreedWithDifferentData();
    }
    @Test
    public void testBreedWithDifferentData19() {
        testBreedWithDifferentData();
    }
    @Test
    public void testBreedWithDifferentData20() {
        testBreedWithDifferentData();
    }

    private void testBreedWithDifferentData() {
        Random random = new Random();

        Genome g1 = new Genome(10, new Mutation(0, 0), random);
        Genome g2 = new Genome(10, new Mutation(0, 0), random);

        Animal a1 = new Animal(new Vector2d(0, 0), MapDirection.NORTH, g1, 0, 5);
        Animal a2 = new Animal(new Vector2d(0, 0), MapDirection.NORTH, g2, 0, 5);

        a1.eat(5);
        a2.eat(5);

        Genome g3 = new Genome(10, a1, a2, new Mutation(0, 0), random, false);

        ArrayList<Integer> expectedGenome1 = new ArrayList<>(g1.getGenome().subList(0, 5));
        expectedGenome1.addAll(g2.getGenome().subList(5, 10));

        ArrayList<Integer> expectedGenome2 = new ArrayList<>(g2.getGenome().subList(0, 5));
        expectedGenome2.addAll(g1.getGenome().subList(5, 10));

        assertTrue(expectedGenome1.equals(g3.getGenome()) || expectedGenome2.equals(g3.getGenome()));
    }

}
