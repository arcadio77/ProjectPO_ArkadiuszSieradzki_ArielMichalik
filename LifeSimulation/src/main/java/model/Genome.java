package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static java.lang.Math.round;

public class Genome {
    private final ArrayList<Integer> genome;
    private final int genomeLength;
    private final Mutation mutation;


    public Genome(int n, Mutation mutation){
        this.genomeLength = n;
        this.mutation = mutation;
        ArrayList<Integer> genome = new ArrayList<>();
        Random rand = new Random();
        for (int i=0; i< genomeLength; i++){
            genome.add(rand.nextInt(8));
        }
        this.genome = genome;
    }

    public Genome(int n, Animal parent1, Animal parent2, Mutation mutation){
        this.genomeLength = n;
        this.mutation = mutation;
        boolean rand = new Random().nextBoolean();

        int combinedEnergy = parent1.getEnergy() + parent2.getEnergy();
        double parent1Share = (double) parent1.getEnergy() / combinedEnergy; //castowanie na double gdzies musi być moze do naprawy?
        double parent2Share = (double) parent2.getEnergy() / combinedEnergy;

        if(true){
            int howManyIdxParent1 = (int)(round(parent1Share * parent1.getGenome().size()));
            List<Integer> partOfParent1 = parent1.getGenome().subList(0, howManyIdxParent1);

            int howManyIdxParent2 = (int)(round(parent2Share * parent2.getGenome().size()));
            int startingIdx = parent2.getGenome().size() - howManyIdxParent2;
            List<Integer> partOfParent2 = parent2.getGenome().subList(startingIdx, parent2.getGenome().size());

            partOfParent1.addAll(partOfParent2); // combined

            this.genome = mutate(partOfParent1); // mutation
        }
        else{
            int howManyIdxParent1 = (int)(parent1Share * parent1.getGenome().size());
            int startingIdx = parent1.getGenome().size() - howManyIdxParent1;
            List<Integer> partOfParent1 = parent1.getGenome().subList(startingIdx, parent1.getGenome().size());

            int howManyIdxParent2 = (int)(parent2Share * parent2.getGenome().size());
            List<Integer> partOfParent2 = parent2.getGenome().subList(0, howManyIdxParent2);

            partOfParent1.addAll(partOfParent2); // combined

            this.genome = mutate(partOfParent1); // mutation
        }
    }

    private ArrayList<Integer> mutate(List<Integer> currGenome){
        int min = mutation.getMinNum();
        int max = mutation.getMaxNum();

        Random rand = new Random();

        int mutationsNum = min + rand.nextInt(max + 1 - min);

        for (int i = 0 ; i < mutationsNum; i++){
            int randomNum = rand.nextInt(8);
            int where = rand.nextInt(currGenome.size());
            currGenome.set(where, randomNum);
        }

        return new ArrayList<>(currGenome);
    }

    public ArrayList<Integer> getGenome() {
        return genome;
    }
    public int getGenomeLength(){return genomeLength;}
}
