package model.enums;

import model.Vector2d;

public enum MapDirection {
    NORTH,
    NORTHEAST,
    EAST,
    SOUTHEAST,
    SOUTH,
    SOUTHWEST,
    WEST,
    NORTHWEST;

    public static MapDirection fromInteger(int x) throws IllegalArgumentException {
        return switch (x) {
            case 0 -> NORTH;
            case 1 -> NORTHEAST;
            case 2 -> EAST;
            case 3 -> SOUTHEAST;
            case 4 -> SOUTH;
            case 5 -> SOUTHWEST;
            case 6 -> WEST;
            case 7 -> NORTHWEST;
            default -> throw new IllegalArgumentException(x + "is not legal gene value");
        };
    }

    public int fromEnum() {
        return switch (this) {
            case NORTH -> 0;
            case NORTHEAST -> 1;
            case EAST -> 2;
            case SOUTHEAST -> 3;
            case SOUTH -> 4;
            case SOUTHWEST -> 5;
            case WEST -> 6;
            case NORTHWEST -> 7;
        };
    }

    public String toString(){
        return switch (this){
            case NORTH -> "Polnoc";
            case NORTHEAST -> "Polnocny-Wschod";
            case EAST -> "Wschod";
            case SOUTHEAST -> "Poludniowy-Wschod";
            case SOUTH -> "Poludnie";
            case SOUTHWEST -> "Poludniowy-Zachod";
            case WEST -> "Zachod";
            case NORTHWEST -> "Polnocy-Zachod";
        };
    }

    public Vector2d toUnitVector(){
        return switch (this) {
            case NORTH -> new Vector2d(0, 1);
            case NORTHEAST -> new Vector2d(1, 1);
            case EAST -> new Vector2d(1, 0);
            case SOUTHEAST -> new Vector2d(1, -1);
            case SOUTH -> new Vector2d(0, -1);
            case SOUTHWEST -> new Vector2d(-1, -1);
            case WEST -> new Vector2d(-1, 0);
            case NORTHWEST -> new Vector2d(-1, 1);
        };
    }

    public MapDirection opposite(){
        return switch (this){
            case NORTH -> SOUTH;
            case NORTHEAST -> SOUTHWEST;
            case EAST -> WEST;
            case SOUTHEAST -> NORTHWEST;
            case SOUTH -> NORTH;
            case SOUTHWEST -> NORTHEAST;
            case WEST -> EAST;
            case NORTHWEST -> SOUTHEAST;
        };
    }

}