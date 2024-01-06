package model;

import java.util.*;

import model.enums.MapDirection;

public class OneCycle {

    private final WorldMap map;

    private Random rand;

    public OneCycle(WorldMap map){
        this.map = map;
        this.rand = new Random();
    }

    public void runOneCycle(){
        map.bestAnimals.clear();

        Map<Vector2d, ArrayList<Animal>> animals = map.getAnimals();
        Map<Vector2d, Grass> plants = map.getPlants();

        // <---------------------------------------------------------------------------------------------->
        //                                        First Phase
        // <---------------------------------------------------------------------------------------------->

        Map<Vector2d, ArrayList<Animal>> toPlace = new HashMap<>();

        for (Vector2d position : animals.keySet()) {
            ArrayList<Animal> animalsOnThisPos = animals.get(position);

            for (Animal animal : animalsOnThisPos) {
                // !!1 dead
                if (animal.getEnergy() > 0) {
                    // !!2 move and place
                    animal.move(map.getLowerLeft(), map.getUpperRight()); // energy--

                    if(!(toPlace.containsKey(animal.getPosition()))) { // placing on our map
                        toPlace.put(animal.getPosition(), new ArrayList<>(List.of(animal)));
                    }
                    else{
                        toPlace.get(animal.getPosition()).add(animal);
                    }
                }
                else{
                    map.animalIsDead();
                }
            }
        }

        animals = toPlace; // ez
        map.animals = animals; // bug fixed

        // <---------------------------------------------------------------------------------------------->
        //                                        Second Phase
        // <---------------------------------------------------------------------------------------------->

        // after updating positions of animals after moving
        for (Vector2d key : animals.keySet()) {
            ArrayList<Animal> animalsOnNewPos = animals.get(key);

            // most powerful
            Animal mostPowerful = (Animal) animalsOnNewPos.toArray()[0];

            for (Animal animal : animalsOnNewPos) {
                if (animal.getEnergy() > mostPowerful.getEnergy()) {
                    mostPowerful = animal;
                }
                else if (animal.getEnergy() == mostPowerful.getEnergy()) {
                    if (animal.getAge() > mostPowerful.getAge())
                        mostPowerful = animal;
                    else if (animal.getAge() == mostPowerful.getAge()) {
                        if (animal.getChildren().size() > mostPowerful.getChildren().size()){
                            mostPowerful = animal;
                        }
                        else if (animal.getChildren().size() == mostPowerful.getChildren().size()){
                            //Random rand = new Random();
                            mostPowerful = rand.nextBoolean() ? animal : mostPowerful;
                        }
                    }
                }
            }
            // !!3 eat <- most powerful
            Vector2d bestAnimalPos = mostPowerful.getPosition();

            map.bestAnimals.put(bestAnimalPos, mostPowerful);

            if (plants.containsKey(bestAnimalPos)) {
                //remove that plant out of the map
                plants.remove(bestAnimalPos);
                mostPowerful.eat(map.getEnergy().getGrassEnergy()); // energy += grassEnergy
            }

            // sec most powerful
            Animal secMostPowerful = (Animal) animalsOnNewPos.toArray()[0];
            if (animalsOnNewPos.size() > 1) {
                for (Animal animal : animalsOnNewPos) {
                    if ((animal.getEnergy() > secMostPowerful.getEnergy()) && (animal.getEnergy() <= mostPowerful.getEnergy())) {
                        secMostPowerful = animal;
                    } else if ((animal.getEnergy() == secMostPowerful.getEnergy()) && (animal.getEnergy() <= mostPowerful.getEnergy())) {
                        if (animal.getAge() > secMostPowerful.getAge())
                            secMostPowerful = animal;
                        else if (animal.getAge() == secMostPowerful.getAge()) {
                            if (animal.getChildren().size() > secMostPowerful.getChildren().size()) {
                                secMostPowerful = animal;
                            } else if (animal.getChildren().size() == secMostPowerful.getChildren().size()) {
                                //Random rand = new Random();
                                secMostPowerful = rand.nextBoolean() ? animal : secMostPowerful;
                            }
                        }
                    }
                }
            }

            // !!4 breed <- two most powerful (sexiest)
            // if they have at least minimum energy required to copulate
            if (!mostPowerful.equals(secMostPowerful)) {
                int energyRequiredToCopulate = map.energy.getBreedReady();
                if (mostPowerful.getEnergy() >= energyRequiredToCopulate && secMostPowerful.getEnergy() >= energyRequiredToCopulate) {
                    System.out.println(mostPowerful.getEnergy());
                    Genome childGenome = new Genome(map.getGenomeLength(), mostPowerful, secMostPowerful, map.mutation);
                    //Random rand = new Random();
                    int geneIDChild = rand.nextInt(0, map.getGenomeLength() - 1);
                    Animal child = new Animal(mostPowerful.getPosition(), MapDirection.NORTH, childGenome, geneIDChild, map.getEnergy().getBreedLost()*2);  //orientation random and geneId random
                    mostPowerful.breed(child, map.getEnergy().getBreedLost());
                    secMostPowerful.breed(child, map.getEnergy().getBreedLost());
                    map.place(child);
                    map.newAnimalBorn();
                }
            }
        }
        map.plants = plants;
    }


    private void growGrass(){
        int numOfGrassGrowingDaily = map.getNumOfGrassGrowingDaily();
    }

}
