package model;

public class SimulationGivenData {
    final int width;
    final int height;
    final int initialAnimalsNumber;
    final int initialGrassNumber;
    final int initialAnimalEnergy;
    final int numOfGrassGrowingDaily;
    final int grassEnergy;
    final int breedReadyEnergy;
    final int breedLostEnergy;
    final int genomeLength;
    final int minMutationNum;
    final int maxMutationNum;



    public SimulationGivenData(int width, int height, int initialAnimalsNumber, int initialGrassNumber, int initialAnimalEnergy,
                               int numOfGrassGrowingDaily, int grassEnergy, int breedReadyEnergy, int breedLostEnergy,
                               int genomeLength, int minMutationNum, int maxMutationNum) {
        this.width = width;
        this.height = height;
        this.initialAnimalsNumber = initialAnimalsNumber;
        this.initialGrassNumber = initialGrassNumber;
        this.initialAnimalEnergy = initialAnimalEnergy;
        this.numOfGrassGrowingDaily = numOfGrassGrowingDaily;
        this.grassEnergy = grassEnergy;
        this.breedReadyEnergy = breedReadyEnergy;
        this.breedLostEnergy = breedLostEnergy;
        this.genomeLength = genomeLength;
        this.minMutationNum = minMutationNum;
        this.maxMutationNum = maxMutationNum;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public int getInitialAnimalsNumber() {
        return initialAnimalsNumber;
    }

    public int getInitialGrassNumber() {
        return initialGrassNumber;
    }

    public int getInitialAnimalEnergy() {
        return initialAnimalEnergy;
    }

    public int getGrassEnergy() {
        return grassEnergy;
    }

    public int getBreedReadyEnergy() {
        return breedReadyEnergy;
    }

    public int getBreedLostEnergy() {
        return breedLostEnergy;
    }

    public int getGenomeLength() {
        return genomeLength;
    }

    public int getNumOfGrassGrowingDaily() {
        return numOfGrassGrowingDaily;
    }

    public int getMinMutationNum() {
        return minMutationNum;
    }

    public int getMaxMutationNum() {
        return maxMutationNum;
    }
}