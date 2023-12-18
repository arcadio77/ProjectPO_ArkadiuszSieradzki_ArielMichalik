package model;

import java.util.ArrayList;
import java.util.Random;

public class Genome {
    private final ArrayList<Integer> genome;

    public Genome(int n){
        ArrayList<Integer> genome = new ArrayList<>();
        Random rand = new Random();
        for (int i=0; i< n; i++){
            genome.add(rand.nextInt(8));
        }
        this.genome = genome;
    }

    public ArrayList<Integer> getGenome() {
        return genome;
    }
}
