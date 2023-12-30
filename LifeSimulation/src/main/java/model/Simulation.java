package model;

import model.util.Energy;
import java.util.Random;

public class Simulation extends SimulationGivenData{
    private GrassField map;
    private SimulationGivenData data;

    public Simulation(){
        super(10, 10, 10, 10, 5, 5, 1,
                2, 1, 10, 1, 2);
    }

    public void run(){
        Energy energy = new Energy(grassEnergy, breedLostEnergy, breedReadyEnergy, initialAnimalEnergy);
        GrassField map = new GrassField(width, height, initialAnimalsNumber, energy, minMutationNum, maxMutationNum,
                initialAnimalsNumber, new Random(), numOfGrassGrowingDaily, 0, 10);
        System.out.println(map); //that prints
        OneCycle oneDay = new OneCycle(map);
        oneDay.runOneCycle();  //here is bug
        System.out.println(map);
    }

}
