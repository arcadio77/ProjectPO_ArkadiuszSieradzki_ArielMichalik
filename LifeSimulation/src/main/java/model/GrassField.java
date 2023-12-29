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

    protected final Map<Vector2d, Set<Animal>> animals;
    private final Map<Vector2d, Grass> plants = new HashMap<>();
    final int numOfGrassGrowingDaily;

    protected MapVisualizer map;
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
        this.map = new MapVisualizer(this);
        this.animals = new HashMap<>();
        this.observers = new ArrayList<>();

        Vector2d worldTopRightCorner = new Vector2d(0, 0);
        Vector2d worldDownLeftCorner = new Vector2d(width, height);
        this.worldBounds = new Boundary(worldDownLeftCorner, worldTopRightCorner);

        setJungleBounds(seed); // jungle generator <- to do


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

    }

    // <---------------------------------------------------------------------------------------------->
    //                                              GETTERS
    // <---------------------------------------------------------------------------------------------->

    public Map<Vector2d, Set<Animal>> getAnimals(){
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

    public ArrayList<WorldElement> getElements() { // do wyjebania albo do zmiany
        //ArrayList<WorldElement> values = new ArrayList<>(new ArrayList<>(animals.values()));
        //values.addAll(grassFields.values());
        //return values;
    }

    // <---------------------------------------------------------------------------------------------->
    //                                              SETTERS
    // <---------------------------------------------------------------------------------------------->

    private void setJungleBounds(int minJungles, int maxJungles, Random seed){ // to do

        int howManyJungles = minJungles + seed.nextInt(maxJungles + 1 - minJungles);

        PositionsGenerator positions = new PositionsGenerator(width, height, 2 * howManyJungles, seed);
        for (Vector2d jungleBounds : positions) {
            if (!isOccupied(jungleBounds)) {

            }
        }
    }

    // <---------------------------------------------------------------------------------------------->
    //                                            POSITIONING
    // <---------------------------------------------------------------------------------------------->

    public boolean canMoveTo(Vector2d position){
        return !isOccupied(position);
    }

    public boolean isOccupied(Vector2d position){
        return animals.containsKey(position);
    }

    public Set<Animal> objectsAt(Vector2d position) { // do wyjebania albo do zmiany
        return animals.get(position);
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

    // <---------------------------------------------------------------------------------------------->
    //                                            DISPLAYING
    // <---------------------------------------------------------------------------------------------->


    public String toString(){
        return map.draw(worldBounds.leftDownCorner(), worldBounds.rightUpperCorner());
    }

    private void showMessage(String message){
        for(MapChangeListener observer: observers){
            observer.mapChanged(this, message);
        }
    }


}
