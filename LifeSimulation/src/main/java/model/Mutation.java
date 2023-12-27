package model;

public class Mutation {
    private final int minNum;
    private final int maxNum;
    public Mutation(int minMutationsNum, int maxMutationsNum){
        this.minNum = minMutationsNum;
        this.maxNum = maxMutationsNum;
    }

    public int getMinNum() {
        return minNum;
    }

    public int getMaxNum() {
        return maxNum;
    }
}

