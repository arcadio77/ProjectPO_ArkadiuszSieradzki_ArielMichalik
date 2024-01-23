package model;

import model.enums.MapDirection;
import model.interfaces.MapChangeListener;
import model.interfaces.WorldElement;
import model.util.*;

import java.util.*;


public class WorldMap {
    private int day;
    private int width;
    private int height;
    protected final ArrayList<MapChangeListener> observers;
    private int initialPlantsNumber;
    private int initialAnimalsNumber;
    private int genomeLength;
    Energy energy;
    Mutation mutation;
    protected Map<Vector2d, ArrayList<Animal>> animals;
    protected Map<Vector2d, Animal> bestAnimals;
    protected Map<Vector2d, Grass> plants;
    private int numOfGrassGrowingDaily;
    private Boundary worldBounds;
    private Boundary jungleBounds;
    private Random random;
    private boolean useMutationSwapGene;
    private boolean useLifeGivingCorpses;
    protected Map<Vector2d, Integer> recentGraves = new HashMap<>();
    protected ArrayList<Animal> deadAnimals;

    public ArrayList<Animal> getDeadAnimals() {
        return deadAnimals;
    }

    public WorldMap(int width, int height, int animalsNumber, int plantsNumber,
                    Energy energy, int minMutationNum, int maxMutationNum, int genomeLength,
                    int numOfGrassGrowingDaily, Random random, boolean useMutationSwapGene,
                    boolean useLifeGivingCorpses){
        this.day = 0;
        this.width = width;
        this.height = height;
        this.initialAnimalsNumber = animalsNumber;
        this.initialPlantsNumber = plantsNumber;
        this.energy = energy;
        this.mutation = new Mutation(minMutationNum, maxMutationNum);
        this.genomeLength = genomeLength;
        this.numOfGrassGrowingDaily = numOfGrassGrowingDaily;
        this.animals = new HashMap<>();
        this.bestAnimals = new HashMap<>();
        this.plants = new HashMap<>();
        this.random = random;
        this.observers = new ArrayList<>();
        this.deadAnimals =  new ArrayList<Animal>();
        this.useMutationSwapGene = useMutationSwapGene;
        this.useLifeGivingCorpses = useLifeGivingCorpses;
    }

    public WorldMap(){
        this(11, 1, 0, 0, new Energy(0, 0, 0, 0),
                0, 0, 0, 0, new Random(), false, false);
    }

    public WorldMap(int w, int h){
        this(w, h, 1000, 1000000, new Energy(10, 2, 1, 10),
                0, 0, 10, 1000000, new Random(), false, false);
    }

    public void passDay(){
        this.day += 1;
    }

    //OBSERVERS
    public void addObserver(MapChangeListener observer){
        System.out.println("added observer");
        observers.add(observer);
    }

    public void mapChanged(String message){
        for(MapChangeListener observer: observers){
            observer.mapChanged(this, message);
        }
    }


    public Boundary setJungleBounds(){
        //20% of the map area, horizontal line of the grass -> const width
        int h = this.height - 1;
        int w = this.width - 1;
        int mapArea = h * w;
        int jungleArea = (int) (mapArea*0.2);
        int y = jungleArea/w;
        int centerHeight = h/2;
        Vector2d leftDownCorner = new Vector2d(0,centerHeight-(int)(y/2));
        Vector2d rightUpCorner = new Vector2d(w,leftDownCorner.y() + Math.max(y,1));
        return new Boundary(leftDownCorner, rightUpCorner);
    }

    public void killAllAnimals(){
        animals = new HashMap<>();
    }

    public void initPutGrasses(int plantsNumber){
        GrassPositionsGenerator grassPositions = new GrassPositionsGenerator(this,plantsNumber, false);
        for(Vector2d grassPosition : grassPositions){
            plants.put(grassPosition, new Grass(grassPosition));
        }
    }

    public void putGrasses(int plantsNumber){
        GrassPositionsGenerator grassPositions = new GrassPositionsGenerator(this,plantsNumber, useLifeGivingCorpses);
        for(Vector2d grassPosition : grassPositions){
            plants.put(grassPosition, new Grass(grassPosition));
        }
    }

    public void initPutAnimals(){
        AnimalPositionsGenerator positions = new AnimalPositionsGenerator(this, initialAnimalsNumber);
        for(Vector2d animalPosition: positions){
            Animal newAnimal = new Animal(animalPosition, MapDirection.generateRandomDirection(), new Genome(genomeLength, mutation, random), 0, energy.getInitialAnimalEnergy());
            this.place(newAnimal);
        }
        for (Vector2d position : animals.keySet()){
            this.bestAnimals.put(position, animals.get(position).get(0));
        }
    }

    public void initBounds() {
        Vector2d worldTopRightCorner = new Vector2d(width-1, height-1);
        Vector2d worldDownLeftCorner = new Vector2d(0, 0);
        this.worldBounds = new Boundary(worldDownLeftCorner, worldTopRightCorner);
        this.jungleBounds = setJungleBounds();
    }

    public void animalIsDead(Vector2d position, Animal animal){
        deadAnimals.add(animal);
        recentGraves.remove(position);
        recentGraves.put(position, 5); // 5 dni są ważne trupy
    }

    public void gravesAreGettingOlder(){
        Map<Vector2d, Integer> newRecentGraves = new HashMap<>();
        for(Vector2d position : recentGraves.keySet()){
            Integer graveStamina = recentGraves.get(position);
            if (graveStamina > 1) {
                newRecentGraves.put(position, graveStamina - 1);
            }
        }
        recentGraves = newRecentGraves;
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
    public Map<Vector2d, Animal> getBestAnimals() {return bestAnimals;}
    public int getGenomeLength() {
        return genomeLength;
    }
    public int getNumOfGrassGrowingDaily() {
        return numOfGrassGrowingDaily;
    }
    public Boundary getJungleBounds(){ return jungleBounds; }
    public Random getRandom() { return random; }
    public int getDay() { return day; }
    public boolean isUseMutationSwapGene() {
        return useMutationSwapGene;
    }
    public Map<Vector2d, Integer> getRecentGraves() {
        return recentGraves;
    }
    public void setInitialPlantsNumber(int initialPlantsNumber) {
        this.initialPlantsNumber = initialPlantsNumber;
    }
    public void setInitialAnimalsNumber(int initialAnimalsNumber) {
        this.initialAnimalsNumber = initialAnimalsNumber;
    }
    public void setGenomeLength(int genomeLength) {
        this.genomeLength = genomeLength;
    }
    public void setEnergy(Energy energy) {
        this.energy = energy;
    }
    public void setMutation(Mutation mutation) {
        this.mutation = mutation;
    }
    public void setWidth(int width) {
        this.width = width;
    }
    public void setHeight(int height) {
        this.height = height;
    }
    public void setNumOfGrassGrowingDaily(int numOfGrassGrowingDaily) {
        this.numOfGrassGrowingDaily = numOfGrassGrowingDaily;
    }
    public void setRandom(Random random) {
        this.random = random;
    }
    public void setUseMutationSwapGene(boolean useMutationSwapGene) {
        this.useMutationSwapGene = useMutationSwapGene;
    }
    public void setUseLifeGivingCorpses(boolean useLifeGivingCorpses) {
        this.useLifeGivingCorpses = useLifeGivingCorpses;
    }
    public boolean isUseLifeGivingCorpses() {
        return useLifeGivingCorpses;
    }
}
