package model.util;

public class Energy {
    private final int initialAnimalEnergy;
    private final int grassEnergy;
    private final int breedReady;
    private final int breedLost;
    
    public Energy(int energyPerOneGrass, int breedEnergyLost, int breedReadyEnergy, int initialAnimalEnergy){
        this.grassEnergy = energyPerOneGrass;
        this.breedLost = breedEnergyLost;
        this.breedReady = breedReadyEnergy;
        this.initialAnimalEnergy = initialAnimalEnergy;
    }

    public int getGrassEnergy() {
        return grassEnergy;
    }

    public int getBreedReady() {
        return breedReady;
    }

    public int getBreedLost() {
        return breedLost;
    }

    public int getInitialAnimalEnergy() {
        return initialAnimalEnergy;
    }
}
