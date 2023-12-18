package model;

import model.interfaces.WorldElement;

public class Grass implements WorldElement {
    private final Vector2d position;

    public Grass(Vector2d position){
        this.position = position;
    }
    @Override
    public Vector2d getPosition(){
        return this.position;
    }

    @Override
    public boolean isAt(Vector2d pos){
        return position.equals(pos);
    }

    @Override
    public String toString() {return "*";}
}
