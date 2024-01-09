package model;

import model.enums.MapDirection;
import model.interfaces.MapChangeListener;
import model.interfaces.WorldElement;
import model.util.*;

import java.util.*;


public class WorldMap {

    private final int width;
    private final int height;
    protected final ArrayList<MapChangeListener> observers;
    private int plantsNumber; //initial at first
    private int animalsNumber; //initial at first
    private int genomeLength;

    private int dead = 0;
    private int newAnimals = 0;

    Energy energy;

    Mutation mutation;

    protected Map<Vector2d, ArrayList<Animal>> animals;
    protected Map<Vector2d, Animal> bestAnimals;
    protected Map<Vector2d, Grass> plants;
    private final int numOfGrassGrowingDaily; //need to move to oneCycle class

    private final Boundary worldBounds;
    private final Boundary jungleBounds;

    private final Random random;

    public WorldMap(int width, int height, int animalsNumber, int plantsNumber,
                    Energy energy, int minMutationNum, int maxMutationNum, int genomeLength,
                    int numOfGrassGrowingDaily, Random random){

        this.width = width;
        this.height = height;
        this.animalsNumber = animalsNumber;
        this.plantsNumber = plantsNumber;
        this.energy = energy;
        this.mutation = new Mutation(minMutationNum, maxMutationNum);
        this.genomeLength = genomeLength;
        this.numOfGrassGrowingDaily = numOfGrassGrowingDaily;
        this.animals = new HashMap<>();
        this.bestAnimals = new HashMap<>();
        this.plants = new HashMap<>();
        this.random = random;
        this.observers = new ArrayList<>();

        Vector2d worldTopRightCorner = new Vector2d(width, height);
        Vector2d worldDownLeftCorner = new Vector2d(0, 0);
        this.worldBounds = new Boundary(worldDownLeftCorner, worldTopRightCorner);
        this.jungleBounds = setJungleBounds();
        putGrasses(this.plantsNumber);
        putAnimals();

        for (Vector2d position : animals.keySet()){
            this.bestAnimals.put(position, animals.get(position).get(0));
        }
    }
    
    public WorldMap(int width, int height){
        this(width, height, 5, 5, new Energy(1, 2, 4, 4),
                1, 1, 10, 4, new Random());
    }

    //OBSERVERS
    public void addObserver(MapChangeListener observer){
        observers.add(observer);
    }

    public void removeObserver(MapChangeListener observer){
        observers.remove(observer);
    }

    private void mapChanged(String message){
        for(MapChangeListener observer: observers){
            observer.mapChanged(this, message);
        }
    }


    public Boundary setJungleBounds(){
        //20% of the map area, horizontal line of the grass -> const width
        int mapArea = this.height * this.width;
        int jungleArea = (int) (mapArea*0.2);
        int y = jungleArea/this.width;
        int centerHeight =this.height/2;
        Vector2d leftDownCorner = new Vector2d(0,centerHeight-(int)(y/2));
        Vector2d rightUpCorner = new Vector2d(this.width,leftDownCorner.y() + Math.max(y,1));
        return new Boundary(leftDownCorner, rightUpCorner);
    }


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
        int cnt = 0;
        for(Map.Entry<Vector2d, ArrayList<Animal>> entry : animals.entrySet()){
            cnt += entry.getValue().size();
        }
        return cnt;
    }
    public void putGrasses(int plantsNumber){
        GrassPositionsGenerator grassPositions = new GrassPositionsGenerator(this,plantsNumber);
        for(Vector2d grassPosition : grassPositions){
            plants.put(grassPosition, new Grass(grassPosition));
        }
    }

    public void putAnimals(){
        AnimalPositionsGenerator positions = new AnimalPositionsGenerator(this, animalsNumber);
        for(Vector2d animalPosition: positions){
            Animal newAnimal = new Animal(animalPosition, MapDirection.generateRandomDirection(), new Genome(genomeLength, mutation, random), 0, energy.getInitialAnimalEnergy());
            this.place(newAnimal);
        }
    }

    public void newAnimalBorn(){
        newAnimals++;
    }

    public void animalIsDead(){
        newAnimals++;
    }


    public boolean isOccupiedByAnimal(Vector2d position){
        return animals.containsKey(position);
    }

    public boolean isOccupiedByGrass(Vector2d position){
        return plants.containsKey(position);
    }

    public WorldElement objectAt(Vector2d position){ // demo version 1
        if(animals.containsKey(position)){
            return bestAnimals.get(position);
        }
        else if(plants.containsKey(position)){
            return plants.get(position);
        }
        return null;
    }

    public void place(Animal animal){
        Vector2d animalPos = animal.position();
        if(isOccupiedByAnimal(animal.position())) {
            animals.get(animalPos).add(animal);
        }
        else{
            animals.put(animalPos, new ArrayList<>(List.of(animal)));
        }
    }

    @Override
    public String toString() {
        MapVisualizer vis = new MapVisualizer(this);
        return vis.draw(worldBounds.lowerLeft(), worldBounds.upperRight());
    }

    public ArrayList<WorldElement> getElements(){
        ArrayList<WorldElement> values = new ArrayList<>(bestAnimals.values());
        values.addAll(plants.values());
        return values;
    }

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

    public int getGenomeLength() {
        return genomeLength;
    }

    public int getNumOfGrassGrowingDaily() {
        return numOfGrassGrowingDaily;
    }

    public Boundary getJungleBounds(){ return jungleBounds; }

    public Random getRandom() { return random; }
}
