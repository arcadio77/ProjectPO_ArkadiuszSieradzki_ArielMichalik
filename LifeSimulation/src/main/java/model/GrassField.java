package model;

import model.enums.MapDirection;
import model.exceptions.PositionAlreadyOccupiedException;
import model.interfaces.MapChangeListener;
import model.interfaces.WorldElement;
import model.util.Energy;
import model.util.MapVisualizer;
import model.util.PositionsGenerator;
import java.util.*;
import static java.lang.Math.sqrt;


public class GrassField extends BaseMap{
    private final Map<Vector2d, Grass> grassFields = new HashMap<>();
    protected final Map<Vector2d, Set<Animal>> animals;
    private int grassNumber; //Starter grassNumber
    final int numOfGrassGrowingDaily;
    protected MapVisualizer map;
    private final Boundary worldBounds;
    private ArrayList<Boundary> jungleBounds;
    protected final ArrayList<MapChangeListener> observers;

    public GrassField(int width, int height, int animalsNumber,
                      Energy energy, Mutation mutation, int grassNumber,
                      Random seed, int numOfGrassGrowingDaily, int minJungleSize, int maxJungleSize){
        super(width,height, animalsNumber, energy, mutation);
        this.numOfGrassGrowingDaily = numOfGrassGrowingDaily;
        this.map = new MapVisualizer(this);
        this.animals = new HashMap<>();
        this.observers = new ArrayList<>();

        Vector2d worldTopRightCorner = new Vector2d(0, 0);
        Vector2d worldDownLeftCorner = new Vector2d(width, height);
        this.worldBounds = new Boundary(worldDownLeftCorner, worldTopRightCorner);

        PositionsGenerator positions = new PositionsGenerator(width,height,grassNumber,seed);
        for(Vector2d grassPosition : positions){
            grassFields.put(grassPosition, new Grass(grassPosition));
        }

    }


    public void place(Animal animal){
        Vector2d animalPos = animal.getPosition();
        if(isOccupied(animal.getPosition())) {
            animals.get(animalPos).add(animal);
        }
        else{
            animals.put(animalPos, new HashSet<Animal>(List.of(animal)));
        }
        showMessage("Animal moved into position: " + animal.getPosition());
    }

    private void showMessage(String message){
        for(MapChangeListener observer: observers){
            observer.mapChanged(this, message);
        }
    }


    public WorldElement objectAt(Vector2d position) { // do wyjebania albo do zmiany
        //return animals.get(position);
    }


    public ArrayList<WorldElement> getElements(){ // do wyjebania albo do zmiany
        //ArrayList<WorldElement> values = new ArrayList<>(new ArrayList<>(animals.values()));
        //values.addAll(grassFields.values());
        //return values;
    }

    public boolean isOccupied(Vector2d position){
        return animals.containsKey(position);
    }

    public boolean canMoveTo(Vector2d position){
        return !isOccupied(position);
    }

    public String toString(){
        return map.draw(worldBounds.leftDownCorner(), worldBounds.rightUpperCorner());
    }

    public void growGrass(){ // everywhere <- the same probability
        PositionsGenerator positions = new PositionsGenerator(height, )
    }



    public void oneMovement(){

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
                place(animal); // placing in our map
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
            for (Animal animal : animalsOnNewPos){
                if (animal.getEnergy() > mostPowerful.getEnergy()){
                    mostPowerful = animal;
                }
                else if (animal.getEnergy() == mostPowerful.getEnergy()){
                    if (animal.getAge() > mostPowerful.getAge())
                        mostPowerful = animal;
                    else if (animal.getAge() == mostPowerful.getAge()){
                        // najwięcej dzieci
                    }
                }
            }
            // !!3 eat <- most powerful
            Vector2d bestAnimalPos = mostPowerful.getPosition();
            if(grassFields.containsKey(bestAnimalPos)){
                grassFields.remove(bestAnimalPos);
                mostPowerful.eat(); // energy++
            }

            // sec most powerful
            Animal secMostPowerful = (Animal) animalsOnNewPos.toArray()[0];
            for (Animal animal : animalsOnNewPos){
                if ((animal.getEnergy() > secMostPowerful.getEnergy()) && (animal.getEnergy() <= mostPowerful.getEnergy())){
                    secMostPowerful = animal;
                }
                else if ((animal.getEnergy() == secMostPowerful.getEnergy()) && (animal.getEnergy() <= mostPowerful.getEnergy())){
                    if (animal.getAge() > secMostPowerful.getAge())
                        secMostPowerful = animal;
                    else if (animal.getAge() == secMostPowerful.getAge()){
                        // najwięcej dzieci
                    }
                }
            }

            // !!4 breed <- two most powerful (sexiest)
            // if they have at least minimum energy required to copulate
            Genome childGenome = new Genome(100, mostPowerful, secMostPowerful); // n is not set right
            Animal child = new Animal(mostPowerful.getPosition(), MapDirection.NORTH, childGenome, 5);  //orientation random and geneId random
            place(child);
        }
    }

}
