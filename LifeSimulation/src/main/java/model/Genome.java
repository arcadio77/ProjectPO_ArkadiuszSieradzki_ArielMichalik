package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static java.lang.Math.ceil;
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
        double ratio = (float) parent1.getEnergy() / combinedEnergy;

        boolean randomBool = random.nextBoolean();

        if(randomBool){
            int Idx = (int)(ceil(ratio * parent1.getGenomeList().size()));
            List<Integer> partOfParent1 = new ArrayList<>(parent1.getGenomeList().subList(0, Idx));
            List<Integer> partOfParent2 = new ArrayList<>(parent2.getGenomeList().subList(Idx, parent2.getGenomeList().size()));

            partOfParent1.addAll(partOfParent2); // combined

            return mutate(partOfParent1, useMutationSwapGene); // mutation
        }
        else{
            int Idx = parent1.getGenomeList().size() - (int)(ceil(ratio * parent1.getGenomeList().size()));
            List<Integer> partOfParent1 = new ArrayList<>(parent1.getGenomeList().subList(Idx, parent1.getGenomeList().size()));
            List<Integer> partOfParent2 = new ArrayList<>(parent2.getGenomeList().subList(0, Idx));

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
            int where = random.nextInt(genomeLength);
            currGenome.set(where, randomNum);
        }

        if (useMutationSwapGene){
            int randomId1 = random.nextInt(genomeLength);
            int randomId2 = random.nextInt(genomeLength);
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
