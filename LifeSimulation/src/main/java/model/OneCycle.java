package model;

import java.util.Map;
import java.util.Set;
import model.enums.MapDirection;

public class OneCycle {

    private final GrassField gF;

    public OneCycle(GrassField gF){
        this.gF = gF;
    }

    public void runOneCycle() {

        Map<Vector2d, Set<Animal>> animals = gF.getAnimals();
        Map<Vector2d, Grass> plants = gF.getPlants();

        // <---------------------------------------------------------------------------------------------->
        //                                        First Phase
        // <---------------------------------------------------------------------------------------------->

        for (Vector2d key : animals.keySet()) {
            Set<Animal> animalsOnThisPos = animals.get(key);

            for (Animal animal : animalsOnThisPos) {
                // !!1 dead
                if (animal.getEnergy() == 0) {
                    animals.remove(key);
                }

                // !!2 move
                animal.move(); // energy--
                gF.place(animal); // placing in our map
            }
        }

        // <---------------------------------------------------------------------------------------------->
        //                                        Second Phase
        // <---------------------------------------------------------------------------------------------->

        // after updating positions of animals after moving
        for (Vector2d key : animals.keySet()) {
            Set<Animal> animalsOnNewPos = animals.get(key);

            // most powerful
            Animal mostPowerful = (Animal) animalsOnNewPos.toArray()[0];

            for (Animal animal : animalsOnNewPos) {
                if (animal.getEnergy() > mostPowerful.getEnergy()) {
                    mostPowerful = animal;
                } else if (animal.getEnergy() == mostPowerful.getEnergy()) {
                    if (animal.getAge() > mostPowerful.getAge())
                        mostPowerful = animal;
                    else if (animal.getAge() == mostPowerful.getAge()) {
                        // najwięcej dzieci
                    }
                }
            }
            // !!3 eat <- most powerful
            Vector2d bestAnimalPos = mostPowerful.getPosition();
            if (plants.containsKey(bestAnimalPos)) {
                plants.remove(bestAnimalPos);
                mostPowerful.eat(1); // energy += grassEnergy <- do zmiany
            }

            // sec most powerful
            Animal secMostPowerful = (Animal) animalsOnNewPos.toArray()[0];

            for (Animal animal : animalsOnNewPos) {
                if ((animal.getEnergy() > secMostPowerful.getEnergy()) && (animal.getEnergy() <= mostPowerful.getEnergy())) {
                    secMostPowerful = animal;
                } else if ((animal.getEnergy() == secMostPowerful.getEnergy()) && (animal.getEnergy() <= mostPowerful.getEnergy())) {
                    if (animal.getAge() > secMostPowerful.getAge())
                        secMostPowerful = animal;
                    else if (animal.getAge() == secMostPowerful.getAge()) {
                        // najwięcej dzieci
                    }
                }
            }

            // !!4 breed <- two most powerful (sexiest)
            // if they have at least minimum energy required to copulate
            Genome childGenome = new Genome(100, mostPowerful, secMostPowerful, gF.mutation); // n is not set right
            Animal child = new Animal(mostPowerful.getPosition(), MapDirection.NORTH, childGenome, 5);  //orientation random and geneId random
            mostPowerful.breed(child);
            secMostPowerful.breed(child);
            gF.place(child);
        }
    }
}
