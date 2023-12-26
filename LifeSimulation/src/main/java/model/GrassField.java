package model;

import model.exceptions.PositionAlreadyOccupiedException;
import model.interfaces.MapChangeListener;
import model.interfaces.WorldElement;
import model.util.MapVisualizer;
import model.util.PositionsGenerator;
import java.util.*;
import static java.lang.Math.sqrt;

public class GrassField{
    private final Map<Vector2d, Grass> grassFields = new HashMap<>();
    protected final Map<Vector2d, Animal> animals;
    protected MapVisualizer map;
    private Boundary worldBounds;
    private ArrayList<Boundary> jungleBounds;
    protected final ArrayList<MapChangeListener> observers;

    public GrassField(int grassNumber,Random seed, int minJungleSize, int maxJungleSize){
        this.map = new MapVisualizer(this);
        this.animals = new HashMap<>();
        this.observers = new ArrayList<>();

        Vector2d worldTopRightCorner = new Vector2d(0, 0);
        Vector2d worldDownLeftCorner = new Vector2d((int)(sqrt(grassNumber * 10)),(int) (sqrt(grassNumber * 10)));

        PositionsGenerator positions = new PositionsGenerator(worldDownLeftCorner.getX(), worldDownLeftCorner.getY(),grassNumber,seed);
        for(Vector2d grassPosition : positions){
            grassFields.put(grassPosition, new Grass(grassPosition));
            worldDownLeftCorner = worldDownLeftCorner.lowerLeft(grassPosition);
            worldTopRightCorner = worldTopRightCorner.upperRight(grassPosition);
        }
        this.grassBounds = new Boundary(worldDownLeftCorner, worldTopRightCorner);
        this.worldBounds = new Boundary(worldDownLeftCorner, worldTopRightCorner);
    }

    public void place(Animal animal) throws PositionAlreadyOccupiedException {
        if(canMoveTo(animal.getPosition())){
            animals.put(animal.getPosition(), animal);
            showMessage("Animal moved into position: " + animal.getPosition());
        }
        else{
            throw new PositionAlreadyOccupiedException(animal.getPosition());

        }
    }

    private void showMessage(String message){
        for(MapChangeListener observer: observers){
            observer.mapChanged(this, message);
        }
    }

    public void move(Animal animal) throws PositionAlreadyOccupiedException {
    }

    public void updateCorners(){
        Vector2d worldDownLeftCorner = grassBounds.leftDownCorner();
        Vector2d worldTopRightCorner = grassBounds.rightUpperCorner();
        for(Vector2d position: animals.keySet()){
            worldDownLeftCorner = worldDownLeftCorner.lowerLeft(position);
            worldTopRightCorner = worldTopRightCorner.upperRight(position);
        }
        worldBounds = new Boundary(worldDownLeftCorner, worldTopRightCorner);
    }

    public WorldElement objectAt(Vector2d position) {
        return animals.get(position);
    }

    public Boundary getCurrentBounds() {
        updateCorners();
        return worldBounds;
    }

    public ArrayList<WorldElement> getElements(){
        ArrayList<WorldElement> values = new ArrayList<>(new ArrayList<>(animals.values()));
        values.addAll(grassFields.values());
        return values;
    }

    public boolean isOccupied(Vector2d position){
        return animals.containsKey(position);
    }

    public boolean canMoveTo(Vector2d position){
        return !isOccupied(position);
    }

    public String toString(){
        Boundary bounds = getCurrentBounds();
        return map.draw(bounds.leftDownCorner(), bounds.rightUpperCorner());
    }

    public void oneMovement(){

        Map<Vector2d, Integer> counter = new HashMap<>();

        for (Vector2d key : animals.keySet()) {
            Animal animal = animals.get(key);

            // dead
            int animalEnergy = animal.getEnergy();
            if(animalEnergy == 0){animals.remove(key);}

            // move
            animal.move();

            //eat
            Vector2d animalPos = animal.getPosition();
            if(grassFields.containsKey(animalPos)){
                grassFields.remove(animalPos);
                animal.eat(); // energy +1
            }

            counter.put(key, )

        }
        Set<Vector2d> animalsPos = animals.keySet();

        for
    }
    



}
