package model;
import model.util.Energy;

import java.util.Random;

public class Simulation extends SimulationGivenData implements Runnable{

    private final Random random;

    private final WorldMap map;

    public Simulation(WorldMap map){
        super(10, 10, 40, 50, 10, 5, 1,
                2, 1, 10, 1, 2, 123456789L,
                true, true);
        this.random = new Random(seed);
        this.map = map;
    }

    public Simulation(){
        super(10, 10, 40, 50, 10, 5, 1,
                2, 1, 10, 1, 2, 123456789L,
                true, true);
        this.random = new Random(seed);

        Energy energy = new Energy(grassEnergy, breedLostEnergy, breedReadyEnergy, initialAnimalEnergy);
        this.map = new WorldMap(width, height, initialAnimalsNumber, initialAnimalsNumber, energy, minMutationNum,
                maxMutationNum, genomeLength, numOfGrassGrowingDaily, this.random, this.useMutationSwapGene, this.useLifeGivingCorpses);
    }

    public void run(){

        //TODO still different variations of the map are printing - don't know why?
        OneDayRunner oneDay = new OneDayRunner(map);
        //System.out.println(map); -> console

        for(int i=0; i < 200; i++){
            oneDay.runOneDay();
            try {
                Thread.sleep(800);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            //System.out.println(map); -> console
        }

    }

}
