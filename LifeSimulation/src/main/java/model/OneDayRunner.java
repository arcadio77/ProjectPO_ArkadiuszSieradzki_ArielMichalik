package model;
import java.util.*;

import model.enums.MapDirection;
import model.util.AnimalComparator;

public class OneDayRunner {
    private final WorldMap map;
    private final Random random;

    public OneDayRunner(WorldMap map){
        this.map = map;
        this.random = map.getRandom();
    }

    public void runOneDay(){
        map.passDay();
        map.bestAnimals.clear();

        Map<Vector2d, ArrayList<Animal>> animals = map.getAnimals();

        animals = afterDeathsAndMoves(animals);
        map.animals = animals;

        for (Vector2d key : animals.keySet()) {
            AnimalComparator comp = new AnimalComparator();
            ArrayList<Animal> animalsOnNewPos = animals.get(key);
            animalsOnNewPos.sort(comp);

            Animal mostPowerful = animalsOnNewPos.get(0);
            Vector2d mostPowerfulPosition = mostPowerful.position();
            map.bestAnimals.put(mostPowerfulPosition, mostPowerful);

            feastOnConquerdPosition(mostPowerful, mostPowerfulPosition);

            if (animalsOnNewPos.size() > 1) {
                Animal secMostPowerful = animalsOnNewPos.get(1);

                breedTwoMostPowerful(mostPowerful, secMostPowerful);
            }
        }
        growGrass();
        gravesAreGettingOlder();
        map.mapChanged("mapChanged");
    }

    private void growGrass(){
        int numOfGrassGrowingDaily = map.getNumOfGrassGrowingDaily();
        map.putGrasses(numOfGrassGrowingDaily);
    }

    private void gravesAreGettingOlder(){
        map.gravesAreGettingOlder();
    }

    private void breedTwoMostPowerful(Animal mostPowerful, Animal secMostPowerful){
        if (!mostPowerful.equals(secMostPowerful)) {
            int energyRequiredToCopulate = map.energy.getBreedReady();
            if (mostPowerful.getEnergy() >= energyRequiredToCopulate && secMostPowerful.getEnergy() >= energyRequiredToCopulate) {
                Genome childGenome = new Genome(map.getGenomeLength(), mostPowerful, secMostPowerful, map.mutation, random, map.isUseMutationSwapGene());
                int geneIDChild = random.nextInt(0, map.getGenomeLength() - 1);
                Animal child = new Animal(mostPowerful.position(), MapDirection.generateRandomDirection(), childGenome, geneIDChild, map.getEnergy().getBreedLost()*2);
                mostPowerful.breed(child, map.getEnergy().getBreedLost());
                secMostPowerful.breed(child, map.getEnergy().getBreedLost());
                map.place(child);
            }
        }
    }

    private void feastOnConquerdPosition(Animal mostPowerful, Vector2d mostPowerfulPos){
        if (map.plants.containsKey(mostPowerfulPos)) {
            map.plants.remove(mostPowerfulPos); //remove that plant out of the map
            mostPowerful.eat(map.getEnergy().getGrassEnergy()); // energy += grassEnergy
        }
    }

    private Map<Vector2d, ArrayList<Animal>> afterDeathsAndMoves(Map<Vector2d, ArrayList<Animal>> animals){
        Map<Vector2d, ArrayList<Animal>> toPlace = new HashMap<>();

        for (Vector2d position : animals.keySet()) {
            ArrayList<Animal> animalsOnThisPos = animals.get(position);

            for (Animal animal : animalsOnThisPos) {
                if (animal.getEnergy() > 0) {
                    animal.move(map.getLowerLeft(), map.getUpperRight()); // energy--

                    if(!(toPlace.containsKey(animal.position()))) { // placing on our map
                        toPlace.put(animal.position(), new ArrayList<>(List.of(animal)));
                    }
                    else{
                        toPlace.get(animal.position()).add(animal);
                    }
                }
                else{ // dead
                    animal.setDeathDate(map.getDay());
                    map.animalIsDead(position, animal);
                }
            }
        }
        return toPlace;
    }
}
