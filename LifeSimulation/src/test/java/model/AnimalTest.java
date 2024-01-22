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
            animal.move(lowerLeft, upperRight);
        }

        // Assert the new position is within the specified bounds
        assertTrue(animal.position().isBiggerOrEqual(lowerLeft));
        assertTrue(animal.position().isSmallerOrEqual(upperRight));

        // Assert the energy and other attributes are updated as expected
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

        Vector2d lowerLeft = new Vector2d(0, 0);
        Vector2d upperRight = new Vector2d(5, 5);

        animal.move(lowerLeft, upperRight);

        assertNotEquals(3, animal.position().y());
    }

    @Test
    void testMoveHorizontally() {
        Vector2d initialPosition = new Vector2d(2, 3);
        Genome genome = new Genome(10, new Mutation(0,0), new Random());
        int geneId = 0;
        int initialEnergy = 10;

        Animal animal = new Animal(initialPosition, MapDirection.NORTH, genome, geneId, initialEnergy);

        Vector2d lowerLeft = new Vector2d(0, 0);
        Vector2d upperRight = new Vector2d(5, 5);

        animal.move(lowerLeft, upperRight);

        assertNotEquals(2, animal.position().x());
    }

    @Test
    void testMoveBothVerticallyAndHorizontally() {
        Animal animal = new Animal(new Vector2d(2, 3), MapDirection.NORTH, new Genome(), 0, 10);

        // Move the animal both vertically and horizontally
        animal.move(new Vector2d(0, 0), new Vector2d(5, 5));

        // Assert the new position is different both in the x-axis and y-axis
        assertNotEquals(2, animal.position().x());
        assertNotEquals(3, animal.position().y());
    }


}