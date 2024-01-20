package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static java.lang.Math.round;

public class Genome {
    private final ArrayList<Integer> genome;
    private final int genomeLength;
    private final Mutation mutation;
    private final Random random;

    public Genome(int n, Mutation mutation, Random random){
        this.genomeLength = n;
        this.random = random;
        this.mutation = mutation;
        ArrayList<Integer> genome = new ArrayList<>();
        for (int i=0; i< genomeLength; i++){
            genome.add(random.nextInt(8));
        }
        this.genome = genome;
    }

    public Genome(int n, Animal parent1, Animal parent2, Mutation mutation, Random random, boolean useMutationSwapGene){
        this.genomeLength = n;
        this.mutation = mutation;
        this.random = random;
        this.genome = generateGenome(parent1, parent2, random, useMutationSwapGene);
    }

    private ArrayList<Integer> generateGenome(Animal parent1, Animal parent2, Random random, boolean useMutationSwapGene){
        int combinedEnergy = parent1.getEnergy() + parent2.getEnergy();
        double parent1Share = (double) parent1.getEnergy() / combinedEnergy;
        double parent2Share = (double) parent2.getEnergy() / combinedEnergy;

        boolean randomBool = random.nextBoolean();

        if(randomBool){
            int howManyIdxParent1 = (int)(round(parent1Share * parent1.getGenomeList().size()));
            List<Integer> partOfParent1 = parent1.getGenomeList().subList(0, howManyIdxParent1);

            int howManyIdxParent2 = (int)(round(parent2Share * parent2.getGenomeList().size()));
            int startingIdx = parent2.getGenomeList().size() - howManyIdxParent2;
            List<Integer> partOfParent2 = parent2.getGenomeList().subList(startingIdx, parent2.getGenomeList().size());

            partOfParent1.addAll(partOfParent2); // combined

            return mutate(partOfParent1, useMutationSwapGene); // mutation
        }
        else{
            int howManyIdxParent1 = (int)(parent1Share * parent1.getGenomeList().size());
            int startingIdx = parent1.getGenomeList().size() - howManyIdxParent1;
            List<Integer> partOfParent1 = parent1.getGenomeList().subList(startingIdx, parent1.getGenomeList().size());

            int howManyIdxParent2 = (int)(parent2Share * parent2.getGenomeList().size());
            List<Integer> partOfParent2 = parent2.getGenomeList().subList(0, howManyIdxParent2);

            partOfParent1.addAll(partOfParent2); // combined

            return mutate(partOfParent1, useMutationSwapGene); // mutation
        }
    }

    private ArrayList<Integer> mutate(List<Integer> currGenome, boolean useMutationSwapGene){
        int min = mutation.minMutationsNum();
        int max = mutation.maxMutationsNum();

        int mutationsNum = min + random.nextInt(max + 1 - min);

        for (int i = 0 ; i < mutationsNum; i++){
            int randomNum = random.nextInt(8);
            int where = random.nextInt(currGenome.size());
            currGenome.set(where, randomNum);
        }

        if (useMutationSwapGene){
            int randomId1 = random.nextInt(currGenome.size());
            int randomId2 = random.nextInt(currGenome.size());
            Integer temp = currGenome.get(randomId1);
            currGenome.set(randomId1, currGenome.get(randomId2));
            currGenome.set(randomId2, temp);
        }

        return new ArrayList<>(currGenome);
    }

    public ArrayList<Integer> getGenome() {
        return genome;
    }
}
