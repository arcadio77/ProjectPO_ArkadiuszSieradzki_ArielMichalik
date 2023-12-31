package model;

import model.interfaces.MapChangeListener;
import model.interfaces.WorldElement;
import model.util.Energy;
import model.util.MapVisualizer;
import model.util.Pair;
import model.util.PositionsGenerator;
import java.util.*;


public class GrassField{

    private final int width;
    private final int height;
    private int plantsNumber; //initial at first
    private int animalsNumber; //initial at first

    private int dead = 0;
    private int newAnimals = 0;

    Energy energy;

    Mutation mutation;

    protected Map<Vector2d, ArrayList<Animal>> animals;
    private Map<Vector2d, Grass> plants;
    private final int numOfGrassGrowingDaily; //need to move to oneCycle class

    private final Boundary worldBounds;
    private ArrayList<Boundary> jungleBounds;

    protected final ArrayList<MapChangeListener> observers;

    public GrassField(int width, int height, int animalsNumber,
                      Energy energy, int minMutationNum, int maxMutationNum, int plantsNumber,
                      int numOfGrassGrowingDaily, int minJungleNum, int maxJungleNum){

        this.width = width;
        this.height = height;
        this.animalsNumber = animalsNumber;
        this.plantsNumber = plantsNumber;
        this.energy = energy;
        this.mutation = new Mutation(minMutationNum, maxMutationNum);
        this.numOfGrassGrowingDaily = numOfGrassGrowingDaily;
        this.animals = new HashMap<>();
        this.plants = new HashMap<>();

        Vector2d worldTopRightCorner = new Vector2d(width, height);
        Vector2d worldDownLeftCorner = new Vector2d(0, 0);
        this.worldBounds = new Boundary(worldDownLeftCorner, worldTopRightCorner);

        this.observers = new ArrayList<>();

        //setJungleBounds(seed); // jungle generator <- to do
        putGrasses();
        putAnimals();

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
        return worldBounds.lowerLeft();
    }

    public Vector2d getUpperRight(){
        return worldBounds.upperRight();
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

    public Mutation getMutation() { return mutation; }

    /*
    public ArrayList<WorldElement> getElements() { // to do
        ArrayList<Set<WorldElement>> values = new ArrayList<>(new ArrayList<>(animals.values()));
        values.addAll(plants.values());
        //return values;
    }*/

    // <---------------------------------------------------------------------------------------------->
    //                                              SETTERS
    // <---------------------------------------------------------------------------------------------->

    public int cntAnimalsOnGivenPosition(Vector2d position){
        return animals.get(position).size();
    }

    public int cntGrassesOnGivenPosition(Vector2d position){
        return isOccupiedByGrass(position) ? 1 : 0;
    }

    public int cntAllGrasess(){
        return plants.size();
    }

    public int cntAllAliveAnimals(){
        //maybe works
        int cnt = 0;
        for(Map.Entry<Vector2d, ArrayList<Animal>> entry : animals.entrySet()){
            cnt += entry.getValue().size();
        }
        return cnt;
    }
    public void putGrasses(){
        PositionsGenerator grassPositions = new PositionsGenerator(width,height,plantsNumber);
        for(Vector2d grassPosition : grassPositions){
            plants.put(grassPosition, new Grass(grassPosition));
        }
    }

    public void putAnimals(){
        PositionsGenerator positions = new PositionsGenerator(width, height, animalsNumber);
        for(Vector2d animalPosition: positions){
            Animal newAnimal = new Animal(animalPosition);
            this.place(newAnimal);
        }
    }

    /*
    private void setJungleBounds(int minJungles, int maxJungles, Random seed){ // to do

        int howManyJungles = minJungles + seed.nextInt(maxJungles + 1 - minJungles);

        PositionsGenerator positions = new PositionsGenerator(width, height, 2 * howManyJungles, seed);
        for (Vector2d jungleBounds : positions) {
            if (!isOccupied(jungleBounds)) {

            }
        }
    }*/

    public void newAnimalBorn(){
        newAnimals++;
    }

    public void animalIsDead(){
        newAnimals++;
    }

    // <---------------------------------------------------------------------------------------------->
    //                                            POSITIONING
    // <---------------------------------------------------------------------------------------------->

    public boolean isOccupiedByAnimal(Vector2d position){
        return animals.containsKey(position);
    }

    public boolean isOccupiedByGrass(Vector2d position){
        return plants.containsKey(position);
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

    public Pair objectsAt(Vector2d position) { // demo version 2
        if(isOccupiedByGrass(position) || isOccupiedByAnimal(position)){
            return new Pair(cntAnimalsOnGivenPosition(position), cntGrassesOnGivenPosition(position));
        }
        return null;
    }

    public void place(Animal animal){
        Vector2d animalPos = animal.getPosition();
        if(isOccupiedByAnimal(animal.getPosition())) {
            animals.get(animalPos).add(animal);
        }
        else{
            animals.put(animalPos, new ArrayList<>(List.of(animal)));
        }
        //showMessage("Animal moved into position: " + animal.getPosition());
    }

    // <---------------------------------------------------------------------------------------------->
    //                                            DISPLAYING
    // <---------------------------------------------------------------------------------------------->


    public String toString() {
        MapVisualizer vis = new MapVisualizer(this);
        return vis.draw(worldBounds.lowerLeft(), worldBounds.upperRight());
    }

    private void showMessage(String message){
        for(MapChangeListener observer: observers){
            observer.mapChanged(this, message);
        }
    }


}

