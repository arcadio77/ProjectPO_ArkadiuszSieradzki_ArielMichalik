package model;
import model.util.Energy;

import java.util.Random;

public class Simulation extends SimulationGivenData{

    private final Random random;

    public Simulation(){
        super(10, 10, 10, 10, 8, 5, 1,
                2, 1, 10, 1, 2, 123456789L);
        this.random = new Random(seed);
    }

    public void run(){
        Energy energy = new Energy(grassEnergy, breedLostEnergy, breedReadyEnergy, initialAnimalEnergy);
        WorldMap map = new WorldMap(width, height, initialAnimalsNumber, initialAnimalsNumber, energy, minMutationNum,
                maxMutationNum, genomeLength, numOfGrassGrowingDaily, this.random);

        OneDayRunner oneDay = new OneDayRunner(map);
        System.out.println(map);

        for(int i=0; i < 100; i++){
            oneDay.runOneDay();
            System.out.println(map);
        }

    }

}
