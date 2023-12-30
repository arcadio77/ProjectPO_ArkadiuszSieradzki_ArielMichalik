package model;

import model.enums.MapDirection;
import model.util.Energy;

import java.util.ArrayList;
import java.util.Random;

public class GenomeAndAnimalTest {
    public static void main(String[] args){
        Genome g1 = new Genome(10, new Mutation(0,0));
        ArrayList<Integer> g1List = g1.getGenome();
        System.out.println(g1List);

        Genome g2 = new Genome(10, new Mutation(0,0));
        ArrayList<Integer> g2List = g2.getGenome();
        System.out.println(g2List);


        Animal a1 = new Animal(new Vector2d(0,0), MapDirection.NORTH, g1, 0, 5);
        Animal a2 = new Animal(new Vector2d(0,0), MapDirection.NORTH, g2, 0, 5);

        a1.eat(10);
        a2.eat(5);

        Genome g3 = new Genome(10, a1, a2, new Mutation(0,0));

        ArrayList<Integer> g3List = g3.getGenome();
        System.out.println(g3List);

        Animal child1 = new Animal(new Vector2d(0,0), MapDirection.NORTH, g3, 0, 5);

        a1.breed(child1, 2);
        a2.breed(child1, 2);

        GrassField gF = new GrassField(10, 10, 10, new Energy(1, 2, 3, 5), 5, 5, 10,
                new Random(), 1, 0, 1);

        gF.place(a1);
        gF.place(a2);
        gF.place(child1);
        
    }
}
