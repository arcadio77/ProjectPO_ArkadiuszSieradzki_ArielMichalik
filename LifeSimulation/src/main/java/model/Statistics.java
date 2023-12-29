package model;

public class Statistics {
    private int animalsCnt;
    private int grassCnt;
    private int animalAvgAge;
    private int animaAvgEnergyLvl;
    private int avgChildrenCnt;
    private Genome mostFrequentGenome;
    private int freeSquaresCnt;

    public Statistics(int animalsCnt, int grassCnt, int animalAvgAge, int animaAvgEnergyLvl, int avgChildrenCnt, Genome mostFrequentGenome, int freeSquaresCnt) {
        this.animalsCnt = animalsCnt;
        this.grassCnt = grassCnt;
        this.animalAvgAge = animalAvgAge;
        this.animaAvgEnergyLvl = animaAvgEnergyLvl;
        this.avgChildrenCnt = avgChildrenCnt;
        this.mostFrequentGenome = mostFrequentGenome;
        this.freeSquaresCnt = freeSquaresCnt;
    }


    public int getAnimalsCnt() {
        return animalsCnt;
    }

    public int getGrassCnt() {
        return grassCnt;
    }

    public int getAnimalAvgAge() {
        return animalAvgAge;
    }

    public int getAnimaAvgEnergyLvl() {
        return animaAvgEnergyLvl;
    }

    public int getAvgChildrenCnt() {
        return avgChildrenCnt;
    }

    public Genome getMostFrequentGenome() {
        return mostFrequentGenome;
    }

    public int getFreeSquaresCnt() {
        return freeSquaresCnt;
    }
}
