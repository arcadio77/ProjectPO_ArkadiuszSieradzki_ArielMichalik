package model;

import model.exceptions.PositionAlreadyOccupiedException;
import model.interfaces.WorldElement;
import model.util.PositionsGenerator;

import java.util.*;

import static java.lang.Math.sqrt;

public class GrassField extends AbstractWorldMap{

    private final Map<Vector2d, Grass> grassFields = new HashMap<>();
    private Boundary worldBounds;
    private final Boundary grassBounds;

    public GrassField(int grassNumber, int mapID){
        this(grassNumber,new Random(), mapID);
    }

    public GrassField(int grassNumber,Random seed, int mapID){
        super(mapID);
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

    @Override
    public void place(Animal animal) throws PositionAlreadyOccupiedException {
        super.place(animal);

    }

    @Override
    public void move(Animal animal, MoveDirection direction) throws PositionAlreadyOccupiedException {
        super.move(animal, direction);

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
    @Override
    public WorldElement objectAt(Vector2d position) {
        if (super.objectAt(position) == null){
            return grassFields.get(position);
        }
        return super.objectAt(position);
    }

    @Override
    public Boundary getCurrentBounds() {
        updateCorners();
        return worldBounds;
    }

    @Override
    public ArrayList<WorldElement> getElements() {
        ArrayList<WorldElement> values = new ArrayList<>(super.getElements());
        values.addAll(grassFields.values());
        return values;
    }
}
