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
    private final Animal animal;
    private final Integer geneId;

    public AnimalMoveHandler(Vector2d lowerLeft, Vector2d upperRight, Animal animal, Integer geneId) {
        this.lowerLeft = lowerLeft;
        this.upperRight = upperRight;
        this.upperLeft = new Vector2d(lowerLeft.x(), upperRight.y());
        this.lowerRight = new Vector2d(upperRight.x(), lowerLeft.y());
        this.animal = animal;
        this.position = animal.position();
        this.direction = animal.getDirection();
        this.geneId = geneId;
    }

    public MapDirection getNewOrientation(){
        int gene = animal.getGenomeList().get(geneId);
        int currOrientationInt = this.direction.fromEnum();
        int newOrientationInt = (currOrientationInt + gene) % 8;
        return MapDirection.fromInteger(newOrientationInt);
    }

    public Vector2d getNewPosition(MapDirection newOrientation){
        return position.add(newOrientation.toUnitVector());
    }

    public boolean isInTheMiddle(){
        return position.isBigger(lowerLeft) && position.isSmaller(upperRight);
    }


    public void move(){
        MapDirection newOrientation = getNewOrientation();
        Vector2d newPosition = getNewPosition(newOrientation);
//        if (isInTheMiddle()){
//            this.position = newPosition;
//            this.direction = newOrientation;
//        }
    }

}
