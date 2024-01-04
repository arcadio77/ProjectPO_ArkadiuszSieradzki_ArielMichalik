package model;

import java.util.Objects;


public class Vector2d {
    private final int x;
    private final int y;


    public Vector2d(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }
    public int getY(){
        return y;
    }

    public String toString(){
        return "(%s,%s)".formatted(String.valueOf(this.x), String.valueOf(this.y));
    }


    public Vector2d add(Vector2d other){
        return new Vector2d(this.x + other.x, this.y + other.y);
    }

    public boolean isBiggerOrEqual(Vector2d other){
        return this.x >= other.x && this.y >= other.y;
    }
    public boolean isSmallerOrEqual(Vector2d other){
        return this.x <= other.x && this.y <= other.y;
    }
    public Vector2d opposite(){
        return new Vector2d(-this.x, -this.y);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Vector2d vector2d)) return false;
        return x == vector2d.x && y == vector2d.y;
    }
    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

}

