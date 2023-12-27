package model.util;

public class Energy {
    private final int PerOneGrass;
    private final int breedReady;
    private final int breedLost;
    
    public Energy(int energyPerOneGrass, int breedEnergyLost, int breedReadyEnergy){
        this.PerOneGrass = energyPerOneGrass;
        this.breedLost = breedEnergyLost;
        this.breedReady = breedReadyEnergy;
    }

    public int getPerOneGrass() {
        return PerOneGrass;
    }

    public int getBreedReady() {
        return breedReady;
    }

    public int getBreedLost() {
        return breedLost;
    }
}
