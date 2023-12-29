package model;

public class SimulationGivenData {
    protected final int width;
    protected final int height;
    protected final int initialAnimalsNumber;
    protected final int initialGrassNumber;
    protected final int initialAnimalEnergy;
    protected final int numOfGrassGrowingDaily;
    protected final int grassEnergy;
    protected final int breedReadyEnergy;
    protected final int breedLostEnergy;
    protected final int genomeLength;
    protected final int minMutationNum;
    protected final int maxMutationNum;



    public SimulationGivenData(int width, int height, int animalsCnt, int grassCnt, int initialAnimalEnergy,
                               int numOfGrassGrowingDaily, int grassEnergy, int breedReadyEnergy, int breedLostEnergy,
                               int genomeLength, int minMutationNum, int maxMutationNum) {
        this.width = width;
        this.height = height;
        this.initialAnimalsNumber = animalsCnt;
        this.initialGrassNumber = grassCnt;
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
