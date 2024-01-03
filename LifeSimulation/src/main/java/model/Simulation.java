package model;
import model.util.Energy;

public class Simulation extends SimulationGivenData{

    public Simulation(){
        super(10, 10, 10, 10, 3, 5, 1,
                2, 1, 10, 1, 2);
    }

    public void run(){
        Energy energy = new Energy(grassEnergy, breedLostEnergy, breedReadyEnergy, initialAnimalEnergy);
        GrassField map = new GrassField(width, height, initialAnimalsNumber, energy, minMutationNum, maxMutationNum,
                initialAnimalsNumber, numOfGrassGrowingDaily);
        System.out.println(map);
        OneCycle oneDay = new OneCycle(map);

        for(int i=0; i < 10; i++){
            oneDay.runOneCycle();
            System.out.println(map);
        }

    }

}
