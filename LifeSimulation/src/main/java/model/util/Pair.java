package model.util;

public class Pair {
    private final int grassNumber;
    private final int animalsNumber;

    //keeps tuple with (number of animals on giver position, number of plants on given position)
    public Pair(int animalsNumber, int grassNumber){
        this.animalsNumber = animalsNumber;
        this.grassNumber = grassNumber;
    }
    public String toString(){
        return "(%d, %d)".formatted(animalsNumber, grassNumber);
    }
}
