package model;

public record Boundary(Vector2d lowerLeft, Vector2d upperRight) {
    public boolean isItWithinTheBoundaries(Vector2d position){
       return position.isBiggerOrEqual(lowerLeft) && position.isSmallerOrEqual(upperRight);
    }
}
