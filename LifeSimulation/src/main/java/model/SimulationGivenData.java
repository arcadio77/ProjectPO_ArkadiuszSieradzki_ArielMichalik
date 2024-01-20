package model;


public class SimulationGivenData {
    final int width;
    final int height;
    //simulacja jest opisywana takze przez wybrany wariant mapy, do wyboru maja byc 4
    //wariant wzrostu roślin (wyjaśnione w sekcji poniżej),
    //wariant mutacji (wyjaśnione w sekcji poniżej)
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
    final long seed;
    final boolean useMutationSwapGene;
    final boolean useLifeGivingCorpses;



    public SimulationGivenData(int width, int height, int initialAnimalsNumber, int initialGrassNumber, int initialAnimalEnergy,
                               int numOfGrassGrowingDaily, int grassEnergy, int breedReadyEnergy, int breedLostEnergy,
                               int genomeLength, int minMutationNum, int maxMutationNum, long seed, boolean useMutationSwapGene,
                               boolean useLifeGivingCorpses) {
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
        this.seed = seed;
        this.useMutationSwapGene = useMutationSwapGene;
        this.useLifeGivingCorpses = useLifeGivingCorpses;
    }

}
