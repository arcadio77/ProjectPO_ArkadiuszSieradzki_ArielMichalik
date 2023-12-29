package model;

public class SimulationGivenData {
    private final int width;
    private final int height;
    int initialAnimalsNumber;
    int initialGrassNumber;
    private final int initialAnimalEnergy;
    private final int grassEnergy;
    private final int breedReadyEnergy;
    private final int breedLostEnergy;
    private final int genomeLength;



    public SimulationGivenData(int width, int height, int animalsCnt, int grassCnt, int initialAnimalEnergy, int grassEnergy, int breedReadyEnergy, int breedLostEnergy, int genomeLength) {
        this.width = width;
        this.height = height;
        this.initialAnimalsNumber = animalsCnt;
        this.initialGrassNumber = grassCnt;
        this.initialAnimalEnergy = initialAnimalEnergy;
        this.grassEnergy = grassEnergy;
        this.breedReadyEnergy = breedReadyEnergy;
        this.breedLostEnergy = breedLostEnergy;
        this.genomeLength = genomeLength;
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

}
