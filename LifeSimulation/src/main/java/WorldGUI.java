import javafx.application.Application;
import model.*;
import model.enums.MapDirection;
import model.util.AnimalComparator;
import view.SimulationApp;

import java.util.ArrayList;
import java.util.Random;


public class WorldGUI {
    public static void main(String[] args) {

        //Application.launch(SimulationApp.class, args);

        WorldMap map1 = new WorldMap(11);
/*
        map1.initBounds();
        map1.initPutAnimals();
        map1.initPutGrasses(10);

        OneDayRunner ODR = new OneDayRunner(map1);

        ODR.runOneDay();
        ODR.runOneDay();
        ODR.runOneDay();
        ODR.runOneDay();
        ODR.runOneDay();
        ODR.runOneDay();
        ODR.runOneDay();
        ODR.runOneDay();
        ODR.runOneDay();
        ODR.runOneDay();
*/
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
        Animal a5 = new Animal(new Vector2d(0,0), MapDirection.NORTH, g2, 0, 50);

        ArrayList<Animal> animals = new ArrayList<>();
        animals.add(a1);
        animals.add(a2);
        animals.add(a3);
        animals.add(a4);
        animals.add(a5);



    }
}