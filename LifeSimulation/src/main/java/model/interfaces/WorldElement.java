package model.interfaces;
import model.Vector2d;

public interface WorldElement {
    Vector2d position();
    @Override
    String toString();

}