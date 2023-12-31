package model.exceptions;

import model.Vector2d;

public class PositionAlreadyOccupiedException extends Exception {
    public PositionAlreadyOccupiedException(Vector2d position){
        super("Position " + position.toString() + " is already occupied");
    }
}