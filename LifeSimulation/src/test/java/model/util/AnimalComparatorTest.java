package model.util;

import model.Animal;
import model.Genome;
import model.Mutation;
import model.Vector2d;
import model.enums.MapDirection;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Random;
import static org.junit.jupiter.api.Assertions.*;

public class AnimalComparatorTest {
    @Test
    public void Test(){
        AnimalComparator ac = new AnimalComparator();
        Random random = new Random();
        Genome g1 = new Genome(10, new Mutation(0,0), random);
        ArrayList<Integer> g1List = g1.getGenome();
        System.out.println(g1List);

        Genome g2 = new Genome(10, new Mutation(0,0), random);
        ArrayList<Integer> g2List = g2.getGenome();
        System.out.println(g2List);

        Animal a1 = new Animal(new Vector2d(0,0), MapDirection.NORTH, g1, 0, 50);
        Animal a2 = new Animal(new Vector2d(0,0), MapDirection.NORTH, g2, 0, 1);
        Animal a3 = new Animal(new Vector2d(0,0), MapDirection.NORTH, g2, 0, 10);
        Animal a4 = new Animal(new Vector2d(0,0), MapDirection.NORTH, g2, 0, 999);
        Animal a5 = new Animal(new Vector2d(0,0), MapDirection.NORTH, g2, 0, 49);

        ArrayList<Animal> animals = new ArrayList<>();
        animals.add(a1);
        animals.add(a2);
        animals.add(a3);
        animals.add(a4);
        animals.add(a5);

        animals.sort(ac);

        System.out.println(animals);

        ArrayList<Animal> expectedAnimals = new ArrayList<Animal>();

        expectedAnimals.add(a4);
        expectedAnimals.add(a1);
        expectedAnimals.add(a5);
        expectedAnimals.add(a3);
        expectedAnimals.add(a2);

        assertEquals(expectedAnimals, animals);
    }

}
