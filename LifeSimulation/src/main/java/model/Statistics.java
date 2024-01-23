package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class Statistics {
    private int numberOfAllAnimals;

    public int getDayNumber() { return map.getDay(); }

    public int getNumberOfAllAnimals() {
        return numberOfAllAnimals;
    }

    public int getNumberOfAllPlants() {
        return numberOfAllPlants;
    }

    public int getNumberOfEmptyCells() {
        return numberOfEmptyCells;
    }

    public List<Integer> getMostPopularGenome() {
        return mostPopularGenome;
    }

    public double getAverageEnergyLevelForLivingAnimals() {
        return averageEnergyLevelForLivingAnimals;
    }

    public double getAverageKidsNumberForLivingAnimals() {
        return averageKidsNumberForLivingAnimals;
    }
    public double getAverageLifespanForDeathAnimals() {return averageLifespanForDeathAnimals;}

    private int numberOfAllPlants;
    private int numberOfEmptyCells;
    private List<Integer> mostPopularGenome;
    private double averageEnergyLevelForLivingAnimals;
    private double averageKidsNumberForLivingAnimals;
    private double averageLifespanForDeathAnimals;

    WorldMap map;

    public Statistics(WorldMap map){
        this.map = map;
    }
    public void updateStats(){
        countAnimals();
        countPlants();
        countEmptyCells();
        countMostPopularGenome();
        countAverageEnergyLevelForLivingAnimals();
        countAverageKidsNumberForLivingAnimals();
        countAverageLifespanForDeadAnimals();
    }


    private void countAverageLifespanForDeadAnimals(){
        double averageLifespan = map.getDeadAnimals().stream()
                .mapToInt(Animal::getAge)
                .average()
                .orElse(0.0);

        this.averageLifespanForDeathAnimals = Math.round(averageLifespan * 100.0)/100.0;
        System.out.println("Average lifeSpan: " + Math.round(averageLifespan * 100.0)/100.0);
    }

    private void countAverageKidsNumberForLivingAnimals() {
        double averageChildren = map.animals.values().stream()
                .flatMap(List::stream)
                .mapToInt(animal -> animal.getChildren().size())
                .average()
                .orElse(0.0);
        this.averageKidsNumberForLivingAnimals = Math.round(averageChildren * 100.0)/100.0;
        System.out.println("Average number of children: " + Math.round(averageChildren * 100.0)/100.0);

    }


    private void countAverageEnergyLevelForLivingAnimals(){
        double averageEnergy = map.animals.values().stream()
                .flatMap(List::stream)
                .mapToInt(Animal::getEnergy)
                .average()
                .orElse(0.0);
        this.averageEnergyLevelForLivingAnimals = Math.round(averageEnergy * 100.0)/100.0;
        System.out.println("Average energy: " + averageEnergy);

    }

    public void countPlants(){
        this.numberOfAllPlants = map.getPlants().size();
    }

    private void countAnimals(){
        int cnt = 0;
        for (Vector2d position: map.animals.keySet()){
            cnt += map.animals.get(position).size();
        }
        this.numberOfAllAnimals = cnt;
    }

    private void countEmptyCells(){ //need to test if works
        List<Vector2d> allPositions = new ArrayList<>();
        for (int i = 0; i < map.getHeight(); i++) {
            for (int j = 0; j < map.getWidth(); j++) {
                allPositions.add(new Vector2d(j, i));
            }
        }
        allPositions.removeAll(map.animals.keySet());
        allPositions.removeAll(map.plants.keySet());
        this.numberOfEmptyCells = allPositions.size();
    }

    private void countMostPopularGenome(){
        Map<List<Integer>, Long> genomeCount = map.animals.values().stream()
                .flatMap(List::stream)
                .collect(Collectors.groupingBy(Animal::getGenomeList, Collectors.counting()));

        Optional<Map.Entry<List<Integer>, Long>> mostOccuredGenome = genomeCount.entrySet().stream()
                .max(Map.Entry.comparingByValue());
        mostOccuredGenome.ifPresent(listLongEntry -> this.mostPopularGenome = listLongEntry.getKey());
    }

}
