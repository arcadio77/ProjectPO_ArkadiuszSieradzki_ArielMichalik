package model;

import model.enums.MapDirection;
import model.interfaces.MapChangeListener;
import model.interfaces.WorldElement;
import model.util.Energy;
import model.util.MapVisualizer;
import model.util.PositionsGenerator;
import java.util.*;


public class GrassField{

    private final int width;
    private final int height;
    private int plantsNumber; //initial at first

    private int animalsNumber; //initial at first

    Energy energy;

    Mutation mutation;

    protected final Map<Vector2d, ArrayList<Animal>> animals;
    private final Map<Vector2d, Grass> plants;
    private final int numOfGrassGrowingDaily;

    private final Boundary worldBounds;
    private ArrayList<Boundary> jungleBounds;

    protected final ArrayList<MapChangeListener> observers;

    public GrassField(int width, int height, int animalsNumber,
                      Energy energy, int minMutationNum, int maxMutationNum, int plantsNumber,
                      Random seed, int numOfGrassGrowingDaily, int minJungleNum, int maxJungleNum){

        this.width = width;
        this.height = height;
        this.animalsNumber = animalsNumber;
        this.plantsNumber = plantsNumber;
        this.energy = energy;
        this.mutation = new Mutation(minMutationNum, maxMutationNum);
        this.numOfGrassGrowingDaily = numOfGrassGrowingDaily;
        this.animals = new HashMap<>();
        this.plants = new HashMap<>();

        this.observers = new ArrayList<>();
        Vector2d worldTopRightCorner = new Vector2d(width, height);
        Vector2d worldDownLeftCorner = new Vector2d(0, 0);
        this.worldBounds = new Boundary(worldDownLeftCorner, worldTopRightCorner);

        //setJungleBounds(seed); // jungle generator <- to do


        //put grass - very dirty but will work
        PositionsGenerator grassPositions = new PositionsGenerator(width,height,plantsNumber,seed);
        for(Vector2d grassPosition : grassPositions){
            plants.put(grassPosition, new Grass(grassPosition));
        }
//        while(plantsNumber != 0){
//            PositionsGenerator positions = new PositionsGenerator(width,height,plantsNumber,seed);
//            for(Vector2d grassPosition : positions){
//                if(!isOccupied(grassPosition)){
//                    plants.put(grassPosition, new Grass(grassPosition));
//                    plantsNumber--;
//                }
//            }
//        }

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
        //showMessage("Animal moved into position: " + animal.getPosition());
    }

    // <---------------------------------------------------------------------------------------------->
    //                                            DISPLAYING
    // <---------------------------------------------------------------------------------------------->


    public String toString() {
        // don't know if necessary
        MapVisualizer vis = new MapVisualizer(this);
        return vis.draw(worldBounds.lowerLeft(), worldBounds.upperRight());
    }

    private void showMessage(String message){
        for(MapChangeListener observer: observers){
            observer.mapChanged(this, message);
        }
    }




    public void runOneCycle(){

        //Map<Vector2d, ArrayList<Animal>> animals = gF.getAnimals();
        //Map<Vector2d, Grass> plants = gF.getPlants();

        // <---------------------------------------------------------------------------------------------->
        //                                        First Phase
        // <---------------------------------------------------------------------------------------------->


        Map<Vector2d, ArrayList<Animal>> copy = new HashMap<>();
        for (Map.Entry<Vector2d, ArrayList<Animal>> entry : animals.entrySet()) {
            ArrayList<Animal> newList = new ArrayList<>();
            for (Animal animal : entry.getValue()) {
                newList.add(animal.copy());
            }
            copy.put(entry.getKey(), newList);
        }

        for (Vector2d key : copy.keySet()) {
            ArrayList<Animal> animalsOnThisPos = copy.get(key);

            for (Animal animal : animalsOnThisPos) {
                // !!1 dead
                if (animal.getEnergy() == 0) {
                    animalsOnThisPos.remove(animal);
                    if (animalsOnThisPos.isEmpty()) {
                        animals.remove(key);
                    }
                }

                // !!2 move
                //int idxOfAnimal = animals.get(key).indexOf(animal);
                animals.get(key).remove(animal); // removing old position of animal
                animal.move(worldBounds.lowerLeft(), worldBounds.upperRight()); // energy--
                if (animals.get(key).isEmpty()) {
                    animals.remove(key);
                }
                place(animal); // placing on our map
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
                mostPowerful.eat(energy.getGrassEnergy()); // energy += grassEnergy
            }

            if (animalsOnNewPos.size() > 1) {
                // sec most powerful
                Animal secMostPowerful = (Animal) animalsOnNewPos.toArray()[0];

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
                                Random rand = new Random();
                                secMostPowerful = rand.nextBoolean() ? animal : secMostPowerful;
                            }
                        }
                    }
                }

                // !!4 breed <- two most powerful (sexiest)
                // if they have at least minimum energy required to copulate
                int energyRequiredToCopulate = energy.getBreedReady();
                if (mostPowerful.getEnergy() >= energyRequiredToCopulate && secMostPowerful.getEnergy() >= energyRequiredToCopulate) {
                    Genome childGenome = new Genome(100, mostPowerful, secMostPowerful, mutation); // n is not set right
                    Animal child = new Animal(mostPowerful.getPosition(), MapDirection.NORTH, childGenome, 5, energy.getInitialAnimalEnergy());  //orientation random and geneId random
                    mostPowerful.breed(child, energy.getBreedLost());
                    secMostPowerful.breed(child, energy.getBreedLost());
                    place(child);
                }
            }
        }
    }

}
