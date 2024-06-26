package model;

import model.enums.MapDirection;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class AnimalTest {

    @Test
    void testMoveWithinBounds() {
        Vector2d initialPosition = new Vector2d(2, 3);
        Genome genome = new Genome(10, new Mutation(0,0), new Random());
        int geneId = 0;
        int initialEnergy = 10;

        Animal animal = new Animal(initialPosition, MapDirection.NORTH, genome, geneId, initialEnergy);

        Vector2d lowerLeft = new Vector2d(0, 0);
        Vector2d upperRight = new Vector2d(5, 5);

        for(int i = 0; i < 5; i++) {
            animal.move(5, 5);
        }

        assertTrue(animal.position().isBiggerOrEqual(lowerLeft));
        assertTrue(animal.position().isSmallerOrEqual(upperRight));

        assertEquals(initialEnergy - 5, animal.getEnergy());
        assertEquals(5, animal.getAge());
        assertEquals((geneId + 5) % genome.getGenome().size(), animal.getGeneId());
    }

    @Test
    void testMoveVertically() {
        Vector2d initialPosition = new Vector2d(2, 3);
        ArrayList<Integer> genomeList = new ArrayList<>(0);
        genomeList.add(0);
        Genome genome = new Genome(1, genomeList);
        int geneId = 0;
        int initialEnergy = 10;

        Animal animal = new Animal(initialPosition, MapDirection.NORTH, genome, geneId, initialEnergy);


        animal.move(5, 5);

        assertNotEquals(3, animal.position().y());
    }

    @Test
    void testMoveHorizontally() {
        Vector2d initialPosition = new Vector2d(2, 3);
        ArrayList<Integer> genomeList = new ArrayList<>(0);
        genomeList.add(2);
        Genome genome = new Genome(1, genomeList);
        int geneId = 0;
        int initialEnergy = 10;

        Animal animal = new Animal(initialPosition, MapDirection.NORTH, genome, geneId, initialEnergy);

        animal.move(5, 5);

        assertNotEquals(2, animal.position().x());
    }

    @Test
    void testMoveBothVerticallyAndHorizontally() {
        ArrayList<Integer> genomeList = new ArrayList<>(0);
        genomeList.add(1);
        Genome genome = new Genome(1, genomeList);
        Animal animal = new Animal(new Vector2d(2, 3), MapDirection.NORTH, genome, 0, 10);

        animal.move(5, 5);

        assertNotEquals(2, animal.position().x());
        assertNotEquals(3, animal.position().y());
    }


}