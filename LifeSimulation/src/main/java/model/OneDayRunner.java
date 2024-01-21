package model;

import java.util.*;

import model.enums.MapDirection;

public class OneDayRunner {

    private final WorldMap map;

    private int dayNumber;
    private final Random random;

    public OneDayRunner(WorldMap map){
        this.dayNumber = 0;
        this.map = map;
        this.random = map.getRandom();
    }

    public void runOneDay(){
        dayNumber++;
        map.bestAnimals.clear();

        Map<Vector2d, ArrayList<Animal>> animals = map.getAnimals();

        animals = afterDeathsAndMoves(animals);
        map.animals = animals;

        for (Vector2d key : animals.keySet()) {
            ArrayList<Animal> animalsOnNewPos = animals.get(key);

            Animal mostPowerful = mostPowerful(animalsOnNewPos);
            Vector2d mostPowerfulPosition = mostPowerful.position();
            map.bestAnimals.put(mostPowerfulPosition, mostPowerful);

            feastOnConquerdPosition(mostPowerful, mostPowerfulPosition);

            Animal secMostPowerful = secMostPowerful(animalsOnNewPos, mostPowerful);

            breedTwoMostPowerful(mostPowerful, secMostPowerful);
        }
        growGrass();
        gravesAreGettingOlder();
        map.mapChanged("uuu");
    }


    private void growGrass(){
        int numOfGrassGrowingDaily = map.getNumOfGrassGrowingDaily();
        map.putGrasses(numOfGrassGrowingDaily);
    }

    private void gravesAreGettingOlder(){
        map.gravesAreGettingOlder();
    }

    private Animal mostPowerful(ArrayList<Animal> animalsOnNewPos){
        Animal mostPowerful = (Animal) animalsOnNewPos.toArray()[0];
        for (Animal animal : animalsOnNewPos) {
            if (animal.getEnergy() > mostPowerful.getEnergy()) {
                mostPowerful = animal;
            }
            else if (animal.getEnergy() == mostPowerful.getEnergy()) {
                mostPowerful = getStrongerAnimal(mostPowerful, animal);
            }
        }
        return mostPowerful;
    }

    private Animal secMostPowerful(ArrayList<Animal> animalsOnNewPos, Animal mostPowerful){
        Animal secMostPowerful = (Animal) animalsOnNewPos.toArray()[0];
        if (animalsOnNewPos.size() > 1) {
            for (Animal animal : animalsOnNewPos) {
                if ((animal.getEnergy() > secMostPowerful.getEnergy()) && (animal.getEnergy() <= mostPowerful.getEnergy())) {
                    secMostPowerful = animal;
                } else if ((animal.getEnergy() == secMostPowerful.getEnergy()) && (animal.getEnergy() <= mostPowerful.getEnergy())) {
                    secMostPowerful = getStrongerAnimal(secMostPowerful, animal);
                }
            }
        }
        return secMostPowerful;
    }

    private Animal getStrongerAnimal(Animal animal1, Animal animal2) { // if two animals have the same energy levels
        if (animal2.getAge() > animal1.getAge())
            animal1 = animal2;
        else if (animal2.getAge() == animal1.getAge()) {
            if (animal2.getChildren().size() > animal1.getChildren().size()) {
                animal1 = animal2;
            } else if (animal2.getChildren().size() == animal1.getChildren().size()) {
                animal1 = random.nextBoolean() ? animal2 : animal1;
            }
        }
        return animal1;
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
                map.newAnimalBorn();
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
                    map.animalIsDead(position, animal);
                }
            }
        }
        return toPlace;
    }

    public int getDayNumber() {
        return dayNumber;
    }
}
