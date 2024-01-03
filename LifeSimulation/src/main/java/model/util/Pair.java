package model.util;

import java.util.Objects;

public class Pair {
    private final int grassNumber;
    private final int animalsNumber;

    //keeps tuple with (number of animals on giver position, number of plants on given position)
    public Pair(int animalsNumber, int grassNumber){
        this.animalsNumber = animalsNumber;
        this.grassNumber = grassNumber;
    }
    @Override
    public String toString(){
        return "(%d, %d) ".formatted(animalsNumber, grassNumber);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Pair pair)) return false;
        return grassNumber == pair.grassNumber && animalsNumber == pair.animalsNumber;
    }

    @Override
    public int hashCode() {
        return Objects.hash(grassNumber, animalsNumber);
    }
}
