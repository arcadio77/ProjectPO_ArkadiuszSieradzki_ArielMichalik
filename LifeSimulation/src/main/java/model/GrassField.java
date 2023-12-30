package model;

import model.interfaces.MapChangeListener;
import model.interfaces.WorldElement;
import model.util.Energy;
import model.util.MapVisualizer;
import model.util.PositionsGenerator;
import java.util.*;


public class GrassField{

    final int width;
    final int height;

    int animalsNumber;

    Energy energy;

    Mutation mutation;

    MapVisualizer map = new MapVisualizer(this);

    protected final Map<Vector2d, ArrayList<Animal>> animals;
    private final Map<Vector2d, Grass> plants = new HashMap<>();
    final int numOfGrassGrowingDaily;

    private final Boundary worldBounds;
    private ArrayList<Boundary> jungleBounds;

    protected final ArrayList<MapChangeListener> observers;

    public GrassField(int width, int height, int animalsNumber,
                      Energy energy, int minMutationNum, int maxMutationNum, int plantsNumber,
                      Random seed, int numOfGrassGrowingDaily, int minJungleNum, int maxJungleNum){

        this.width = width;
        this.height = height;
        this.animalsNumber = animalsNumber;
        this.energy = energy;
        this.mutation = new Mutation(minMutationNum, maxMutationNum);
        this.numOfGrassGrowingDaily = numOfGrassGrowingDaily;
        this.animals = new HashMap<>();
        this.observers = new ArrayList<>();

        Vector2d worldTopRightCorner = new Vector2d(width, height);
        Vector2d worldDownLeftCorner = new Vector2d(0, 0);
        this.worldBounds = new Boundary(worldDownLeftCorner, worldTopRightCorner);

        //setJungleBounds(seed); // jungle generator <- to do


        //put grass - very dirty but will work
        while(plantsNumber != 0){
            PositionsGenerator positions = new PositionsGenerator(width,height,plantsNumber,seed);
            for(Vector2d grassPosition : positions){
                if(!isOccupied(grassPosition)){
                    plants.put(grassPosition, new Grass(grassPosition));
                    plantsNumber--;
                }
            }
        }

        //put animals on map
        PositionsGenerator positions = new PositionsGenerator(width, height, animalsNumber, seed);
        for(Vector2d animalPosition: positions){
            Animal newAnimal = new Animal(animalPosition);
            this.place(newAnimal);
        }

    }

    // <---------------------------------------------------------------------------------------------->
    //                                              GETTERS
    // <---------------------------------------------------------------------------------------------->

    public Map<Vector2d, ArrayList<Animal>> getAnimals(){
        return this.animals;
    }

    public  Map<Vector2d, Grass> getPlants(){
        return this.plants;
    }

    public Vector2d getLowerLeft(){
        return worldBounds.leftDownCorner();
    }

    public Vector2d getUpperRight(){
        return worldBounds.rightUpperCorner();
    }

    public int getWidth(){
        return width;
    }

    public int getHeight(){
        return height;
    }

    public Energy getEnergy(){
        return energy;
    }

    /*
    public ArrayList<WorldElement> getElements() { // to do
        ArrayList<Set<WorldElement>> values = new ArrayList<>(new ArrayList<>(animals.values()));
        values.addAll(plants.values());
        //return values;
    }*/

    // <---------------------------------------------------------------------------------------------->
    //                                              SETTERS
    // <---------------------------------------------------------------------------------------------->

    /*
    private void setJungleBounds(int minJungles, int maxJungles, Random seed){ // to do

        int howManyJungles = minJungles + seed.nextInt(maxJungles + 1 - minJungles);

        PositionsGenerator positions = new PositionsGenerator(width, height, 2 * howManyJungles, seed);
        for (Vector2d jungleBounds : positions) {
            if (!isOccupied(jungleBounds)) {

            }
        }
    }*/


    // <---------------------------------------------------------------------------------------------->
    //                                            POSITIONING
    // <---------------------------------------------------------------------------------------------->

    public boolean canMoveTo(Vector2d position){ // change this to something that will tell us if an animal is on an edge of the map
        return !isOccupied(position);
    }

    public boolean isOccupied(Vector2d position){
        return animals.containsKey(position);
    }

    public WorldElement objectAt(Vector2d position){ // demo version 1
        if(animals.containsKey(position)){
            ArrayList<Animal> animalsOnPos = animals.get(position);
            return animalsOnPos.get(0); // first animal on this position
        }
        else if(plants.containsKey(position)){
            return plants.get(position);
        }
        return null;
    }
    public ArrayList<Animal> objectsAt(Vector2d position) { // demo version 2
        return animals.get(position);
    }

    public void place(Animal animal){
        Vector2d animalPos = animal.getPosition();
        if(isOccupied(animal.getPosition())) {
            animals.get(animalPos).add(animal);
        }
        else{
            animals.put(animalPos, new ArrayList<>(List.of(animal)));
        }
        showMessage("Animal moved into position: " + animal.getPosition());
    }

    // <---------------------------------------------------------------------------------------------->
    //                                            DISPLAYING
    // <---------------------------------------------------------------------------------------------->


    public String toString(){ // don't know if necessary
        return map.draw(worldBounds.leftDownCorner(), worldBounds.rightUpperCorner());
    }

    private void showMessage(String message){
        for(MapChangeListener observer: observers){
            observer.mapChanged(this, message);
        }
    }

}
