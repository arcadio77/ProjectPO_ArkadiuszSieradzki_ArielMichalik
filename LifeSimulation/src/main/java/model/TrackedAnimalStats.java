package model;


import java.util.ArrayList;

public class TrackedAnimalStats {
    private int activeGene;
    private int energyLevel;
    private int eatenPlantsNumber;
    private int descendentsNumber;
    private int kidsNumber;
    private int howManyDaysIsLiving;
    private int animalDayOfDeath;

    private ArrayList<Integer> genome;


    Animal animal;


    public TrackedAnimalStats(Animal animal){
        this.animal = animal;
    }

    public void updateStats(){
        this.activeGene = animal.getGeneId();
        this.energyLevel = animal.getEnergy();
        this.kidsNumber = animal.getChildren().size();
        this.howManyDaysIsLiving = animal.getAge();
        this.genome = animal.getGenomeList();
        this.animalDayOfDeath = animal.getDeathDate();
        this.eatenPlantsNumber = animal.getEatenPlants();
    }


    public int getActiveGene() {
        return activeGene;
    }

    public int getEnergyLevel() {
        return energyLevel;
    }

    public int getEatenPlantsNumber() {
        return eatenPlantsNumber;
    }


    public int getKidsNumber() {
        return kidsNumber;
    }

    public int getHowManyDaysIsLiving() {
        return howManyDaysIsLiving;
    }

    public int getAnimalDayOfDeath() {
        return animalDayOfDeath;
    }


    public ArrayList<Integer> getGenome() {
        return genome;
    }
    public int getDescendentsNumber() {
        return descendentsNumber;
    }
}
