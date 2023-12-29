package model;

public class SimulationGivenData {
    private final int width;
    private final int height;
    private final int initialAnimalsNumber;
    private final int initialGrassNumber;
    private final int initialAnimalEnergy;
    private final int numOfGrassGrowingDaily;
    private final int grassEnergy;
    private final int breedReadyEnergy;
    private final int breedLostEnergy;
    private final int genomeLength;
    private final int minMutationNum;
    private final int maxMutationNum;



    public SimulationGivenData(int width, int height, int animalsCnt, int grassCnt, int initialAnimalEnergy, int numOfGrassGrowingDaily, int grassEnergy, int breedReadyEnergy, int breedLostEnergy, int genomeLength, int minMutationNum, int maxMutationNum) {
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
