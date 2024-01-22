package model;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TrackedAnimalStats {
    private WorldMap map;
    private int activeGene;
    private int energyLevel;
    private int eatenPlantsNumber;
    private int descendantsNumber;
    private int kidsNumber;
    private int howManyDaysIsLiving;
    private int animalDayOfDeath;
    private ArrayList<Integer> genome;
    Animal animal;
    private Map<Animal, Integer> descendantsVisited = new HashMap<>();

    public TrackedAnimalStats(Animal animal){
        this.animal = animal;
    }

    public void updateStats(){
        updateDescendantsNumber();
        this.energyLevel = animal.getEnergy();
        this.kidsNumber = animal.getChildren().size();
        this.howManyDaysIsLiving = animal.getAge();
        this.genome = animal.getGenomeList();
        this.activeGene = genome.get(animal.getGeneId());
        this.animalDayOfDeath = animal.getDeathDate();
        this.eatenPlantsNumber = animal.getEatenPlants();
    }

    private void updateDescendantsNumber(){
        descendantsNumber = rek(animal).size();
    }

    private ArrayList<Animal> rek(Animal a){
        if (a.getChildren().isEmpty()){
            return new ArrayList<>();
        }
        ArrayList<Animal> currentDescendants = new ArrayList<>();
        for(Animal ch : a.getChildren()){
            if(!descendantsVisited.containsKey(ch)){
                descendantsVisited.put(ch, 1);
                currentDescendants.add(ch);
                currentDescendants.addAll(rek(ch));
            }
            else{
                continue;
            }
        }
        return currentDescendants;
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
    public int getDescendantsNumber() { return descendantsNumber; }
}
