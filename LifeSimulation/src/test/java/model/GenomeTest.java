package model;

import model.enums.MapDirection;
import org.junit.jupiter.api.Test;

import javax.naming.InsufficientResourcesException;
import java.util.ArrayList;
import java.util.Random;
import static org.junit.jupiter.api.Assertions.*;

public class GenomeTest {

    @Test
    public void Test(){
        Random random = new Random();
        Genome g1 = new Genome(10, new Mutation(0,0), random);
        ArrayList<Integer> g1List = g1.getGenome();
        System.out.println(g1List);

        Genome g2 = new Genome(10, new Mutation(0,0), random);
        ArrayList<Integer> g2List = g2.getGenome();
        System.out.println(g2List);


        Animal a1 = new Animal(new Vector2d(0,0), MapDirection.NORTH, g1, 0, 5);
        Animal a2 = new Animal(new Vector2d(0,0), MapDirection.NORTH, g2, 0, 5);

        a1.eat(5);
        a2.eat(5);

        Genome g3 = new Genome(10, a1, a2, new Mutation(0,0), random, false);

        ArrayList<Integer> g3List = g3.getGenome();
        System.out.println(g3List);

        Animal child1 = new Animal(new Vector2d(0,0), MapDirection.NORTH, g3, 0, 5);

        a1.breed(child1, 2);
        a2.breed(child1, 2);

        ArrayList<Integer> expectedGenome = new ArrayList<>(g1.getGenome().subList(0, 5));
        expectedGenome.addAll(g2.getGenome().subList(5,10));

        assertEquals(expectedGenome, g3.getGenome());
        
    }

}
