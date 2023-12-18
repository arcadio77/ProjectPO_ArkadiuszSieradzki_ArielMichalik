package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import model.interfaces.WorldMap;
import model.util.MapVisualizer;
import model.interfaces.WorldElement;

public abstract class AbstractWorldMap implements WorldMap {
    protected final int mapID;
    protected final Map<Vector2d, Animal> animals;
    protected MapVisualizer map;
    protected final ArrayList<MapChangeListener> observers;

    public AbstractWorldMap(int mapID){
        this.mapID = mapID;
        this.map = new MapVisualizer(this);
        this.animals = new HashMap<>();
        this.observers = new ArrayList<>();
    }
    @Override
    public int getId(){
        return mapID;
    }
    public void addObserver(MapChangeListener observer){
        observers.add(observer);
    }

    public void removeObserver(MapChangeListener observer){
        observers.remove(observer);
    }

    private void showMessage(String message){
        for(MapChangeListener observer: observers){
            observer.mapChanged(this, message);
        }
    }


    public void move(Animal animal, MoveDirection direction) throws PositionAlreadyOccupiedException {
        Vector2d oldPosition = animal.getPosition();

        if(objectAt(animal.getPosition()) == animal){
            this.animals.remove(animal.getPosition());
            animal.move(direction,this);
            this.place(animal);
        }

        switch (direction){
            case FORWARD -> {
                if (oldPosition != animal.getPosition()){
                    showMessage("Animal %s moved forward".formatted(animal.getPosition()));
                }}
            case BACKWARD -> {
                if (oldPosition != animal.getPosition()){
                    showMessage("Animal %s moved backward".formatted(animal.getPosition()));
                }
            }
            case RIGHT -> showMessage("Animal %s turned right".formatted(animal.getPosition()));
            case LEFT -> showMessage("Animal %s turned left".formatted(animal.getPosition()));
        }
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
    public boolean isOccupied(Vector2d position) {
        return animals.containsKey(position);
    }
    public WorldElement objectAt(Vector2d position) {
        return animals.get(position);
    }
    public boolean canMoveTo(Vector2d position) {
        return !isOccupied(position);
    }
    public ArrayList<WorldElement> getElements() {
        return new ArrayList<>(animals.values());
    }

    @Override
    public abstract Boundary getCurrentBounds();

    @Override
    public String toString() {
        Boundary bounds = getCurrentBounds();
        return map.draw(bounds.leftDownCorner(), bounds.rightUpperCorner());
    }

}

