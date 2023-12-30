package model;

import java.util.ArrayList;
import java.util.Map;
import java.util.Random;

import model.enums.MapDirection;

public class OneCycle {

    private final GrassField gF;

    public OneCycle(GrassField gF){
        this.gF = gF;
    }

    public void runOneCycle(){

        Map<Vector2d, ArrayList<Animal>> animals = gF.getAnimals();
        Map<Vector2d, Grass> plants = gF.getPlants();

        // <---------------------------------------------------------------------------------------------->
        //                                        First Phase
        // <---------------------------------------------------------------------------------------------->

        for (Vector2d key : animals.keySet()) {
            ArrayList<Animal> animalsOnThisPos = animals.get(key);

            for (Animal animal : animalsOnThisPos) {
                // !!1 dead
                if (animal.getEnergy() == 0) {
                    animalsOnThisPos.remove(animal);
                }

                // !!2 move
                animal.move(gF.getLowerLeft(), gF.getUpperRight()); // energy--
                gF.place(animal); // placing on our map
                animalsOnThisPos.remove(animal); // removing old position of animal

            }
        }

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
                            Random rand = new Random();
                            mostPowerful = rand.nextBoolean() ? animal : mostPowerful;
                        }
                    }
                }
            }
            // !!3 eat <- most powerful
            Vector2d bestAnimalPos = mostPowerful.getPosition();
            if (plants.containsKey(bestAnimalPos)) {
                //remove that plant out of the map
                plants.remove(bestAnimalPos);
                mostPowerful.eat(gF.getEnergy().getGrassEnergy()); // energy += grassEnergy
            }

            // sec most powerful
            Animal secMostPowerful = (Animal) animalsOnNewPos.toArray()[0];

            for (Animal animal : animalsOnNewPos) {
                if ((animal.getEnergy() > secMostPowerful.getEnergy()) && (animal.getEnergy() <= mostPowerful.getEnergy())) {
                    secMostPowerful = animal;
                }
                else if ((animal.getEnergy() == secMostPowerful.getEnergy()) && (animal.getEnergy() <= mostPowerful.getEnergy())) {
                    if (animal.getAge() > secMostPowerful.getAge())
                        secMostPowerful = animal;
                    else if (animal.getAge() == secMostPowerful.getAge()) {
                        if (animal.getChildren().size() > secMostPowerful.getChildren().size()){
                            secMostPowerful = animal;
                        }
                        else if (animal.getChildren().size() == secMostPowerful.getChildren().size()){
                            Random rand = new Random();
                            secMostPowerful = rand.nextBoolean() ? animal : secMostPowerful;
                        }
                    }
                }
            }

            // !!4 breed <- two most powerful (sexiest)
            // if they have at least minimum energy required to copulate
            int energyRequiredToCopulate = gF.energy.getBreedReady();
            if (mostPowerful.getEnergy() >= energyRequiredToCopulate && secMostPowerful.getEnergy() >= energyRequiredToCopulate){
                Genome childGenome = new Genome(100, mostPowerful, secMostPowerful, gF.mutation); // n is not set right
                Animal child = new Animal(mostPowerful.getPosition(), MapDirection.NORTH, childGenome, 5, gF.energy.getInitialAnimalEnergy());  //orientation random and geneId random
                mostPowerful.breed(child, gF.getEnergy().getBreedLost());
                secMostPowerful.breed(child, gF.getEnergy().getBreedLost());
                gF.place(child);
            }
        }
    }
}
