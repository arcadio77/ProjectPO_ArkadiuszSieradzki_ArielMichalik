package model;
import model.util.Energy;

public class Simulation extends SimulationGivenData{

    public Simulation(){
        super(10, 10, 10, 10, 8, 5, 1,
                2, 1, 10, 1, 2);
    }

    public void run(){
        Energy energy = new Energy(grassEnergy, breedLostEnergy, breedReadyEnergy, initialAnimalEnergy);
        WorldMap map = new WorldMap(width, height, initialAnimalsNumber, initialAnimalsNumber, energy, minMutationNum,
                maxMutationNum, genomeLength, numOfGrassGrowingDaily);
        OneCycle oneDay = new OneCycle(map);
        System.out.println(map);

        for(int i=0; i < 8; i++){
            oneDay.runOneCycle();
            System.out.println(map);
        }

    }

}
