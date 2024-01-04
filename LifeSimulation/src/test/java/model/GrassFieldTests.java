package model;

import model.enums.MapDirection;
import model.util.Energy;



public class GrassFieldTests {

    public static void main(String[] args) {
        GrassField gF = new GrassField(10, 10, 10, new Energy(1, 2, 3, 5), 5, 5, 10,
                1);

        Genome g1 = new Genome(10, new Mutation(0, 0));
        Genome g2 = new Genome(10, new Mutation(0, 0));

        Animal a1 = new Animal(new Vector2d(0, 0), MapDirection.NORTH, g1, 0, 5);
        Animal a2 = new Animal(new Vector2d(0, 0), MapDirection.NORTH, g2, 0, 5);

        gF.place(a1);
        gF.place(a2);
        System.out.println(gF.cntAnimalsOnGivenPosition(new Vector2d(0, 0)));
        System.out.println(gF.cntGrassesOnGivenPosition(new Vector2d(1,0)));

        Animal a3 = new Animal(new Vector2d(0, 0), MapDirection.NORTH, g2, 0, 5);
        gF.place(a3);
        System.out.println(gF.cntAnimalsOnGivenPosition(new Vector2d(0, 0)));
        System.out.println(gF.cntGrassesOnGivenPosition(new Vector2d(1,0)));
    }
}
