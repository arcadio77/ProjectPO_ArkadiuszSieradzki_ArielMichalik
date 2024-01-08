package model.util;

import model.Animal;
import model.Vector2d;
import model.enums.MapDirection;

public class AnimalMoveHandler {
    //TODO to replace move function to make code cleaner in Animal class
    private final Vector2d lowerLeft;
    private final Vector2d upperRight;
    private  final Vector2d upperLeft;
    private final Vector2d lowerRight;
    private final Vector2d position;
    private final MapDirection direction;

    public AnimalMoveHandler(Vector2d lowerLeft, Vector2d upperRight, Animal animal) {
        this.lowerLeft = lowerLeft;
        this.upperRight = upperRight;
        this.upperLeft = new Vector2d(lowerLeft.x(), upperRight.y());
        this.lowerRight = new Vector2d(upperRight.x(), lowerLeft.y());
        this.position = animal.position();
        this.direction = animal.getDirection();

    }


}
