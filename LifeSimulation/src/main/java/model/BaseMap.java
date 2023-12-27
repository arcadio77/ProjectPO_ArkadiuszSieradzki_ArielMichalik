package model;

import model.util.Energy;

public class BaseMap {
    final int width;
    final int height;
    int animalsNumber;
    Energy energy;
    Mutation mutation;




    public BaseMap(int width, int height, int animalsNumber, Energy energy, Mutation mutation){
        this.width = width;
        this.height = height;
        this.animalsNumber = animalsNumber;
        this.energy = energy;
        this.mutation = mutation;


    }
    public BaseMap(int width, int height){
        this(width,height, 5,
                new Energy(1, 5, 3),
                new Mutation(0, 0));

    }


}
